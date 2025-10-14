package org.orelio.controllers;

import org.orelio.facade.ChallengeCoreBankingFacade;
import org.orelio.model.Account;
import org.orelio.model.Constants;
import org.orelio.model.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for banking operations in the ChallengeCoreBanking system.
 * 
 * <p>This controller provides RESTful endpoints for core banking operations including
 * account balance retrieval, banking operations (deposit, withdraw, transfer), and
 * system reset functionality.</p>
 * 
 * <p>All endpoints return JSON responses and use standard HTTP status codes:</p>
 * <ul>
 *   <li>{@code 200 OK} - Successful GET requests</li>
 *   <li>{@code 201 Created} - Successful POST operations</li>
 *   <li>{@code 404 Not Found} - Account not found or invalid operations</li>
 * </ul>
 * 
 * <p>The controller delegates business logic to the {@link ChallengeCoreBankingFacade}
 * service layer.</p>
 * 
 * @author Marcos Orelio
 * @version 1.0-SNAPSHOT
 * @since 1.0
 * @see org.orelio.facade.ChallengeCoreBankingFacade
 * @see org.orelio.model.Account
 * @see org.orelio.model.Operation
 */
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class GreetingsController {

    /**
     * The banking facade service that handles all business logic operations.
     */
    @Autowired
    private ChallengeCoreBankingFacade challengeCoreBankingFacade;

    /**
     * Resets the banking system by clearing all accounts.
     * 
     * <p>This endpoint removes all accounts from the system and returns the system
     * to its initial state. This is useful for testing or system maintenance.</p>
     * 
     * @return ResponseEntity containing "OK" string with HTTP 200 status
     * @see ChallengeCoreBankingFacade#resetAccount()
     */
    @PostMapping("/reset")
    @ResponseBody
    public ResponseEntity<String> restBalance(){
        challengeCoreBankingFacade.resetAccount();
        return new ResponseEntity<String>("OK", HttpStatus.OK);
    }

    /**
     * Retrieves the balance of a specific account.
     * 
     * <p>This endpoint looks up an account by its ID and returns the current balance.
     * If the account does not exist, it returns "0" with a 404 status.</p>
     * 
     * @param accountId the unique identifier of the account to query
     * @return ResponseEntity containing the account balance as a string:
     *         <ul>
     *           <li>HTTP 200 with balance if account exists</li>
     *           <li>HTTP 404 with "0" if account does not exist</li>
     *         </ul>
     * @see ChallengeCoreBankingFacade#getAccount(String)
     */
    @GetMapping("/balance")
    @ResponseBody
    public ResponseEntity<String> getAccount(@RequestParam("account_id") String accountId){
        Account account = challengeCoreBankingFacade.getAccount(accountId);
        if(account != null){
            return new ResponseEntity<String>(account.getBalance().toString(), HttpStatus.OK);
        }
        return new ResponseEntity<String>(Constants.ZERO, HttpStatus.NOT_FOUND);
    }

    /**
     * Performs a banking operation (deposit, withdraw, or transfer).
     * 
     * <p>This endpoint processes banking operations based on the operation type:</p>
     * <ul>
     *   <li><strong>deposit</strong> - Adds funds to an account (creates account if it doesn't exist)</li>
     *   <li><strong>withdraw</strong> - Removes funds from an existing account</li>
     *   <li><strong>transfer</strong> - Moves funds between accounts</li>
     * </ul>
     * 
     * <p>The request body should contain:</p>
     * <ul>
     *   <li>{@code type} - Operation type ("deposit", "withdraw", "transfer")</li>
     *   <li>{@code amount} - Amount as string (e.g., "100")</li>
     *   <li>{@code destination} - Destination account ID (required for deposit/transfer)</li>
     *   <li>{@code origin} - Origin account ID (required for withdraw/transfer)</li>
     * </ul>
     * 
     * @param operation the banking operation to perform
     * @return ResponseEntity containing the operation result:
     *         <ul>
     *           <li>HTTP 201 with account details if operation succeeds</li>
     *           <li>HTTP 404 with "0" if operation fails (invalid account, insufficient funds, etc.)</li>
     *         </ul>
     * @see ChallengeCoreBankingFacade#operationEvent(Operation)
     * @see org.orelio.model.Operation
     */
    @PostMapping("/event")
    @ResponseBody
    public ResponseEntity<String> deposit(@RequestBody Operation operation){
        String resultBalance = challengeCoreBankingFacade.operationEvent(operation);
        if(!resultBalance.equals(Constants.ZERO)){
            return new ResponseEntity<String>(resultBalance,  HttpStatus.CREATED);
        }
        return new ResponseEntity<String>(resultBalance, HttpStatus.NOT_FOUND);
    }
}
