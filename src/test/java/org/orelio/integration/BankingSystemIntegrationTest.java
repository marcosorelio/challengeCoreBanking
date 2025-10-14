package org.orelio.integration;

import org.junit.Before;
import org.junit.Test;
import org.orelio.facade.ChallengeCoreBankingFacade;
import org.orelio.model.Account;
import org.orelio.model.Constants;
import org.orelio.model.Operation;
import static org.junit.Assert.*;

/**
 * Integration test class for ChallengeCoreBanking system
 * Demonstrates comprehensive integration testing scenarios including:
 * - End-to-end workflow testing
 * - Multi-step operation testing
 * - System state validation
 * - Complex business scenario testing
 * - Error recovery testing
 * 
 * @author Marcos Orelio
 * @version 1.0-SNAPSHOT
 * @since 28/09/2025
 */
public class BankingSystemIntegrationTest {

    private ChallengeCoreBankingFacade facade;

    @Before
    public void setUp() {
        // This method runs before each test method
        facade = new ChallengeCoreBankingFacade();
        // Reset the system state before each test
        facade.resetAccount();
    }

    /**
     * Integration test: Complete banking workflow
     * Tests the entire flow from account creation to complex operations
     */
    @Test
    public void testCompleteBankingWorkflow() {
        // Step 1: Create initial accounts
        Account account1 = new Account();
        account1.setId("ACC001");
        account1.setBalance(5000L);
        facade.createAccount(account1);

        Account account2 = new Account();
        account2.setId("ACC002");
        account2.setBalance(2000L);
        facade.createAccount(account2);

        // Verify initial state
        assertEquals("Account 1 initial balance should be 5000", 
                    Long.valueOf(5000L), facade.getAccount("ACC001").getBalance());
        assertEquals("Account 2 initial balance should be 2000", 
                    Long.valueOf(2000L), facade.getAccount("ACC002").getBalance());

        // Step 2: Perform deposit operation
        Operation deposit = new Operation();
        deposit.setType("deposit");
        deposit.setAmount("1000");
        deposit.setDestination("ACC001");
        String depositResult = facade.operationEvent(deposit);

        // Verify deposit
        assertNotNull("Deposit result should not be null", depositResult);
        assertTrue("Deposit result should contain destination", depositResult.contains("destination"));
        assertEquals("Account 1 balance after deposit should be 6000", 
                    Long.valueOf(6000L), facade.getAccount("ACC001").getBalance());

        // Step 3: Perform withdraw operation
        Operation withdraw = new Operation();
        withdraw.setType("withdraw");
        withdraw.setAmount("500");
        withdraw.setOrigin("ACC001");
        String withdrawResult = facade.operationEvent(withdraw);

        // Verify withdraw
        assertNotNull("Withdraw result should not be null", withdrawResult);
        assertTrue("Withdraw result should contain origin", withdrawResult.contains("origin"));
        assertEquals("Account 1 balance after withdraw should be 5500", 
                    Long.valueOf(5500L), facade.getAccount("ACC001").getBalance());

        // Step 4: Perform transfer operation
        Operation transfer = new Operation();
        transfer.setType("transfer");
        transfer.setAmount("1500");
        transfer.setOrigin("ACC001");
        transfer.setDestination("ACC002");
        String transferResult = facade.operationEvent(transfer);

        // Verify transfer
        assertNotNull("Transfer result should not be null", transferResult);
        assertTrue("Transfer result should contain origin", transferResult.contains("origin"));
        assertTrue("Transfer result should contain destination", transferResult.contains("destination"));
        assertEquals("Account 1 balance after transfer should be 4000", 
                    Long.valueOf(4000L), facade.getAccount("ACC001").getBalance());
        assertEquals("Account 2 balance after transfer should be 3500", 
                    Long.valueOf(3500L), facade.getAccount("ACC002").getBalance());
    }

