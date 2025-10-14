package org.orelio.model;

/**
 * Represents a bank account in the ChallengeCoreBanking system.
 * 
 * <p>This class models a simple bank account with an identifier and balance.
 * It provides basic getter and setter methods for account properties and
 * serves as a data transfer object for banking operations.</p>
 * 
 * <p><strong>Account Properties:</strong></p>
 * <ul>
 *   <li><strong>ID:</strong> Unique identifier for the account (String)</li>
 *   <li><strong>Balance:</strong> Current account balance in cents (Long)</li>
 * </ul>
 * 
 * <p><strong>Usage Notes:</strong></p>
 * <ul>
 *   <li>Account ID should be unique within the system</li>
 *   <li>Balance can be negative (overdraft scenarios)</li>
 *   <li>Balance is stored as Long to avoid floating-point precision issues</li>
 *   <li>Both ID and balance can be null</li>
 * </ul>
 * 
 * @author Marcos Orelio
 * @version 1.0-SNAPSHOT
 * @since 1.0
 */
public class Account {
    
    /**
     * Unique identifier for the account.
     * This field serves as the primary key for account identification.
     */
    private String id;
    
    /**
     * Current balance of the account in cents.
     * This field represents the monetary value stored in the account.
     * Can be negative to represent overdraft situations.
     */
    private Long balance;

    /**
     * Gets the unique identifier of the account.
     * 
     * @return the account ID, or null if not set
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the account.
     * 
     * @param id the account ID to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the current balance of the account.
     * 
     * @return the account balance in cents, or null if not set
     */
    public Long getBalance() {
        return balance;
    }

    /**
     * Sets the current balance of the account.
     * 
     * @param balance the account balance in cents to set
     */
    public void setBalance(Long balance) {
        this.balance = balance;
    }
}

