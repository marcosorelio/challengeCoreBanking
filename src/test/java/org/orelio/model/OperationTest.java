package org.orelio.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * JUnit test class for Operation model
 * Demonstrates comprehensive testing scenarios including:
 * - Basic getter/setter tests
 * - Validation testing
 * - Edge case testing
 * - Business logic validation
 * 
 * @author Marcos Orelio
 * @version 1.0-SNAPSHOT
 * @since 28/09/2025
 */
public class OperationTest {

    private Operation operation;

    @Before
    public void setUp() {
        // This method runs before each test method
        operation = new Operation();
    }

    /**
     * Test basic getter and setter functionality for type
     */
    @Test
    public void testTypeGetterAndSetter() {
        // Arrange
        String expectedType = "deposit";

        // Act
        operation.setType(expectedType);
        String actualType = operation.getType();

        // Assert
        assertEquals("Type should match the set value", expectedType, actualType);
        assertNotNull("Type should not be null", actualType);
    }

    /**
     * Test basic getter and setter functionality for amount
     */
    @Test
    public void testAmountGetterAndSetter() {
        // Arrange
        String expectedAmount = "1000";

        // Act
        operation.setAmount(expectedAmount);
        String actualAmount = operation.getAmount();

        // Assert
        assertEquals("Amount should match the set value", expectedAmount, actualAmount);
        assertNotNull("Amount should not be null", actualAmount);
    }

    /**
     * Test basic getter and setter functionality for origin
     */
    @Test
    public void testOriginGetterAndSetter() {
        // Arrange
        String expectedOrigin = "ACC001";

        // Act
        operation.setOrigin(expectedOrigin);
        String actualOrigin = operation.getOrigin();

        // Assert
        assertEquals("Origin should match the set value", expectedOrigin, actualOrigin);
        assertNotNull("Origin should not be null", actualOrigin);
    }

    /**
     * Test basic getter and setter functionality for destination
     */
    @Test
    public void testDestinationGetterAndSetter() {
        // Arrange
        String expectedDestination = "ACC002";

        // Act
        operation.setDestination(expectedDestination);
        String actualDestination = operation.getDestination();

        // Assert
        assertEquals("Destination should match the set value", expectedDestination, actualDestination);
        assertNotNull("Destination should not be null", actualDestination);
    }

    /**
     * Test setting null values for all fields
     */
    @Test
    public void testSetAllFieldsWithNull() {
        // Act
        operation.setType(null);
        operation.setAmount(null);
        operation.setOrigin(null);
        operation.setDestination(null);

        // Assert
        assertNull("Type should be null", operation.getType());
        assertNull("Amount should be null", operation.getAmount());
        assertNull("Origin should be null", operation.getOrigin());
        assertNull("Destination should be null", operation.getDestination());
    }

    /**
     * Test deposit operation setup
     */
    @Test
    public void testDepositOperationSetup() {
        // Arrange
        String type = "deposit";
        String amount = "500";
        String destination = "ACC001";

        // Act
        operation.setType(type);
        operation.setAmount(amount);
        operation.setDestination(destination);

        // Assert
        assertEquals("Type should be deposit", type, operation.getType());
        assertEquals("Amount should be 500", amount, operation.getAmount());
        assertEquals("Destination should be ACC001", destination, operation.getDestination());
        assertNull("Origin should be null for deposit", operation.getOrigin());
    }

    /**
     * Test withdraw operation setup
     */
    @Test
    public void testWithdrawOperationSetup() {
        // Arrange
        String type = "withdraw";
        String amount = "200";
        String origin = "ACC001";

        // Act
        operation.setType(type);
        operation.setAmount(amount);
        operation.setOrigin(origin);

        // Assert
        assertEquals("Type should be withdraw", type, operation.getType());
        assertEquals("Amount should be 200", amount, operation.getAmount());
        assertEquals("Origin should be ACC001", origin, operation.getOrigin());
        assertNull("Destination should be null for withdraw", operation.getDestination());
    }

    /**
     * Test transfer operation setup
     */
    @Test
    public void testTransferOperationSetup() {
        // Arrange
        String type = "transfer";
        String amount = "1000";
        String origin = "ACC001";
        String destination = "ACC002";

        // Act
        operation.setType(type);
        operation.setAmount(amount);
        operation.setOrigin(origin);
        operation.setDestination(destination);

        // Assert
        assertEquals("Type should be transfer", type, operation.getType());
        assertEquals("Amount should be 1000", amount, operation.getAmount());
        assertEquals("Origin should be ACC001", origin, operation.getOrigin());
        assertEquals("Destination should be ACC002", destination, operation.getDestination());
    }