    /**
     * Integration test: Multiple concurrent operations
     * Tests system behavior with multiple operations on different accounts
     */
    @Test
    public void testMultipleConcurrentOperations() {
        // Setup multiple accounts
        Account account1 = new Account();
        account1.setId("ACC001");
        account1.setBalance(10000L);
        facade.createAccount(account1);

        Account account2 = new Account();
        account2.setId("ACC002");
        account2.setBalance(5000L);
        facade.createAccount(account2);

        Account account3 = new Account();
        account3.setId("ACC003");
        account3.setBalance(3000L);
        facade.createAccount(account3);

        // Perform multiple operations
        // Operation 1: Deposit to account 1
        Operation deposit1 = new Operation();
        deposit1.setType("deposit");
        deposit1.setAmount("2000");
        deposit1.setDestination("ACC001");
        facade.operationEvent(deposit1);

        // Operation 2: Withdraw from account 2
        Operation withdraw1 = new Operation();
        withdraw1.setType("withdraw");
        withdraw1.setAmount("1000");
        withdraw1.setOrigin("ACC002");
        facade.operationEvent(withdraw1);

        // Operation 3: Transfer from account 1 to account 3
        Operation transfer1 = new Operation();
        transfer1.setType("transfer");
        transfer1.setAmount("3000");
        transfer1.setOrigin("ACC001");
        transfer1.setDestination("ACC003");
        facade.operationEvent(transfer1);

        // Operation 4: Transfer from account 3 to account 2
        Operation transfer2 = new Operation();
        transfer2.setType("transfer");
        transfer2.setAmount("500");
        transfer2.setOrigin("ACC003");
        transfer2.setDestination("ACC002");
        facade.operationEvent(transfer2);

        // Verify final balances
        assertEquals("Account 1 final balance should be 9000", 
                    Long.valueOf(9000L), facade.getAccount("ACC001").getBalance());
        assertEquals("Account 2 final balance should be 4500", 
                    Long.valueOf(4500L), facade.getAccount("ACC002").getBalance());
        assertEquals("Account 3 final balance should be 5500", 
                    Long.valueOf(5500L), facade.getAccount("ACC003").getBalance());
    }

    /**
     * Integration test: Account lifecycle management
     * Tests complete account lifecycle from creation to deletion
     */
    @Test
    public void testAccountLifecycleManagement() {
        // Step 1: Create account
        Account newAccount = new Account();
        newAccount.setId("ACC001");
        newAccount.setBalance(0L);
        facade.createAccount(newAccount);

        // Verify account creation
        assertNotNull("Account should be created", facade.getAccount("ACC001"));
        assertEquals("Initial balance should be 0", 
                    Long.valueOf(0L), facade.getAccount("ACC001").getBalance());

        // Step 2: Perform operations to build balance
        Operation deposit1 = new Operation();
        deposit1.setType("deposit");
        deposit1.setAmount("1000");
        deposit1.setDestination("ACC001");
        facade.operationEvent(deposit1);

        Operation deposit2 = new Operation();
        deposit2.setType("deposit");
        deposit2.setAmount("500");
        deposit2.setDestination("ACC001");
        facade.operationEvent(deposit2);

        // Verify balance growth
        assertEquals("Balance should be 1500 after deposits", 
                    Long.valueOf(1500L), facade.getAccount("ACC001").getBalance());

        // Step 3: Perform withdrawals
        Operation withdraw1 = new Operation();
        withdraw1.setType("withdraw");
        withdraw1.setAmount("200");
        withdraw1.setOrigin("ACC001");
        facade.operationEvent(withdraw1);

        // Verify balance reduction
        assertEquals("Balance should be 1300 after withdrawal", 
                    Long.valueOf(1300L), facade.getAccount("ACC001").getBalance());

        // Step 4: Update account directly
        Account updatedAccount = new Account();
        updatedAccount.setId("ACC001");
        updatedAccount.setBalance(2000L);
        facade.updateAccount(updatedAccount, "ACC001");

        // Verify account update
        assertEquals("Balance should be 2000 after update", 
                    Long.valueOf(2000L), facade.getAccount("ACC001").getBalance());

        // Step 5: Reset system (simulating account deletion)
        facade.resetAccount();

        // Verify account deletion
        assertNull("Account should be deleted after reset", facade.getAccount("ACC001"));
    }

