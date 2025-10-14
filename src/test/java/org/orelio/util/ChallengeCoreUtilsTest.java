package org.orelio.util;

import org.junit.Before;
import org.junit.Test;
import org.orelio.model.Account;
import org.orelio.model.Constants;
import static org.junit.Assert.*;

/**
 * JUnit test class for ChallengeCoreUtils
 * Demonstrates comprehensive testing scenarios including:
 * - Static method testing
 * - JSON serialization testing
 * - Error handling testing
 * - Edge case testing
 * - Format string testing
 * 
 * @author Marcos Orelio
 * @version 1.0-SNAPSHOT
 * @since 28/09/2025
 */
public class ChallengeCoreUtilsTest {

    private Account testAccount;

    @Before
    public void setUp() {
        // This method runs before each test method
        testAccount = new Account();
        testAccount.setId("ACC001");
        testAccount.setBalance(1000L);
    }

    /**
     * Test basic JSON serialization without format string
     */
    @Test
    public void testResultJsonAPIWithoutFormat() {
        // Act
        String result = ChallengeCoreUtils.resultJsonAPI(testAccount);

        // Assert
        assertNotNull("JSON result should not be null", result);
        assertTrue("JSON should contain account ID", result.contains("ACC001"));
        assertTrue("JSON should contain balance", result.contains("1000"));
        assertTrue("JSON should be valid format", result.startsWith("{") && result.endsWith("}"));
    }

    /**
     * Test JSON serialization with destination format
     */
    @Test
    public void testResultJsonAPIWithDestinationFormat() {
        // Act
        String result = ChallengeCoreUtils.resultJsonAPI(testAccount, Constants.fmtDestination);

        // Assert
        assertNotNull("JSON result should not be null", result);
        assertTrue("Result should contain destination key", result.contains("destination"));
        assertTrue("Result should contain account ID", result.contains("ACC001"));
        assertTrue("Result should contain balance", result.contains("1000"));
        assertTrue("Result should be valid JSON format", result.startsWith("{") && result.endsWith("}"));
    }

    /**
     * Test JSON serialization with origin format
     */
    @Test
    public void testResultJsonAPIWithOriginFormat() {
        // Act
        String result = ChallengeCoreUtils.resultJsonAPI(testAccount, Constants.fmtOrigin);

        // Assert
        assertNotNull("JSON result should not be null", result);
        assertTrue("Result should contain origin key", result.contains("origin"));
        assertTrue("Result should contain account ID", result.contains("ACC001"));
        assertTrue("Result should contain balance", result.contains("1000"));
        assertTrue("Result should be valid JSON format", result.startsWith("{") && result.endsWith("}"));
    }

    /**
     * Test JSON serialization with origin-destination format
     */
    @Test
    public void testResultJsonAPIWithOriginDestinationFormat() {
        // Act
        String result = ChallengeCoreUtils.resultJsonAPI(testAccount, Constants.fmtOriginDestin);

        // Assert
        assertNotNull("JSON result should not be null", result);
        assertTrue("Result should contain origin key", result.contains("origin"));
        assertTrue("Result should contain destination key", result.contains("destination"));
        assertTrue("Result should contain account ID", result.contains("ACC001"));
        assertTrue("Result should contain balance", result.contains("1000"));
        assertTrue("Result should be valid JSON format", result.startsWith("{") && result.endsWith("}"));
    }

    /**
     * Test JSON serialization with null account
     */
    @Test
    public void testResultJsonAPIWithNullAccount() {
        // Act
        String result = ChallengeCoreUtils.resultJsonAPI(null);

        // Assert
        assertEquals("Null account should return ZERO constant", Constants.ZERO, result);
    }

    /**
     * Test JSON serialization with null account and format
     */
    @Test
    public void testResultJsonAPIWithNullAccountAndFormat() {
        // Act
        String result = ChallengeCoreUtils.resultJsonAPI(null, Constants.fmtDestination);

        // Assert
        assertEquals("Null account with format should return ZERO constant", Constants.ZERO, result);
    }

    /**
     * Test JSON serialization with null format string
     */
    @Test
    public void testResultJsonAPIWithNullFormat() {
        // Act
        String result = ChallengeCoreUtils.resultJsonAPI(testAccount, null);

        // Assert
        assertEquals("Null format should return ZERO constant", Constants.ZERO, result);
    }

