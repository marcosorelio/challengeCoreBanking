package org.orelio.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.orelio.model.Account;
import org.orelio.model.Constants;

/**Classe utils to covert Object in JsonString
 * @author Marcos Orelio
 * @version 1.0-SNAPSHOT
 * @since 28/09/2025
 */
public class ChallengeCoreUtils {

    /**@param Account - Return Account
     * @param String - String to Compose Format
     * @return String - Json String.format Result*/
    public static String resultJsonAPI(Account account, String fmt) {
        try {
            ObjectMapper objMapper = new ObjectMapper();
            // Convert Object to jsonString
            String jsonStr = objMapper.writeValueAsString(account);
            // Format creating a new string by embedding
            return String.format(fmt, jsonStr);

        } catch (Exception e) {
            return Constants.ZERO;
        }

    }
    /** Overload method
     * Allows you to create multiple methods with the same name in the same class,
     * @param Account - Return Account
     * @return String - Json String Result*/
    public static String resultJsonAPI(Account account) {
        try {
            ObjectMapper objMapper = new ObjectMapper();
            return objMapper.writeValueAsString(account);

        } catch (Exception e) {
            return Constants.ZERO;
        }

    }
}