    /**
     * Integration test: Error handling and recovery
     * Tests system behavior with invalid operations and recovery
     */
    @Test
    public void testErrorHandlingAndRecovery() {
        // Setup valid account
        Account account = new Account();
        account.setId("ACC001");
        account.setBalance(1000L);
        facade.createAccount(account);

        // Test 1: Invalid operation type
        Operation invalidOp = new Operation();
        invalidOp.setType("invalid");
        invalidOp.setAmount("100");
        invalidOp.setDestination("ACC001");
        String invalidResult = facade.operationEvent(invalidOp);

        assertEquals("Invalid operation should return ZERO", Constants.ZERO, invalidResult);
        assertEquals("Account balance should remain unchanged", 
                    Long.valueOf(1000L), facade.getAccount("ACC001").getBalance());

        // Test 2: Withdraw from non-existent account
        Operation withdrawFromNonExistent = new Operation();
        withdrawFromNonExistent.setType("withdraw");
        withdrawFromNonExistent.setAmount("100");
        withdrawFromNonExistent.setOrigin("NONEXISTENT");
        String withdrawResult = facade.operationEvent(withdrawFromNonExistent);

        assertEquals("Withdraw from non-existent account should return ZERO", Constants.ZERO, withdrawResult);

        // Test 3: Transfer from non-existent account
        Operation transferFromNonExistent = new Operation();
        transferFromNonExistent.setType("transfer");
        transferFromNonExistent.setAmount("100");
        transferFromNonExistent.setOrigin("NONEXISTENT");
        transferFromNonExistent.setDestination("ACC001");
        String transferResult = facade.operationEvent(transferFromNonExistent);

        assertEquals("Transfer from non-existent account should return ZERO", Constants.ZERO, transferResult);

        // Test 4: Recovery - valid operation after errors
        Operation validDeposit = new Operation();
        validDeposit.setType("deposit");
        validDeposit.setAmount("500");
        validDeposit.setDestination("ACC001");
        String validResult = facade.operationEvent(validDeposit);

        assertNotNull("Valid operation after errors should work", validResult);
        assertTrue("Valid operation should contain destination", validResult.contains("destination"));
        assertEquals("Account balance should be updated after valid operation", 
                    Long.valueOf(1500L), facade.getAccount("ACC001").getBalance());
    }

    /**
     * Integration test: Edge case scenarios
     * Tests system behavior with edge cases and boundary conditions
     */
    @Test
    public void testEdgeCaseScenarios() {
        // Test 1: Zero amount operations
        Account account = new Account();
        account.setId("ACC001");
        account.setBalance(1000L);
        facade.createAccount(account);

        Operation zeroDeposit = new Operation();
        zeroDeposit.setType("deposit");
        zeroDeposit.setAmount("0");
        zeroDeposit.setDestination("ACC001");
        facade.operationEvent(zeroDeposit);

        assertEquals("Zero deposit should not change balance", 
                    Long.valueOf(1000L), facade.getAccount("ACC001").getBalance());

        Operation zeroWithdraw = new Operation();
        zeroWithdraw.setType("withdraw");
        zeroWithdraw.setAmount("0");
        zeroWithdraw.setOrigin("ACC001");
        facade.operationEvent(zeroWithdraw);

        assertEquals("Zero withdrawal should not change balance", 
                    Long.valueOf(1000L), facade.getAccount("ACC001").getBalance());

        // Test 2: Large amount operations
        Operation largeDeposit = new Operation();
        largeDeposit.setType("deposit");
        largeDeposit.setAmount("999999999");
        largeDeposit.setDestination("ACC001");
        facade.operationEvent(largeDeposit);

        assertEquals("Large deposit should work", 
                    Long.valueOf(1000000000L), facade.getAccount("ACC001").getBalance());

        // Test 3: Negative balance handling
        Operation largeWithdraw = new Operation();
        largeWithdraw.setType("withdraw");
        largeWithdraw.setAmount("2000000000");
        largeWithdraw.setOrigin("ACC001");
        facade.operationEvent(largeWithdraw);

        assertEquals("Large withdrawal should result in negative balance", 
                    Long.valueOf(-1000000000L), facade.getAccount("ACC001").getBalance());
    }

