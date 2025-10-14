package org.orelio.model;

/**
 * Represents a banking operation in the ChallengeCoreBanking system.
 * 
 * <p>This class models a banking transaction that can be performed on accounts.
 * It supports three types of operations: deposit, withdraw, and transfer.</p>
 * 
 * <p><strong>Operation Types:</strong></p>
 * <ul>
 *   <li><strong>deposit</strong> - Adds funds to a destination account</li>
 *   <li><strong>withdraw</strong> - Removes funds from an origin account</li>
 *   <li><strong>transfer</strong> - Moves funds from origin to destination account</li>
 * </ul>
 * 
 * <p><strong>Required Fields by Operation Type:</strong></p>
 * <ul>
 *   <li><strong>Deposit:</strong> type, amount, destination</li>
 *   <li><strong>Withdraw:</strong> type, amount, origin</li>
 *   <li><strong>Transfer:</strong> type, amount, origin, destination</li>
 * </ul>
 * 
 * <p><strong>Field Details:</strong></p>
 * <ul>
 *   <li><strong>type:</strong> Operation type (case-insensitive)</li>
 *   <li><strong>amount:</strong> Transaction amount as string (e.g., "100")</li>
 *   <li><strong>origin:</strong> Source account ID for withdraw/transfer operations</li>
 *   <li><strong>destination:</strong> Target account ID for deposit/transfer operations</li>
 * </ul>
 * 
 * @author Marcos Orelio
 * @version 1.0-SNAPSHOT
 * @since 1.0
 * @see org.orelio.facade.ChallengeCoreBankingFacade#operationEvent(Operation)
 */
public class Operation {

    /**
     * The type of banking operation to perform.
     * Valid values: "deposit", "withdraw", "transfer" (case-insensitive).
     */
    private String type;
    
    /**
     * The amount of money involved in the operation.
     * Stored as string to avoid precision issues and parsed as Long.
     */
    private String amount;
    
    /**
     * The ID of the source account for withdraw and transfer operations.
     * Not required for deposit operations.
     */
    private String origin;
    
    /**
     * The ID of the target account for deposit and transfer operations.
     * Not required for withdraw operations.
     */
    private String destination;

    /**
     * Gets the type of banking operation.
     * 
     * @return the operation type (deposit, withdraw, or transfer)
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of banking operation.
     * 
     * @param type the operation type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the amount of money involved in the operation.
     * 
     * @return the operation amount as string
     */
    public String getAmount() {
        return amount;
    }

    /**
     * Sets the amount of money involved in the operation.
     * 
     * @param amount the operation amount as string
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     * Gets the ID of the source account.
     * 
     * @return the origin account ID
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * Sets the ID of the source account.
     * 
     * @param origin the origin account ID to set
     */
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    /**
     * Gets the ID of the target account.
     * 
     * @return the destination account ID
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Sets the ID of the target account.
     * 
     * @param destination the destination account ID to set
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }
}
