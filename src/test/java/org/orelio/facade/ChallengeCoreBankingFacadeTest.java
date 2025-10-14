package org.orelio.facade;

import org.junit.Before;
import org.junit.Test;
import org.orelio.model.Account;
import org.orelio.model.Constants;
import org.orelio.model.Operation;
import static org.junit.Assert.*;

/**
 * JUnit test class for ChallengeCoreBankingFacade
 * Demonstrates comprehensive testing scenarios including:
 * - Business logic testing
 * - Edge case testing
 * - Error handling
 * - Integration testing
 * - Mock testing concepts
 * 
 * @author Marcos Orelio
 * @version 1.0-SNAPSHOT
 * @since 28/09/2025
 */
public class ChallengeCoreBankingFacadeTest {

    private ChallengeCoreBankingFacade facade;

    @Before
    public void setUp() {
        // This method runs before each test method
        facade = new ChallengeCoreBankingFacade();
        // Reset the account map before each test
        facade.resetAccount();
    }

    /**
     * Test creating a new account
     */
    @Test
    public void testCreateAccount() {
        // Arrange
        Account account = new Account();
        account.setId("ACC001");
        account.setBalance(1000L);

        // Act
        Account result = facade.createAccount(account);

        // Assert
        assertNotNull("Created account should not be null", result);
        assertEquals("Account ID should match", "ACC001", result.getId());
        assertEquals("Account balance should match", Long.valueOf(1000L), result.getBalance());
        
        // Verify account is stored
        Account retrievedAccount = facade.getAccount("ACC001");
        assertNotNull("Retrieved account should not be null", retrievedAccount);
        assertEquals("Retrieved account ID should match", "ACC001", retrievedAccount.getId());
        assertEquals("Retrieved account balance should match", Long.valueOf(1000L), retrievedAccount.getBalance());
    }

    /**
     * Test updating an existing account
     */
    @Test
    public void testUpdateAccount() {
        // Arrange - Create initial account
        Account initialAccount = new Account();
        initialAccount.setId("ACC001");
        initialAccount.setBalance(1000L);
        facade.createAccount(initialAccount);

        // Create updated account
        Account updatedAccount = new Account();
        updatedAccount.setId("ACC001");
        updatedAccount.setBalance(2000L);

        // Act
        Account result = facade.updateAccount(updatedAccount, "ACC001");

        // Assert
        assertNotNull("Updated account should not be null", result);
        assertEquals("Updated account ID should match", "ACC001", result.getId());
        assertEquals("Updated account balance should match", Long.valueOf(2000L), result.getBalance());
        
        // Verify account is updated
        Account retrievedAccount = facade.getAccount("ACC001");
        assertEquals("Retrieved account balance should be updated", Long.valueOf(2000L), retrievedAccount.getBalance());
    }

    /**
     * Test getting a non-existent account
     */
    @Test
    public void testGetNonExistentAccount() {
        // Act
        Account result = facade.getAccount("NONEXISTENT");

        // Assert
        assertNull("Non-existent account should return null", result);
    }

    /**
     * Test resetting all accounts
     */
    @Test
    public void testResetAccount() {
        // Arrange - Create some accounts
        Account account1 = new Account();
        account1.setId("ACC001");
        account1.setBalance(1000L);
        facade.createAccount(account1);

        Account account2 = new Account();
        account2.setId("ACC002");
        account2.setBalance(2000L);
        facade.createAccount(account2);

        // Verify accounts exist
        assertNotNull("Account 1 should exist", facade.getAccount("ACC001"));
        assertNotNull("Account 2 should exist", facade.getAccount("ACC002"));

        // Act
        facade.resetAccount();

        // Assert
        assertNull("Account 1 should be removed", facade.getAccount("ACC001"));
        assertNull("Account 2 should be removed", facade.getAccount("ACC002"));
    }

    /**
     * Test deposit operation with new account
     */
    @Test
    public void testDepositOperationNewAccount() {
        // Arrange
        Operation operation = new Operation();
        operation.setType("deposit");
        operation.setAmount("500");
        operation.setDestination("ACC001");

        // Act
        String result = facade.operationEvent(operation);

        // Assert
        assertNotNull("Deposit result should not be null", result);
        assertTrue("Result should contain destination", result.contains("destination"));
        assertTrue("Result should contain ACC001", result.contains("ACC001"));
        assertTrue("Result should contain balance 500", result.contains("500"));
        
        // Verify account was created
        Account account = facade.getAccount("ACC001");
        assertNotNull("Account should be created", account);
        assertEquals("Account balance should be 500", Long.valueOf(500L), account.getBalance());
    }