    /**
     * Test JSON serialization with account having null ID
     */
    @Test
    public void testResultJsonAPIWithNullId() {
        // Arrange
        Account accountWithNullId = new Account();
        accountWithNullId.setId(null);
        accountWithNullId.setBalance(1000L);

        // Act
        String result = ChallengeCoreUtils.resultJsonAPI(accountWithNullId);

        // Assert
        assertNotNull("JSON result should not be null", result);
        assertTrue("JSON should contain null ID", result.contains("null"));
        assertTrue("JSON should contain balance", result.contains("1000"));
    }

    /**
     * Test JSON serialization with account having null balance
     */
    @Test
    public void testResultJsonAPIWithNullBalance() {
        // Arrange
        Account accountWithNullBalance = new Account();
        accountWithNullBalance.setId("ACC001");
        accountWithNullBalance.setBalance(null);

        // Act
        String result = ChallengeCoreUtils.resultJsonAPI(accountWithNullBalance);

        // Assert
        assertNotNull("JSON result should not be null", result);
        assertTrue("JSON should contain account ID", result.contains("ACC001"));
        assertTrue("JSON should contain null balance", result.contains("null"));
    }

    /**
     * Test JSON serialization with account having zero balance
     */
    @Test
    public void testResultJsonAPIWithZeroBalance() {
        // Arrange
        Account accountWithZeroBalance = new Account();
        accountWithZeroBalance.setId("ACC001");
        accountWithZeroBalance.setBalance(0L);

        // Act
        String result = ChallengeCoreUtils.resultJsonAPI(accountWithZeroBalance);

        // Assert
        assertNotNull("JSON result should not be null", result);
        assertTrue("JSON should contain account ID", result.contains("ACC001"));
        assertTrue("JSON should contain zero balance", result.contains("0"));
    }

    /**
     * Test JSON serialization with account having negative balance
     */
    @Test
    public void testResultJsonAPIWithNegativeBalance() {
        // Arrange
        Account accountWithNegativeBalance = new Account();
        accountWithNegativeBalance.setId("ACC001");
        accountWithNegativeBalance.setBalance(-500L);

        // Act
        String result = ChallengeCoreUtils.resultJsonAPI(accountWithNegativeBalance);

        // Assert
        assertNotNull("JSON result should not be null", result);
        assertTrue("JSON should contain account ID", result.contains("ACC001"));
        assertTrue("JSON should contain negative balance", result.contains("-500"));
    }

    /**
     * Test JSON serialization with account having large balance
     */
    @Test
    public void testResultJsonAPIWithLargeBalance() {
        // Arrange
        Account accountWithLargeBalance = new Account();
        accountWithLargeBalance.setId("ACC001");
        accountWithLargeBalance.setBalance(Long.MAX_VALUE);

        // Act
        String result = ChallengeCoreUtils.resultJsonAPI(accountWithLargeBalance);

        // Assert
        assertNotNull("JSON result should not be null", result);
        assertTrue("JSON should contain account ID", result.contains("ACC001"));
        assertTrue("JSON should contain large balance", result.contains(String.valueOf(Long.MAX_VALUE)));
    }

    /**
     * Test JSON serialization with empty string ID
     */
    @Test
    public void testResultJsonAPIWithEmptyStringId() {
        // Arrange
        Account accountWithEmptyId = new Account();
        accountWithEmptyId.setId("");
        accountWithEmptyId.setBalance(1000L);

        // Act
        String result = ChallengeCoreUtils.resultJsonAPI(accountWithEmptyId);

        // Assert
        assertNotNull("JSON result should not be null", result);
        assertTrue("JSON should contain empty string ID", result.contains("\"\""));
        assertTrue("JSON should contain balance", result.contains("1000"));
    }

    /**
     * Test JSON serialization with special characters in ID
     */
    @Test
    public void testResultJsonAPIWithSpecialCharactersInId() {
        // Arrange
        Account accountWithSpecialId = new Account();
        accountWithSpecialId.setId("ACC-001_@#$");
        accountWithSpecialId.setBalance(1000L);

        // Act
        String result = ChallengeCoreUtils.resultJsonAPI(accountWithSpecialId);

        // Assert
        assertNotNull("JSON result should not be null", result);
        assertTrue("JSON should contain special characters in ID", result.contains("ACC-001_@#$"));
        assertTrue("JSON should contain balance", result.contains("1000"));
    }

