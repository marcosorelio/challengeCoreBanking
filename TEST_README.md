# JUnit Test Examples for ChallengeCoreBanking

This project contains comprehensive JUnit test examples demonstrating various testing scenarios and best practices for the ChallengeCoreBanking application.

## Test Structure

The test suite is organized into several layers:

### 1. Model Layer Tests
- **`AccountTest.java`** - Tests for the Account model class
- **`OperationTest.java`** - Tests for the Operation model class

### 2. Service Layer Tests
- **`ChallengeCoreBankingFacadeTest.java`** - Tests for the core banking business logic

### 3. Utility Layer Tests
- **`ChallengeCoreUtilsTest.java`** - Tests for utility methods and JSON serialization

### 4. Integration Tests
- **`BankingSystemIntegrationTest.java`** - End-to-end workflow testing

### 5. Advanced Testing Examples
- **`AdvancedJUnitTestExamples.java`** - Demonstrates advanced JUnit concepts

### 6. Test Suite
- **`AllTestsSuite.java`** - Runs all test classes together

## Running the Tests

### Prerequisites
- Java 8 or higher
- Maven 3.6 or higher
- JUnit 4.12 or higher

### Running Individual Test Classes

```bash
# Run specific test class
mvn test -Dtest=AccountTest

# Run specific test method
mvn test -Dtest=AccountTest#testIdGetterAndSetter

# Run tests in a specific package
mvn test -Dtest=org.orelio.model.*
```

### Running All Tests

```bash
# Run all tests
mvn test

# Run the test suite
mvn test -Dtest=AllTestsSuite
```

### Running Tests with Different Profiles

```bash
# Run only fast tests
mvn test -Dtest=AdvancedJUnitTestExamples -Dgroups=FastTests

# Run only slow tests
mvn test -Dtest=AdvancedJUnitTestExamples -Dgroups=SlowTests
```

## Test Categories and Examples

### 1. Basic Unit Tests
- **Getter/Setter Testing** - Validates model class properties
- **Null Value Handling** - Tests behavior with null inputs
- **Edge Case Testing** - Tests boundary conditions and special values

### 2. Business Logic Tests
- **Deposit Operations** - Tests money deposit functionality
- **Withdraw Operations** - Tests money withdrawal functionality
- **Transfer Operations** - Tests money transfer between accounts
- **Error Handling** - Tests invalid operation scenarios

### 3. Integration Tests
- **End-to-End Workflows** - Tests complete banking operations
- **Multi-Step Operations** - Tests complex business scenarios
- **System State Validation** - Ensures data consistency
- **Performance Testing** - Tests system under load

### 4. Advanced Testing Concepts
- **Parameterized Tests** - Tests multiple scenarios with different data
- **Test Rules** - Demonstrates timeout and exception testing
- **Concurrent Testing** - Tests thread safety
- **Custom Assertions** - Shows how to create reusable test methods
- **Test Categories** - Organizes tests by speed or type
- **Test Fixtures** - Demonstrates reusable test data

## Test Coverage Areas

### Model Classes
- ✅ Account creation and validation
- ✅ Operation setup and validation
- ✅ Getter/setter functionality
- ✅ Null value handling
- ✅ Edge cases and boundary conditions

### Business Logic
- ✅ Account management (create, update, retrieve, delete)
- ✅ Deposit operations (new and existing accounts)
- ✅ Withdraw operations (with balance validation)
- ✅ Transfer operations (between accounts)
- ✅ Error handling and validation
- ✅ Case sensitivity handling

### Utility Functions
- ✅ JSON serialization
- ✅ Format string handling
- ✅ Error handling in utilities
- ✅ Performance testing

### Integration Scenarios
- ✅ Complete banking workflows
- ✅ Multiple concurrent operations
- ✅ Account lifecycle management
- ✅ Error recovery scenarios
- ✅ System state consistency
- ✅ Performance under load

## Test Best Practices Demonstrated

### 1. Test Organization
- **Arrange-Act-Assert** pattern
- **Given-When-Then** structure
- **Descriptive test names**
- **Proper test documentation**

### 2. Test Data Management
- **Test fixtures and builders**
- **Parameterized test data**
- **Test data isolation**
- **Cleanup and teardown**

### 3. Assertions
- **Specific assertions** (not just assertTrue)
- **Custom assertion methods**
- **Meaningful error messages**
- **Multiple assertion types**

### 4. Test Lifecycle
- **@Before and @After methods**
- **@BeforeClass and @AfterClass**
- **Test setup and teardown**
- **Resource cleanup**

### 5. Error Testing
- **Exception testing**
- **Error condition validation**
- **Recovery testing**
- **Boundary condition testing**

## Running Tests in IDE

### IntelliJ IDEA
1. Right-click on test class → Run 'TestClassName'
2. Right-click on test method → Run 'testMethodName'
3. Right-click on package → Run 'Tests in package'

### Eclipse
1. Right-click on test class → Run As → JUnit Test
2. Right-click on test method → Run As → JUnit Test
3. Right-click on project → Run As → JUnit Test

### VS Code
1. Use the Java Test Runner extension
2. Click on test icons in the editor
3. Use Command Palette → Java: Run Tests

## Test Reports

After running tests, you can find reports in:
- **Maven Surefire Reports**: `target/surefire-reports/`
- **Test Results**: `target/surefire-reports/TEST-*.xml`
- **Coverage Reports**: `target/site/jacoco/` (if Jacoco is configured)

## Continuous Integration

These tests are designed to run in CI/CD pipelines:

```yaml
# Example GitHub Actions workflow
name: Tests
on: [push, pull_request]
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - name: Set up JDK 8
        uses: actions/setup-java@v2
        with:
          java-version: '8'
          distribution: 'adopt'
      - name: Run tests
        run: mvn test
```

## Troubleshooting

### Common Issues

1. **Tests failing due to missing dependencies**
   ```bash
   mvn clean install
   ```

2. **Tests timing out**
   - Check for infinite loops
   - Verify resource cleanup
   - Increase timeout values if needed

3. **Tests failing due to state pollution**
   - Ensure proper @Before/@After methods
   - Use @BeforeClass/@AfterClass for expensive setup
   - Reset system state between tests

4. **Concurrent test failures**
   - Check for shared state
   - Use proper synchronization
   - Consider test isolation

## Contributing

When adding new tests:
1. Follow the existing naming conventions
2. Add proper documentation
3. Include both positive and negative test cases
4. Ensure tests are independent and can run in any order
5. Add tests to the appropriate test suite

## Resources

- [JUnit 4 Documentation](https://junit.org/junit4/)
- [Maven Surefire Plugin](https://maven.apache.org/surefire/maven-surefire-plugin/)
- [Testing Best Practices](https://martinfowler.com/articles/practical-test-pyramid.html)
