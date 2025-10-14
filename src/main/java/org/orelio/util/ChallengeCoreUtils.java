package org.orelio.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.orelio.model.Account;
import org.orelio.model.Constants;

/**
 * Utility class for JSON serialization and formatting in the ChallengeCoreBanking system.
 * 
 * <p>This class provides static methods for converting Account objects to JSON strings
 * and formatting them according to predefined templates. It uses Jackson's ObjectMapper
 * for JSON serialization and handles exceptions gracefully by returning error constants.</p>
 * 
 * <p><strong>Key Features:</strong></p>
 * <ul>
 *   <li>JSON serialization of Account objects</li>
 *   <li>Custom format string support</li>
 *   <li>Exception handling with fallback to error constants</li>
 *   <li>Method overloading for different use cases</li>
 * </ul>
 * 
 * <p><strong>Usage Examples:</strong></p>
 * <pre>{@code
 * // Simple JSON serialization
 * String json = ChallengeCoreUtils.resultJsonAPI(account);
 * 
 * // JSON with custom format
 * String formattedJson = ChallengeCoreUtils.resultJsonAPI(account, Constants.fmtDestination);
 * }</pre>
 * 
 * @author Marcos Orelio
 * @version 1.0-SNAPSHOT
 * @since 1.0
 * @see org.orelio.model.Account
 * @see org.orelio.model.Constants
 * @see com.fasterxml.jackson.databind.ObjectMapper
 */
public class ChallengeCoreUtils {

    /**
     * Converts an Account object to a JSON string using a custom format template.
     * 
     * <p>This method serializes the Account object to JSON and then applies the
     * provided format string using String.format(). The format string should contain
     * a single %s placeholder where the JSON string will be inserted.</p>
     * 
     * <p><strong>Format String Examples:</strong></p>
     * <ul>
     *   <li>{@code "{\"destination\": %s}"} - For destination account responses</li>
     *   <li>{@code "{\"origin\": %s}"} - For origin account responses</li>
     *   <li>{@code "{\"account\": %s, \"status\": \"active\"}"} - Custom format</li>
     * </ul>
     * 
     * @param account the Account object to serialize
     * @param fmt the format string template (must contain %s placeholder)
     * @return formatted JSON string, or {@link Constants#ZERO} if serialization fails
     * @throws IllegalArgumentException if format string is invalid or has wrong number of placeholders
     * @see Constants#fmtDestination
     * @see Constants#fmtOrigin
     * @see Constants#fmtOriginDestin
     */
    public static String resultJsonAPI(Account account, String fmt) {
        try {
            ObjectMapper objMapper = new ObjectMapper();
            // Convert Account object to JSON string
            String jsonStr = objMapper.writeValueAsString(account);
            // Apply format template to create final response
            return String.format(fmt, jsonStr);

        } catch (Exception e) {
            // Return error constant if serialization or formatting fails
            return Constants.ZERO;
        }
    }
    
    /**
     * Converts an Account object to a plain JSON string.
     * 
     * <p>This is an overloaded method that provides simple JSON serialization
     * without any additional formatting. It's useful when you need just the
     * raw JSON representation of the Account object.</p>
     * 
     * <p><strong>Example Output:</strong></p>
     * <pre>{"id":"ACC001","balance":1000}</pre>
     * 
     * @param account the Account object to serialize
     * @return JSON string representation of the account, or {@link Constants#ZERO} if serialization fails
     * @see #resultJsonAPI(Account, String)
     */
    public static String resultJsonAPI(Account account) {
        try {
            ObjectMapper objMapper = new ObjectMapper();
            return objMapper.writeValueAsString(account);

        } catch (Exception e) {
            // Return error constant if serialization fails
            return Constants.ZERO;
        }
    }
}
