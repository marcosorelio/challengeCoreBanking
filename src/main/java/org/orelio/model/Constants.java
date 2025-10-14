package org.orelio.model;

/**
 * Constants class containing fixed values used throughout the ChallengeCoreBanking system.
 * 
 * <p>This class defines string constants for operation types and JSON format templates
 * used in API responses. All constants are public, static, and final to ensure
 * immutability and global accessibility.</p>
 * 
 * <p><strong>Operation Type Constants:</strong></p>
 * <ul>
 *   <li>{@link #DEPOSIT} - Deposit operation type</li>
 *   <li>{@link #WITHDRAW} - Withdrawal operation type</li>
 *   <li>{@link #TRANSFER} - Transfer operation type</li>
 * </ul>
 * 
 * <p><strong>Response Format Constants:</strong></p>
 * <ul>
 *   <li>{@link #fmtDestination} - JSON format for destination account responses</li>
 *   <li>{@link #fmtOrigin} - JSON format for origin account responses</li>
 *   <li>{@link #fmtOriginDestin} - JSON format for transfer responses with both accounts</li>
 * </ul>
 * 
 * <p><strong>Error Constants:</strong></p>
 * <ul>
 *   <li>{@link #ZERO} - Error response value for failed operations</li>
 * </ul>
 * 
 * @author Marcos Orelio
 * @version 1.0-SNAPSHOT
 * @since 1.0
 * @see org.orelio.facade.ChallengeCoreBankingFacade#operationEvent(Operation)
 */
public class Constants {

    /**
     * Constant for deposit operation type.
     * Used to identify deposit operations in the banking system.
     */
    public static final String DEPOSIT = "deposit";
    
    /**
     * Constant for withdrawal operation type.
     * Used to identify withdrawal operations in the banking system.
     */
    public static final String WITHDRAW = "withdraw";
    
    /**
     * Constant for transfer operation type.
     * Used to identify transfer operations in the banking system.
     */
    public static final String TRANSFER = "transfer";
    
    /**
     * Constant for error response value.
     * Returned when operations fail or accounts are not found.
     */
    public static final String ZERO = "0";

    /**
     * JSON format string for destination account responses.
     * Used in deposit operations to format the response.
     * Format: {"destination": %s}
     */
    public static final String fmtDestination = "{\"destination\": %s}";
    
    /**
     * JSON format string for origin account responses.
     * Used in withdrawal operations to format the response.
     * Format: {"origin": %s}
     */
    public static final String fmtOrigin = "{\"origin\": %s}";
    
    /**
     * JSON format string for transfer operation responses.
     * Used in transfer operations to format responses with both accounts.
     * Format: {"origin": %s, "destination": %s}
     */
    public static final String fmtOriginDestin = "{\"origin\": %s, \"destination\": %s}";
}
