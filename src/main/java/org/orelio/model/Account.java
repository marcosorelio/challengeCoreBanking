package org.orelio.model;

/**Object Class Account, where Getter and Setter variables will be contained.
 * @author Marcos Orelio
 * @version 1.0-SNAPSHOT
 * @since 28/09/2025
 */
public class Account {
    private String id;
    private Long balance;

    // Generated Getter and Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }
}

