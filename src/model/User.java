package model;

import java.sql.Timestamp;

/**
 * User class.
 */
public class User {
    private int id;
    private String name;
    private String password;
    private Timestamp dateCreated;
    private String createdBy;
    private Timestamp dateModified;
    private String modifiedBy;

    /**
     * User constructor.
     * @param id
     * @param name
     * @param password
     * @param dateCreated
     * @param createdBy
     * @param dateModified
     * @param modifiedBy
     */
    public User(int id, String name, String password, Timestamp dateCreated, String createdBy, Timestamp dateModified, String modifiedBy) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.dateCreated = dateCreated;
        this.createdBy = createdBy;
        this.dateModified = dateModified;
        this.modifiedBy = modifiedBy;
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
     * Gets password.
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets dateCreated.
     * @return
     */
    public Timestamp getDateCreated() {
        return dateCreated;
    }

    /**
     * Sets dateCreated.
     * @param dateCreated
     */
    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * Gets createdBy.
     * @return
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets createdBy.
     * @param createdBy
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Gets dateModified.
     * @return
     */
    public Timestamp getDateModified() {
        return dateModified;
    }

    /**
     * Sets dateModified.
     * @param dateModified
     */
    public void setDateModified(Timestamp dateModified) {
        this.dateModified = dateModified;
    }

    /**
     * Gets modifiedBy.
     * @return
     */
    public String getModifiedBy() {
        return modifiedBy;
    }

    /**
     * Sets modifiedBy.
     * @param modifiedBy
     */
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
}
