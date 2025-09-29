package org.orelio.model;

/**Constants Class, type String Final
 * fixed values to be used throughout the project.
 * @author Marcos Orelio
 * @version 1.0-SNAPSHOT
 * @since 28/09/2025
 */
public class Constants {

    public static final String DEPOSIT = "deposit";
    public static final String WITHDRAW = "withdraw";
    public static final String TRANSFER = "transfer";
    public static final String ZERO = "0";

    // String format creating a new string by embedding
    public static final String fmtDestination = "{\"destination\": %s}";
    public static final String fmtOrigin = "{\"origin\": %s}";
    public static final String fmtOriginDestin = "{\"origin\": %s, \"destination\": %s}";
}
