package org.orelio.advanced;

import org.junit.*;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.orelio.facade.ChallengeCoreBankingFacade;
import org.orelio.model.Account;
import org.orelio.model.Constants;
import org.orelio.model.Operation;
import org.springframework.core.annotation.Order;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Advanced JUnit test class demonstrating advanced testing concepts
 * Demonstrates:
 * - Parameterized tests
 * - Test rules and exceptions
 * - Timeout testing
 * - Concurrent testing
 * - Test suites
 * - Custom assertions
 * 
 * @author Marcos Orelio
 * @version 1.0-SNAPSHOT
 * @since 28/09/2025
 */
@RunWith(Parameterized.class)
public class AdvancedJUnitTestExamples {

    private ChallengeCoreBankingFacade facade;
    private String operationType;
    private String amount;
    private String expectedResult;

    // Test rule for expected exceptions
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    // Test rule for timeout
    @Rule
    public org.junit.rules.Timeout timeout = new org.junit.rules.Timeout(1000); // 1 second

    // Constructor for parameterized tests
    public AdvancedJUnitTestExamples(String operationType, String amount, String expectedResult) {
        this.operationType = operationType;
        this.amount = amount;
        this.expectedResult = expectedResult;
    }

    // Parameters for parameterized tests
    @Parameters(name = "Operation: {0}, Amount: {1}, Expected: {2}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
            {"deposit", "100", "success"},
            {"withdraw", "50", "success"},
            {"transfer", "200", "success"},
            {"invalid", "100", "failure"},
            {"deposit", "0", "success"},
            {"withdraw", "0", "success"}
        });
    }

    @Before
    public void setUp() {
        facade = new ChallengeCoreBankingFacade();
        facade.resetAccount();
        
        // Setup test account
        Account account = new Account();
        account.setId("ACC001");
        account.setBalance(1000L);
        facade.createAccount(account);
    }

    @After
    public void tearDown() {
        // Cleanup after each test
        facade.resetAccount();
    }

    /**
     * Parameterized test for different operation types and amounts
     */
    @Test
    public void testParameterizedOperations() {
        Operation operation = new Operation();
        operation.setType(operationType);
        operation.setAmount(amount);
        operation.setDestination("ACC001");
        operation.setOrigin("ACC001");

        String result = facade.operationEvent(operation);

        if ("success".equals(expectedResult)) {
            assertNotNull("Result should not be null for successful operation", result);
            assertNotEquals("Result should not be ZERO for successful operation", Constants.ZERO, result);
        } else {
            assertEquals("Result should be ZERO for failed operation", Constants.ZERO, result);
        }
    }

    /**
     * Test demonstrating timeout rule
     */
    @Test
    public void testOperationTimeout() {
        // This test will timeout if it takes longer than 1 second
        Operation operation = new Operation();
        operation.setType("deposit");
        operation.setAmount("100");
        operation.setDestination("ACC001");

        String result = facade.operationEvent(operation);
        assertNotNull("Operation should complete within timeout", result);
    }

    /**
     * Test demonstrating expected exception rule
     */
    @Test
    public void testExpectedException() {
        // This test expects an exception to be thrown
        thrown.expect(NumberFormatException.class);
        
        Operation operation = new Operation();
        operation.setType("deposit");
        operation.setAmount("invalid_number");
        operation.setDestination("ACC001");

        // This should throw NumberFormatException when parsing amount
        facade.operationEvent(operation);
    }

    /**
     * Test demonstrating concurrent operations
     */
    @Test
    public void testConcurrentOperations() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        
        // Submit multiple concurrent operations
        for (int i = 0; i < 10; i++) {
            executor.submit(() -> {
                Operation operation = new Operation();
                operation.setType("deposit");
                operation.setAmount("10");
                operation.setDestination("ACC001");
                return facade.operationEvent(operation);
            });
        }

        executor.shutdown();
        assertTrue("All operations should complete within 5 seconds", 
                  executor.awaitTermination(5, TimeUnit.SECONDS));

        // Verify final balance
        Account account = facade.getAccount("ACC001");
        assertNotNull("Account should exist after concurrent operations", account);
        assertTrue("Balance should be greater than initial", account.getBalance() > 1000L);
    }

    /**
     * Test demonstrating custom assertions
     */
    @Test
    public void testCustomAssertions() {
        Operation operation = new Operation();
        operation.setType("deposit");
        operation.setAmount("500");
        operation.setDestination("ACC001");

        String result = facade.operationEvent(operation);

        // Custom assertion method
        assertValidDepositResult(result, "ACC001", 1500L);
    }

    /**
     * Custom assertion method for deposit results
     */
    private void assertValidDepositResult(String result, String expectedAccountId, Long expectedBalance) {
        assertNotNull("Deposit result should not be null", result);
        assertTrue("Result should contain destination", result.contains("destination"));
        assertTrue("Result should contain account ID", result.contains(expectedAccountId));
        
        Account account = facade.getAccount(expectedAccountId);
        assertNotNull("Account should exist", account);
        assertEquals("Account balance should match expected", expectedBalance, account.getBalance());
    }

    /**
     * Test demonstrating test categories (using JUnit 4 categories)
     */
    @Test
    @Category(FastTests.class)
    public void testFastOperation() {
        Operation operation = new Operation();
        operation.setType("deposit");
        operation.setAmount("100");
        operation.setDestination("ACC001");

        String result = facade.operationEvent(operation);
        assertNotNull("Fast operation should complete quickly", result);
    }

    @Test
    @Category(SlowTests.class)
    public void testSlowOperation() throws InterruptedException {
        // Simulate a slow operation
        Thread.sleep(100);
        
        Operation operation = new Operation();
        operation.setType("deposit");
        operation.setAmount("100");
        operation.setDestination("ACC001");

        String result = facade.operationEvent(operation);
        assertNotNull("Slow operation should still complete", result);
    }

    /**
     * Test demonstrating assumptions
     */
    @Test
    public void testWithAssumptions() {
        // Assume we're running on a system with sufficient memory
        Assume.assumeTrue("Test requires sufficient memory", 
                         Runtime.getRuntime().freeMemory() > 1000000);

        Operation operation = new Operation();
        operation.setType("deposit");
        operation.setAmount("100");
        operation.setDestination("ACC001");

        String result = facade.operationEvent(operation);
        assertNotNull("Operation should succeed with sufficient memory", result);
    }

    /**
     * Test demonstrating test data builders
     */
    @Test
    public void testWithDataBuilder() {
        Operation operation = OperationBuilder.builder()
                .type("deposit")
                .amount("200")
                .destination("ACC001")
                .build();

        String result = facade.operationEvent(operation);
        assertNotNull("Operation built with builder should work", result);
        assertTrue("Result should contain destination", result.contains("destination"));
    }

    /**
     * Test demonstrating mock-like behavior with stubs
     */
    @Test
    public void testWithStubBehavior() {
        // Create a stub account with specific behavior
        Account stubAccount = new Account() {
            @Override
            public Long getBalance() {
                return 9999L; // Always return this value
            }
        };
        stubAccount.setId("STUB001");

        facade.createAccount(stubAccount);

        Account retrievedAccount = facade.getAccount("STUB001");
        assertEquals("Stub account should return stubbed balance", 
                    Long.valueOf(9999L), retrievedAccount.getBalance());
    }

    /**
     * Test demonstrating test fixtures
     */
    @Test
    public void testWithTestFixtures() {
        // Use test fixtures for common test data
        Operation depositOperation = TestFixtures.createDepositOperation("ACC001", "300");
        Operation withdrawOperation = TestFixtures.createWithdrawOperation("ACC001", "100");

        String depositResult = facade.operationEvent(depositOperation);
        String withdrawResult = facade.operationEvent(withdrawOperation);

        assertNotNull("Deposit result should not be null", depositResult);
        assertNotNull("Withdraw result should not be null", withdrawResult);

        Account account = facade.getAccount("ACC001");
        assertEquals("Final balance should be 1200", Long.valueOf(1200L), account.getBalance());
    }

    /**
     * Test demonstrating test lifecycle methods
     */
    @BeforeClass
    public static void setUpClass() {
        System.out.println("Setting up test class - AdvancedJUnitTestExamples");
    }

    @AfterClass
    public static void tearDownClass() {
        System.out.println("Tearing down test class - AdvancedJUnitTestExamples");
    }

    /**
     * Test demonstrating test ordering
     */
    @Test
    @Order(1)
    public void testFirstInOrder() {
        System.out.println("Running first test in order");
        assertTrue("First test should pass", true);
    }

    @Test
    @Order(2)
    public void testSecondInOrder() {
        System.out.println("Running second test in order");
        assertTrue("Second test should pass", true);
    }

    /**
     * Test demonstrating test dependencies
     */
    @Test
    public void testDependentTest() {
        // This test depends on the account being created in setUp
        Account account = facade.getAccount("ACC001");
        assertNotNull("Account should exist from setUp method", account);
        assertEquals("Account should have initial balance", Long.valueOf(1000L), account.getBalance());
    }

    /**
     * Test demonstrating test retry mechanism
     */
    @Test
    public void testWithRetry() {
        int maxRetries = 3;
        int attempt = 0;
        boolean success = false;

        while (attempt < maxRetries && !success) {
            try {
                Operation operation = new Operation();
                operation.setType("deposit");
                operation.setAmount("100");
                operation.setDestination("ACC001");

                String result = facade.operationEvent(operation);
                assertNotNull("Operation should succeed on retry", result);
                success = true;
            } catch (Exception e) {
                attempt++;
                if (attempt >= maxRetries) {
                    fail("Operation failed after " + maxRetries + " attempts");
                }
            }
        }

        assertTrue("Test should eventually succeed", success);
    }

    // Inner classes for test categories
    public interface FastTests {}
    public interface SlowTests {}

    // Inner classes for test utilities
    public static class OperationBuilder {
        private Operation operation = new Operation();

        public static OperationBuilder builder() {
            return new OperationBuilder();
        }

        public OperationBuilder type(String type) {
            operation.setType(type);
            return this;
        }

        public OperationBuilder amount(String amount) {
            operation.setAmount(amount);
            return this;
        }

        public OperationBuilder destination(String destination) {
            operation.setDestination(destination);
            return this;
        }

        public OperationBuilder origin(String origin) {
            operation.setOrigin(origin);
            return this;
        }

        public Operation build() {
            return operation;
        }
    }

    public static class TestFixtures {
        public static Operation createDepositOperation(String destination, String amount) {
            Operation operation = new Operation();
            operation.setType("deposit");
            operation.setAmount(amount);
            operation.setDestination(destination);
            return operation;
        }

        public static Operation createWithdrawOperation(String origin, String amount) {
            Operation operation = new Operation();
            operation.setType("withdraw");
            operation.setAmount(amount);
            operation.setOrigin(origin);
            return operation;
        }

        public static Operation createTransferOperation(String origin, String destination, String amount) {
            Operation operation = new Operation();
            operation.setType("transfer");
            operation.setAmount(amount);
            operation.setOrigin(origin);
            operation.setDestination(destination);
            return operation;
        }
    }
}