    /**
     * Test case insensitive type handling
     */
    @Test
    public void testCaseInsensitiveTypeHandling() {
        // Test uppercase
        operation.setType("DEPOSIT");
        assertEquals("Type should handle uppercase", "DEPOSIT", operation.getType());

        // Test mixed case
        operation.setType("TrAnSfEr");
        assertEquals("Type should handle mixed case", "TrAnSfEr", operation.getType());

        // Test lowercase
        operation.setType("withdraw");
        assertEquals("Type should handle lowercase", "withdraw", operation.getType());
    }

    /**
     * Test numeric amount validation
     */
    @Test
    public void testNumericAmountValidation() {
        // Test valid numeric amounts
        String[] validAmounts = {"0", "1", "100", "1000", "999999", "0.50"};

        for (String amount : validAmounts) {
            operation.setAmount(amount);
            assertEquals("Amount should accept valid numeric value: " + amount, 
                        amount, operation.getAmount());
        }
    }

    /**
     * Test non-numeric amount handling
     */
    @Test
    public void testNonNumericAmountHandling() {
        // Test non-numeric amounts (should still be accepted as strings)
        String[] nonNumericAmounts = {"abc", "invalid", "amount123", "test"};

        for (String amount : nonNumericAmounts) {
            operation.setAmount(amount);
            assertEquals("Amount should accept non-numeric value: " + amount, 
                        amount, operation.getAmount());
        }
    }

    /**
     * Test empty string handling
     */
    @Test
    public void testEmptyStringHandling() {
        // Act
        operation.setType("");
        operation.setAmount("");
        operation.setOrigin("");
        operation.setDestination("");

        // Assert
        assertEquals("Type should be empty string", "", operation.getType());
        assertEquals("Amount should be empty string", "", operation.getAmount());
        assertEquals("Origin should be empty string", "", operation.getOrigin());
        assertEquals("Destination should be empty string", "", operation.getDestination());
    }

    /**
     * Test whitespace handling
     */
    @Test
    public void testWhitespaceHandling() {
        // Arrange
        String whitespaceValue = "   ";

        // Act
        operation.setType(whitespaceValue);
        operation.setAmount(whitespaceValue);
        operation.setOrigin(whitespaceValue);
        operation.setDestination(whitespaceValue);

        // Assert
        assertEquals("Type should preserve whitespace", whitespaceValue, operation.getType());
        assertEquals("Amount should preserve whitespace", whitespaceValue, operation.getAmount());
        assertEquals("Origin should preserve whitespace", whitespaceValue, operation.getOrigin());
        assertEquals("Destination should preserve whitespace", whitespaceValue, operation.getDestination());
    }

    /**
     * Test complete operation setup
     */
    @Test
    public void testCompleteOperationSetup() {
        // Arrange
        String type = "transfer";
        String amount = "2500";
        String origin = "ACC001";
        String destination = "ACC002";

        // Act
        operation.setType(type);
        operation.setAmount(amount);
        operation.setOrigin(origin);
        operation.setDestination(destination);

        // Assert
        assertEquals("Complete operation type should be set", type, operation.getType());
        assertEquals("Complete operation amount should be set", amount, operation.getAmount());
        assertEquals("Complete operation origin should be set", origin, operation.getOrigin());
        assertEquals("Complete operation destination should be set", destination, operation.getDestination());
    }

    /**
     * Test multiple field updates
     */
    @Test
    public void testMultipleFieldUpdates() {
        // Initial setup
        operation.setType("deposit");
        operation.setAmount("100");
        operation.setOrigin("ACC001");
        operation.setDestination("ACC002");

        // Verify initial values
        assertEquals("Initial type should be deposit", "deposit", operation.getType());
        assertEquals("Initial amount should be 100", "100", operation.getAmount());

        // Update values
        operation.setType("withdraw");
        operation.setAmount("200");

        // Verify updated values
        assertEquals("Updated type should be withdraw", "withdraw", operation.getType());
        assertEquals("Updated amount should be 200", "200", operation.getAmount());
        assertEquals("Origin should remain unchanged", "ACC001", operation.getOrigin());
        assertEquals("Destination should remain unchanged", "ACC002", operation.getDestination());
    }

    /**
     * Test special characters in fields
     */
    @Test
    public void testSpecialCharactersInFields() {
        // Arrange
        String specialType = "deposit@#$";
        String specialAmount = "100.50";
        String specialOrigin = "ACC-001";
        String specialDestination = "ACC_002";

        // Act
        operation.setType(specialType);
        operation.setAmount(specialAmount);
        operation.setOrigin(specialOrigin);
        operation.setDestination(specialDestination);

        // Assert
        assertEquals("Type should handle special characters", specialType, operation.getType());
        assertEquals("Amount should handle decimal values", specialAmount, operation.getAmount());
        assertEquals("Origin should handle special characters", specialOrigin, operation.getOrigin());
        assertEquals("Destination should handle special characters", specialDestination, operation.getDestination());
    }
}
