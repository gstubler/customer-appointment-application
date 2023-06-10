package model;

/**
 * Country class.
 */
public class Country {
    private int id;
    private String name;

    /**
     * Country constructor.
     * @param id
     * @param name
     */
    public Country(int id, String name) {
        this.id = id;
        this.name = name;
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
}