    /**
     * Test deposit operation with existing account
     */
    @Test
    public void testDepositOperationExistingAccount() {
        // Arrange - Create existing account
        Account existingAccount = new Account();
        existingAccount.setId("ACC001");
        existingAccount.setBalance(1000L);
        facade.createAccount(existingAccount);

        Operation operation = new Operation();
        operation.setType("deposit");
        operation.setAmount("500");
        operation.setDestination("ACC001");

        // Act
        String result = facade.operationEvent(operation);

        // Assert
        assertNotNull("Deposit result should not be null", result);
        assertTrue("Result should contain destination", result.contains("destination"));
        
        // Verify account balance was updated
        Account account = facade.getAccount("ACC001");
        assertEquals("Account balance should be 1500", Long.valueOf(1500L), account.getBalance());
    }

    /**
     * Test withdraw operation with existing account
     */
    @Test
    public void testWithdrawOperationExistingAccount() {
        // Arrange - Create existing account
        Account existingAccount = new Account();
        existingAccount.setId("ACC001");
        existingAccount.setBalance(1000L);
        facade.createAccount(existingAccount);

        Operation operation = new Operation();
        operation.setType("withdraw");
        operation.setAmount("300");
        operation.setOrigin("ACC001");

        // Act
        String result = facade.operationEvent(operation);

        // Assert
        assertNotNull("Withdraw result should not be null", result);
        assertTrue("Result should contain origin", result.contains("origin"));
        
        // Verify account balance was updated
        Account account = facade.getAccount("ACC001");
        assertEquals("Account balance should be 700", Long.valueOf(700L), account.getBalance());
    }

    /**
     * Test withdraw operation with non-existent account
     */
    @Test
    public void testWithdrawOperationNonExistentAccount() {
        // Arrange
        Operation operation = new Operation();
        operation.setType("withdraw");
        operation.setAmount("300");
        operation.setOrigin("NONEXISTENT");

        // Act
        String result = facade.operationEvent(operation);

        // Assert
        assertEquals("Withdraw from non-existent account should return 0", Constants.ZERO, result);
    }

    /**
     * Test transfer operation with both accounts existing
     */
    @Test
    public void testTransferOperationBothAccountsExist() {
        // Arrange - Create origin account
        Account originAccount = new Account();
        originAccount.setId("ACC001");
        originAccount.setBalance(1000L);
        facade.createAccount(originAccount);

        // Create destination account
        Account destAccount = new Account();
        destAccount.setId("ACC002");
        destAccount.setBalance(500L);
        facade.createAccount(destAccount);

        Operation operation = new Operation();
        operation.setType("transfer");
        operation.setAmount("300");
        operation.setOrigin("ACC001");
        operation.setDestination("ACC002");

        // Act
        String result = facade.operationEvent(operation);

        // Assert
        assertNotNull("Transfer result should not be null", result);
        assertTrue("Result should contain origin", result.contains("origin"));
        assertTrue("Result should contain destination", result.contains("destination"));
        
        // Verify both account balances were updated
        Account updatedOrigin = facade.getAccount("ACC001");
        Account updatedDest = facade.getAccount("ACC002");
        
        assertEquals("Origin account balance should be 700", Long.valueOf(700L), updatedOrigin.getBalance());
        assertEquals("Destination account balance should be 800", Long.valueOf(800L), updatedDest.getBalance());
    }

    /**
     * Test transfer operation with new destination account
     */
    @Test
    public void testTransferOperationNewDestinationAccount() {
        // Arrange - Create origin account
        Account originAccount = new Account();
        originAccount.setId("ACC001");
        originAccount.setBalance(1000L);
        facade.createAccount(originAccount);

        Operation operation = new Operation();
        operation.setType("transfer");
        operation.setAmount("300");
        operation.setOrigin("ACC001");
        operation.setDestination("ACC002");

        // Act
        String result = facade.operationEvent(operation);

        // Assert
        assertNotNull("Transfer result should not be null", result);
        assertTrue("Result should contain origin", result.contains("origin"));
        assertTrue("Result should contain destination", result.contains("destination"));
        
        // Verify both account balances
        Account updatedOrigin = facade.getAccount("ACC001");
        Account newDest = facade.getAccount("ACC002");
        
        assertEquals("Origin account balance should be 700", Long.valueOf(700L), updatedOrigin.getBalance());
        assertNotNull("New destination account should be created", newDest);
        assertEquals("New destination account balance should be 300", Long.valueOf(300L), newDest.getBalance());
    }

    /**
     * Test transfer operation with non-existent origin account
     */
    @Test
    public void testTransferOperationNonExistentOrigin() {
        // Arrange
        Operation operation = new Operation();
        operation.setType("transfer");
        operation.setAmount("300");
        operation.setOrigin("NONEXISTENT");
        operation.setDestination("ACC002");

        // Act
        String result = facade.operationEvent(operation);

        // Assert
        assertEquals("Transfer from non-existent origin should return 0", Constants.ZERO, result);
    }

