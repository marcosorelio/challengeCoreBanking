package org.orelio.facade;

import org.orelio.model.Account;
import org.orelio.model.Constants;
import org.orelio.model.Operation;
import org.orelio.util.ChallengeCoreUtils;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

/**Class Facade to create, update, return and remove Accounts final Map<String>
 * @author Marcos Orelio
 * @version 1.0-SNAPSHOT
 * @since 28/09/2025
 */

@Service
public class ChallengeCoreBankingFacade {
    private static final Map<String, Account> accountMap = new HashMap<>();

    public Account createAccount(Account account) {
        accountMap.put(account.getId(), account);
        return account;
    }

    public Account updateAccount(Account account, String accountId) {
        accountMap.put(accountId, account);
        return account;
    }

    public Account getAccount(String accountId) {
        return accountMap.get(accountId);
    }

    public void resetAccount() {
        accountMap.clear();
    }

    /**Method to identify operation field Type by API request body
     * @since 28/09/2025
     * @param Operation - Object to Request API
     * @return String
     */
    public String operationEvent(Operation operation) {
        // return String types of operation (deposit, withdraw and transfer)
        String optTypeStr = operation.getType().toLowerCase();
        // return Object Accounts (destination and origin)
        Account accountReturn = getAccount(operation.getDestination());
        Account accountOrigin = getAccount(operation.getOrigin());
        // new Account to used instance
        Account account = new Account();
        switch (optTypeStr) {
            case Constants.DEPOSIT:
                //verify param account destination
                if (accountReturn == null) {
                    account.setId(operation.getDestination());
                    account.setBalance(Long.parseLong(operation.getAmount()));
                } else {
                    account.setId(accountReturn.getId());
                    account.setBalance(accountReturn.getBalance() + Long.parseLong(operation.getAmount()));
                }
                return ChallengeCoreUtils.resultJsonAPI(createAccount(account), Constants.fmtDestination);

            case Constants.TRANSFER:
                //verify param account origin
                if (accountOrigin != null) {
                    String jsonResult = "";

                    if (accountReturn == null) {
                        // if account destination not exists create new one
                        account.setId(operation.getDestination());
                        account.setBalance(Long.parseLong(operation.getAmount()));
                        jsonResult = ChallengeCoreUtils.resultJsonAPI(createAccount(account));

                    } else {
                        // if account exists sum balance (not specify in Transfer from existing account)
                        account.setId(accountReturn.getId());
                        account.setBalance(accountReturn.getBalance() + Long.parseLong(operation.getAmount()));
                        //updateAccount(account, accountReturn.getId());
                        jsonResult = ChallengeCoreUtils.resultJsonAPI(updateAccount(account, accountReturn.getId()));
                    }

                    //origin minus balance
                    account = new Account();
                    account.setId(accountOrigin.getId());
                    account.setBalance(accountOrigin.getBalance() - Long.parseLong(operation.getAmount()));

                    return String.format(Constants.fmtOriginDestin, ChallengeCoreUtils.resultJsonAPI(updateAccount(account, accountOrigin.getId())), jsonResult);
                }
                return Constants.ZERO;

            case Constants.WITHDRAW:
                //verify param account origin
                if (accountOrigin != null) {
                    account.setId(accountOrigin.getId());
                    account.setBalance(accountOrigin.getBalance() - Long.parseLong(operation.getAmount()));
                    return ChallengeCoreUtils.resultJsonAPI(updateAccount(account, account.getId()), Constants.fmtOrigin);
                }
                return Constants.ZERO;

            default:
                return Constants.ZERO;

        }
    }



}
