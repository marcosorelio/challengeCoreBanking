package org.orelio.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.orelio.model.AccountTest;
import org.orelio.model.OperationTest;
import org.orelio.facade.ChallengeCoreBankingFacadeTest;
import org.orelio.util.ChallengeCoreUtilsTest;
import org.orelio.integration.BankingSystemIntegrationTest;
import org.orelio.advanced.AdvancedJUnitTestExamples;

/**
 * Test suite that runs all JUnit test classes
 * Demonstrates how to organize and run multiple test classes together
 * 
 * @author Marcos Orelio
 * @version 1.0-SNAPSHOT
 * @since 28/09/2025
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    // Model layer tests
    AccountTest.class,
    OperationTest.class,
    
    // Service layer tests
    ChallengeCoreBankingFacadeTest.class,
    
    // Utility layer tests
    ChallengeCoreUtilsTest.class,
    
    // Integration tests
    BankingSystemIntegrationTest.class,
    
    // Advanced testing examples
    AdvancedJUnitTestExamples.class
})
public class AllTestsSuite {
    // This class remains empty, it is used only as a holder for the above annotations
}