    /**
     * Integration test: Case sensitivity handling
     * Tests system behavior with different case variations
     */
    @Test
    public void testCaseSensitivityHandling() {
        // Setup account
        Account account = new Account();
        account.setId("ACC001");
        account.setBalance(1000L);
        facade.createAccount(account);

        // Test uppercase operation type
        Operation uppercaseDeposit = new Operation();
        uppercaseDeposit.setType("DEPOSIT");
        uppercaseDeposit.setAmount("500");
        uppercaseDeposit.setDestination("ACC001");
        String uppercaseResult = facade.operationEvent(uppercaseDeposit);

        assertNotNull("Uppercase operation should work", uppercaseResult);
        assertTrue("Uppercase operation should contain destination", uppercaseResult.contains("destination"));

        // Test mixed case operation type
        Operation mixedCaseWithdraw = new Operation();
        mixedCaseWithdraw.setType("WiThDrAw");
        mixedCaseWithdraw.setAmount("200");
        mixedCaseWithdraw.setOrigin("ACC001");
        String mixedCaseResult = facade.operationEvent(mixedCaseWithdraw);

        assertNotNull("Mixed case operation should work", mixedCaseResult);
        assertTrue("Mixed case operation should contain origin", mixedCaseResult.contains("origin"));

        // Verify final balance
        assertEquals("Final balance should reflect case-insensitive operations", 
                    Long.valueOf(1300L), facade.getAccount("ACC001").getBalance());
    }

    /**
     * Integration test: System state consistency
     * Tests that the system maintains consistent state across operations
     */
    @Test
    public void testSystemStateConsistency() {
        // Setup multiple accounts
        Account account1 = new Account();
        account1.setId("ACC001");
        account1.setBalance(1000L);
        facade.createAccount(account1);

        Account account2 = new Account();
        account2.setId("ACC002");
        account2.setBalance(2000L);
        facade.createAccount(account2);

        // Perform operations that should maintain total balance
        long initialTotalBalance = 1000L + 2000L;

        // Transfer from account 2 to account 1
        Operation transfer = new Operation();
        transfer.setType("transfer");
        transfer.setAmount("500");
        transfer.setOrigin("ACC002");
        transfer.setDestination("ACC001");
        facade.operationEvent(transfer);

        // Verify individual balances
        assertEquals("Account 1 balance should be 1500", 
                    Long.valueOf(1500L), facade.getAccount("ACC001").getBalance());
        assertEquals("Account 2 balance should be 1500", 
                    Long.valueOf(1500L), facade.getAccount("ACC002").getBalance());

        // Verify total balance consistency
        long finalTotalBalance = facade.getAccount("ACC001").getBalance() + 
                                facade.getAccount("ACC002").getBalance();
        assertEquals("Total balance should remain consistent", 
                    initialTotalBalance, finalTotalBalance);

        // Perform deposit to verify system can still handle new money
        Operation deposit = new Operation();
        deposit.setType("deposit");
        deposit.setAmount("1000");
        deposit.setDestination("ACC001");
        facade.operationEvent(deposit);

        // Verify final state
        assertEquals("Account 1 final balance should be 2500", 
                    Long.valueOf(2500L), facade.getAccount("ACC001").getBalance());
        assertEquals("Account 2 balance should remain 1500", 
                    Long.valueOf(1500L), facade.getAccount("ACC002").getBalance());
    }

    /**
     * Integration test: Performance under load
     * Tests system performance with multiple rapid operations
     */
    @Test
    public void testPerformanceUnderLoad() {
        // Setup account
        Account account = new Account();
        account.setId("ACC001");
        account.setBalance(100000L);
        facade.createAccount(account);

        // Perform multiple operations rapidly
        long startTime = System.currentTimeMillis();
        
        for (int i = 0; i < 100; i++) {
            Operation deposit = new Operation();
            deposit.setType("deposit");
            deposit.setAmount("100");
            deposit.setDestination("ACC001");
            facade.operationEvent(deposit);

            Operation withdraw = new Operation();
            withdraw.setType("withdraw");
            withdraw.setAmount("50");
            withdraw.setOrigin("ACC001");
            facade.operationEvent(withdraw);
        }
        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        // Verify performance
        assertTrue("Operations should complete within reasonable time", duration < 5000); // 5 seconds
        
        // Verify final balance (100 deposits of 100, 100 withdrawals of 50 = net +5000)
        assertEquals("Final balance should be correct after load test", 
                    Long.valueOf(150000L), facade.getAccount("ACC001").getBalance());
    }
}