    /**
     * Test invalid operation type
     */
    @Test
    public void testInvalidOperationType() {
        // Arrange
        Operation operation = new Operation();
        operation.setType("invalid");
        operation.setAmount("100");
        operation.setDestination("ACC001");

        // Act
        String result = facade.operationEvent(operation);

        // Assert
        assertEquals("Invalid operation type should return 0", Constants.ZERO, result);
    }

    /**
     * Test case insensitive operation type
     */
    @Test
    public void testCaseInsensitiveOperationType() {
        // Arrange - Create existing account
        Account existingAccount = new Account();
        existingAccount.setId("ACC001");
        existingAccount.setBalance(1000L);
        facade.createAccount(existingAccount);

        Operation operation = new Operation();
        operation.setType("DEPOSIT"); // Uppercase
        operation.setAmount("500");
        operation.setDestination("ACC001");

        // Act
        String result = facade.operationEvent(operation);

        // Assert
        assertNotNull("Uppercase operation type should work", result);
        assertTrue("Result should contain destination", result.contains("destination"));
        
        // Verify account balance was updated
        Account account = facade.getAccount("ACC001");
        assertEquals("Account balance should be 1500", Long.valueOf(1500L), account.getBalance());
    }

    /**
     * Test operation with null values
     */
    @Test
    public void testOperationWithNullValues() {
        // Arrange
        Operation operation = new Operation();
        operation.setType(null);
        operation.setAmount(null);
        operation.setDestination(null);

        // Act
        String result = facade.operationEvent(operation);

        // Assert
        assertEquals("Operation with null values should return 0", Constants.ZERO, result);
    }

    /**
     * Test operation with empty strings
     */
    @Test
    public void testOperationWithEmptyStrings() {
        // Arrange
        Operation operation = new Operation();
        operation.setType("");
        operation.setAmount("");
        operation.setDestination("");

        // Act
        String result = facade.operationEvent(operation);

        // Assert
        assertEquals("Operation with empty strings should return 0", Constants.ZERO, result);
    }

    /**
     * Test large amount operations
     */
    @Test
    public void testLargeAmountOperations() {
        // Arrange - Create existing account
        Account existingAccount = new Account();
        existingAccount.setId("ACC001");
        existingAccount.setBalance(Long.MAX_VALUE);
        facade.createAccount(existingAccount);

        Operation operation = new Operation();
        operation.setType("withdraw");
        operation.setAmount("1000");
        operation.setOrigin("ACC001");

        // Act
        String result = facade.operationEvent(operation);

        // Assert
        assertNotNull("Large amount operation should work", result);
        
        // Verify account balance was updated
        Account account = facade.getAccount("ACC001");
        assertEquals("Account balance should handle large values", 
                    Long.valueOf(Long.MAX_VALUE - 1000), account.getBalance());
    }

    /**
     * Test multiple operations on same account
     */
    @Test
    public void testMultipleOperationsOnSameAccount() {
        // Arrange - Create account
        Account account = new Account();
        account.setId("ACC001");
        account.setBalance(1000L);
        facade.createAccount(account);

        // Act - Multiple deposits
        Operation deposit1 = new Operation();
        deposit1.setType("deposit");
        deposit1.setAmount("200");
        deposit1.setDestination("ACC001");
        facade.operationEvent(deposit1);

        Operation deposit2 = new Operation();
        deposit2.setType("deposit");
        deposit2.setAmount("300");
        deposit2.setDestination("ACC001");
        facade.operationEvent(deposit2);

        // Act - Withdraw
        Operation withdraw = new Operation();
        withdraw.setType("withdraw");
        withdraw.setAmount("150");
        withdraw.setOrigin("ACC001");
        facade.operationEvent(withdraw);

        // Assert
        Account finalAccount = facade.getAccount("ACC001");
        assertEquals("Final balance should be 1350", Long.valueOf(1350L), finalAccount.getBalance());
    }

    /**
     * Test zero amount operations
     */
    @Test
    public void testZeroAmountOperations() {
        // Arrange - Create existing account
        Account existingAccount = new Account();
        existingAccount.setId("ACC001");
        existingAccount.setBalance(1000L);
        facade.createAccount(existingAccount);

        Operation operation = new Operation();
        operation.setType("deposit");
        operation.setAmount("0");
        operation.setDestination("ACC001");

        // Act
        String result = facade.operationEvent(operation);

        // Assert
        assertNotNull("Zero amount operation should work", result);
        
        // Verify account balance remains the same
        Account account = facade.getAccount("ACC001");
        assertEquals("Account balance should remain 1000", Long.valueOf(1000L), account.getBalance());
    }
}
