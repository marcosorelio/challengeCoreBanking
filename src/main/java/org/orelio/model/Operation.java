package org.orelio.model;

/**Object Class Operation, where Getter and Setter variables will be contained.
 * @author Marcos Orelio
 * @version 1.0-SNAPSHOT
 * @since 28/09/2025
 */
public class Operation {

    private String type;
    private String amount;
    private String origin;
    private String destination;

    // Generated Getter and Setter
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
