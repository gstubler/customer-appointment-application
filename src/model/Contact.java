package model;

/**
 * Contact class.
 */
public class Contact {
    private int id;
    private String name;
    private String email;

    /**
     * Contact constructor.
     * @param id
     * @param name
     * @param email
     */
    public Contact(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /**
     * Gets id.
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets name.
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets email.
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
