#!/bin/bash

# JUnit Test Runner Script for ChallengeCoreBanking
# This script demonstrates how to compile and run the JUnit tests

echo "=== JUnit Test Examples for ChallengeCoreBanking ==="
echo ""

# Check if Java is available
if ! command -v java &> /dev/null; then
    echo "Error: Java is not installed or not in PATH"
    exit 1
fi

echo "Java version:"
java -version
echo ""

# Check if Maven is available
if command -v mvn &> /dev/null; then
    echo "Maven found! Running tests with Maven..."
    echo ""
    
    # Compile the project
    echo "Compiling project..."
    mvn clean compile test-compile
    
    if [ $? -eq 0 ]; then
        echo "Compilation successful!"
        echo ""
        
        # Run specific test classes
        echo "Running Account model tests..."
        mvn test -Dtest=AccountTest
        
        echo ""
        echo "Running Operation model tests..."
        mvn test -Dtest=OperationTest
        
        echo ""
        echo "Running Facade business logic tests..."
        mvn test -Dtest=ChallengeCoreBankingFacadeTest
        
        echo ""
        echo "Running Utility tests..."
        mvn test -Dtest=ChallengeCoreUtilsTest
        
        echo ""
        echo "Running Integration tests..."
        mvn test -Dtest=BankingSystemIntegrationTest
        
        echo ""
        echo "Running Advanced test examples..."
        mvn test -Dtest=AdvancedJUnitTestExamples
        
        echo ""
        echo "Running all tests in suite..."
        mvn test -Dtest=AllTestsSuite
        
    else
        echo "Compilation failed!"
        exit 1
    fi
    
else
    echo "Maven not found. Manual compilation would be required."
    echo ""
    echo "To run these tests manually:"
    echo "1. Install Maven: https://maven.apache.org/install.html"
    echo "2. Run: mvn test"
    echo ""
    echo "Or use your IDE to run the test classes directly."
fi

echo ""
echo "=== Test Examples Created Successfully ==="
echo ""
echo "The following test files have been created:"
echo "1. src/test/java/org/orelio/model/AccountTest.java"
echo "2. src/test/java/org/orelio/model/OperationTest.java"
echo "3. src/test/java/org/orelio/facade/ChallengeCoreBankingFacadeTest.java"
echo "4. src/test/java/org/orelio/util/ChallengeCoreUtilsTest.java"
echo "5. src/test/java/org/orelio/integration/BankingSystemIntegrationTest.java"
echo "6. src/test/java/org/orelio/advanced/AdvancedJUnitTestExamples.java"
echo "7. src/test/java/org/orelio/suite/AllTestsSuite.java"
echo ""
echo "Documentation: TEST_README.md"
echo ""
echo "These examples demonstrate:"
echo "- Basic unit testing"
echo "- Business logic testing"
echo "- Integration testing"
echo "- Advanced JUnit concepts"
echo "- Test organization and best practices"
