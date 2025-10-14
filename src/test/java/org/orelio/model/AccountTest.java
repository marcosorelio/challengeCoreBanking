package org.orelio.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * JUnit test class for Account model
 * Demonstrates various testing scenarios including:
 * - Basic getter/setter tests
 * - Constructor tests
 * - Edge case testing
 * - Null value handling
 * 
 * @author Marcos Orelio
 * @version 1.0-SNAPSHOT
 * @since 28/09/2025
 */
public class AccountTest {

    private Account account;

    @Before
    public void setUp() {
        // This method runs before each test method
        account = new Account();
    }

    /**
     * Test basic getter and setter functionality for ID
     */
    @Test
    public void testIdGetterAndSetter() {
        // Arrange
        String expectedId = "ACC001";

        // Act
        account.setId(expectedId);
        String actualId = account.getId();

        // Assert
        assertEquals("ID should match the set value", expectedId, actualId);
        assertNotNull("ID should not be null", actualId);
    }

    /**
     * Test basic getter and setter functionality for balance
     */
    @Test
    public void testBalanceGetterAndSetter() {
        // Arrange
        Long expectedBalance = 1000L;

        // Act
        account.setBalance(expectedBalance);
        Long actualBalance = account.getBalance();

        // Assert
        assertEquals("Balance should match the set value", expectedBalance, actualBalance);
        assertNotNull("Balance should not be null", actualBalance);
    }

    /**
     * Test setting null ID
     */
    @Test
    public void testSetIdWithNull() {
        // Act
        account.setId(null);

        // Assert
        assertNull("ID should be null when set to null", account.getId());
    }

    /**
     * Test setting null balance
     */
    @Test
    public void testSetBalanceWithNull() {
        // Act
        account.setBalance(null);

        // Assert
        assertNull("Balance should be null when set to null", account.getBalance());
    }

    /**
     * Test setting zero balance
     */
    @Test
    public void testSetBalanceWithZero() {
        // Arrange
        Long zeroBalance = 0L;

        // Act
        account.setBalance(zeroBalance);

        // Assert
        assertEquals("Balance should be zero", zeroBalance, account.getBalance());
    }

    /**
     * Test setting negative balance
     */
    @Test
    public void testSetBalanceWithNegativeValue() {
        // Arrange
        Long negativeBalance = -500L;

        // Act
        account.setBalance(negativeBalance);

        // Assert
        assertEquals("Balance should accept negative values", negativeBalance, account.getBalance());
    }

    /**
     * Test setting large balance value
     */
    @Test
    public void testSetBalanceWithLargeValue() {
        // Arrange
        Long largeBalance = Long.MAX_VALUE;

        // Act
        account.setBalance(largeBalance);

        // Assert
        assertEquals("Balance should handle large values", largeBalance, account.getBalance());
    }

    /**
     * Test setting empty string ID
     */
    @Test
    public void testSetIdWithEmptyString() {
        // Arrange
        String emptyId = "";

        // Act
        account.setId(emptyId);

        // Assert
        assertEquals("ID should be empty string", emptyId, account.getId());
    }

    /**
     * Test setting whitespace ID
     */
    @Test
    public void testSetIdWithWhitespace() {
        // Arrange
        String whitespaceId = "   ";

        // Act
        account.setId(whitespaceId);

        // Assert
        assertEquals("ID should preserve whitespace", whitespaceId, account.getId());
    }

    /**
     * Test complete account setup
     */
    @Test
    public void testCompleteAccountSetup() {
        // Arrange
        String expectedId = "ACC123";
        Long expectedBalance = 2500L;

        // Act
        account.setId(expectedId);
        account.setBalance(expectedBalance);

        // Assert
        assertEquals("Account ID should be set correctly", expectedId, account.getId());
        assertEquals("Account balance should be set correctly", expectedBalance, account.getBalance());
    }

    /**
     * Test multiple balance updates
     */
    @Test
    public void testMultipleBalanceUpdates() {
        // Arrange
        Long initialBalance = 1000L;
        Long updatedBalance = 2000L;
        Long finalBalance = 1500L;

        // Act & Assert
        account.setBalance(initialBalance);
        assertEquals("Initial balance should be set", initialBalance, account.getBalance());

        account.setBalance(updatedBalance);
        assertEquals("Updated balance should be set", updatedBalance, account.getBalance());

        account.setBalance(finalBalance);
        assertEquals("Final balance should be set", finalBalance, account.getBalance());
    }

    /**
     * Test multiple ID updates
     */
    @Test
    public void testMultipleIdUpdates() {
        // Arrange
        String initialId = "ACC001";
        String updatedId = "ACC002";
        String finalId = "ACC003";

        // Act & Assert
        account.setId(initialId);
        assertEquals("Initial ID should be set", initialId, account.getId());

        account.setId(updatedId);
        assertEquals("Updated ID should be set", updatedId, account.getId());

        account.setId(finalId);
        assertEquals("Final ID should be set", finalId, account.getId());
    }
}