    /**
     * Test JSON serialization with whitespace in ID
     */
    @Test
    public void testResultJsonAPIWithWhitespaceInId() {
        // Arrange
        Account accountWithWhitespaceId = new Account();
        accountWithWhitespaceId.setId("  ACC001  ");
        accountWithWhitespaceId.setBalance(1000L);

        // Act
        String result = ChallengeCoreUtils.resultJsonAPI(accountWithWhitespaceId);

        // Assert
        assertNotNull("JSON result should not be null", result);
        assertTrue("JSON should contain whitespace in ID", result.contains("  ACC001  "));
        assertTrue("JSON should contain balance", result.contains("1000"));
    }

    /**
     * Test JSON serialization with custom format string
     */
    @Test
    public void testResultJsonAPIWithCustomFormat() {
        // Arrange
        String customFormat = "{\"account\": %s, \"status\": \"active\"}";

        // Act
        String result = ChallengeCoreUtils.resultJsonAPI(testAccount, customFormat);

        // Assert
        assertNotNull("JSON result should not be null", result);
        assertTrue("Result should contain account key", result.contains("account"));
        assertTrue("Result should contain status key", result.contains("status"));
        assertTrue("Result should contain active value", result.contains("active"));
        assertTrue("Result should contain account ID", result.contains("ACC001"));
        assertTrue("Result should contain balance", result.contains("1000"));
    }

    /**
     * Test JSON serialization with invalid format string
     */
    @Test
    public void testResultJsonAPIWithInvalidFormat() {
        // Arrange
        String invalidFormat = "Invalid format without placeholder";

        // Act
        String result = ChallengeCoreUtils.resultJsonAPI(testAccount, invalidFormat);

        // Assert
        assertEquals("Invalid format should return ZERO constant", Constants.ZERO, result);
    }

    /**
     * Test JSON serialization with multiple placeholders in format
     */
    @Test
    public void testResultJsonAPIWithMultiplePlaceholders() {
        // Arrange
        String multiPlaceholderFormat = "{\"first\": %s, \"second\": %s}";

        // Act
        String result = ChallengeCoreUtils.resultJsonAPI(testAccount, multiPlaceholderFormat);

        // Assert
        assertEquals("Multiple placeholders should return ZERO constant", Constants.ZERO, result);
    }

    /**
     * Test JSON serialization consistency
     */
    @Test
    public void testResultJsonAPIConsistency() {
        // Act - Call multiple times with same account
        String result1 = ChallengeCoreUtils.resultJsonAPI(testAccount);
        String result2 = ChallengeCoreUtils.resultJsonAPI(testAccount);

        // Assert
        assertEquals("Multiple calls should return consistent results", result1, result2);
    }

    /**
     * Test JSON serialization with different accounts
     */
    @Test
    public void testResultJsonAPIWithDifferentAccounts() {
        // Arrange
        Account account1 = new Account();
        account1.setId("ACC001");
        account1.setBalance(1000L);

        Account account2 = new Account();
        account2.setId("ACC002");
        account2.setBalance(2000L);

        // Act
        String result1 = ChallengeCoreUtils.resultJsonAPI(account1);
        String result2 = ChallengeCoreUtils.resultJsonAPI(account2);

        // Assert
        assertNotEquals("Different accounts should produce different results", result1, result2);
        assertTrue("Result 1 should contain ACC001", result1.contains("ACC001"));
        assertTrue("Result 1 should contain 1000", result1.contains("1000"));
        assertTrue("Result 2 should contain ACC002", result2.contains("ACC002"));
        assertTrue("Result 2 should contain 2000", result2.contains("2000"));
    }

    /**
     * Test JSON serialization performance with large data
     */
    @Test
    public void testResultJsonAPIPerformance() {
        // Arrange
        Account largeAccount = new Account();
        largeAccount.setId("ACC001");
        largeAccount.setBalance(Long.MAX_VALUE);

        // Act - Multiple calls to test performance
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            ChallengeCoreUtils.resultJsonAPI(largeAccount);
        }
        long endTime = System.currentTimeMillis();

        // Assert
        long duration = endTime - startTime;
        assertTrue("Serialization should complete within reasonable time", duration < 5000); // 5 seconds
    }

    /**
     * Test JSON serialization with empty account object
     */
    @Test
    public void testResultJsonAPIWithEmptyAccount() {
        // Arrange
        Account emptyAccount = new Account();

        // Act
        String result = ChallengeCoreUtils.resultJsonAPI(emptyAccount);

        // Assert
        assertNotNull("JSON result should not be null", result);
        assertTrue("JSON should contain null values", result.contains("null"));
        assertTrue("JSON should be valid format", result.startsWith("{") && result.endsWith("}"));
    }
}
