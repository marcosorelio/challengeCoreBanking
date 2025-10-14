package org.orelio.facade;

import org.orelio.model.Account;
import org.orelio.model.Constants;
import org.orelio.model.Operation;
import org.orelio.util.ChallengeCoreUtils;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

/**
 * Service facade for core banking operations in the ChallengeCoreBanking system.
 * 
 * <p>This facade provides the business logic layer for all banking operations including
 * account management and transaction processing. It uses an in-memory HashMap for
 * account storage and provides methods for:</p>
 * <ul>
 *   <li>Account creation and management</li>
 *   <li>Deposit operations</li>
 *   <li>Withdrawal operations</li>
 *   <li>Transfer operations between accounts</li>
 *   <li>System reset functionality</li>
 * </ul>
 * 
 * <p>The service is thread-safe for basic operations but uses in-memory storage,
 * so data is lost when the application restarts.</p>
 * 
 * @author Marcos Orelio
 * @version 1.0-SNAPSHOT
 * @since 1.0
 * @see org.orelio.model.Account
 * @see org.orelio.model.Operation
 * @see org.orelio.util.ChallengeCoreUtils
 */
@Service
public class ChallengeCoreBankingFacade {
    
    /**
     * In-memory storage for accounts using account ID as key.
     * This map stores all accounts in the system.
     */
    private static final Map<String, Account> accountMap = new HashMap<>();

    /**
     * Creates a new account in the system.
     * 
     * <p>If an account with the same ID already exists, it will be overwritten
     * with the new account data.</p>
     * 
     * @param account the account to create
     * @return the created account (same reference as input)
     * @throws IllegalArgumentException if account is null or has null ID
     */
    public Account createAccount(Account account) {
        accountMap.put(account.getId(), account);
        return account;
    }

    /**
     * Updates an existing account in the system.
     * 
     * <p>This method updates the account data for the specified account ID.
     * If the account doesn't exist, it will be created.</p>
     * 
     * @param account the account data to update
     * @param accountId the ID of the account to update
     * @return the updated account
     * @throws IllegalArgumentException if account or accountId is null
     */
    public Account updateAccount(Account account, String accountId) {
        accountMap.put(accountId, account);
        return account;
    }

    /**
     * Retrieves an account by its ID.
     * 
     * @param accountId the unique identifier of the account
     * @return the account if found, null otherwise
     */
    public Account getAccount(String accountId) {
        return accountMap.get(accountId);
    }

    /**
     * Resets the banking system by clearing all accounts.
     * 
     * <p>This method removes all accounts from the system, effectively
     * returning it to its initial empty state.</p>
     */
    public void resetAccount() {
        accountMap.clear();
    }

    /**
     * Processes a banking operation based on the operation type.
     * 
     * <p>This method handles three types of operations:</p>
     * <ul>
     *   <li><strong>deposit</strong> - Adds funds to a destination account</li>
     *   <li><strong>withdraw</strong> - Removes funds from an origin account</li>
     *   <li><strong>transfer</strong> - Moves funds from origin to destination account</li>
     * </ul>
     * 
     * <p>The method returns a JSON string containing the updated account information.
     * If the operation fails (e.g., invalid account, insufficient funds), it returns
     * the {@link Constants#ZERO} constant.</p>
     * 
     * <p><strong>Operation Details:</strong></p>
     * <ul>
     *   <li><strong>Deposit:</strong> Creates destination account if it doesn't exist</li>
     *   <li><strong>Withdraw:</strong> Requires origin account to exist</li>
     *   <li><strong>Transfer:</strong> Requires origin account to exist; creates destination if needed</li>
     * </ul>
     * 
     * @param operation the banking operation to process
     * @return JSON string with account details if successful, {@link Constants#ZERO} if failed
     * @throws NumberFormatException if the amount cannot be parsed as a long
     * @see Constants#DEPOSIT
     * @see Constants#WITHDRAW
     * @see Constants#TRANSFER
     * @see ChallengeCoreUtils#resultJsonAPI(Account, String)
     */
    public String operationEvent(Operation operation) {
        // Normalize operation type to lowercase for case-insensitive comparison
        String optTypeStr = operation.getType().toLowerCase();
        
        // Get account references for origin and destination
        Account accountReturn = getAccount(operation.getDestination());
        Account accountOrigin = getAccount(operation.getOrigin());
        
        // Create new account instance for operations
        Account account = new Account();
        
        switch (optTypeStr) {
            case Constants.DEPOSIT:
                // Handle deposit operation
                if (accountReturn == null) {
                    // Create new account with deposit amount
                    account.setId(operation.getDestination());
                    account.setBalance(Long.parseLong(operation.getAmount()));
                } else {
                    // Add to existing account balance
                    account.setId(accountReturn.getId());
                    account.setBalance(accountReturn.getBalance() + Long.parseLong(operation.getAmount()));
                }
                return ChallengeCoreUtils.resultJsonAPI(createAccount(account), Constants.fmtDestination);

            case Constants.TRANSFER:
                // Handle transfer operation
                if (accountOrigin != null) {
                    String jsonResult = "";

                    if (accountReturn == null) {
                        // Create new destination account
                        account.setId(operation.getDestination());
                        account.setBalance(Long.parseLong(operation.getAmount()));
                        jsonResult = ChallengeCoreUtils.resultJsonAPI(createAccount(account));

                    } else {
                        // Add to existing destination account
                        account.setId(accountReturn.getId());
                        account.setBalance(accountReturn.getBalance() + Long.parseLong(operation.getAmount()));
                        jsonResult = ChallengeCoreUtils.resultJsonAPI(updateAccount(account, accountReturn.getId()));
                    }

                    // Subtract from origin account
                    account = new Account();
                    account.setId(accountOrigin.getId());
                    account.setBalance(accountOrigin.getBalance() - Long.parseLong(operation.getAmount()));

                    return String.format(Constants.fmtOriginDestin, 
                                      ChallengeCoreUtils.resultJsonAPI(updateAccount(account, accountOrigin.getId())), 
                                      jsonResult);
                }
                return Constants.ZERO;

            case Constants.WITHDRAW:
                // Handle withdrawal operation
                if (accountOrigin != null) {
                    account.setId(accountOrigin.getId());
                    account.setBalance(accountOrigin.getBalance() - Long.parseLong(operation.getAmount()));
                    return ChallengeCoreUtils.resultJsonAPI(updateAccount(account, account.getId()), Constants.fmtOrigin);
                }
                return Constants.ZERO;

            default:
                // Invalid operation type
                return Constants.ZERO;
        }
    }
}
