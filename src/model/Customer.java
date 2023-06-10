package model;

import java.sql.Timestamp;

/**
 * Customer class.
 */
public class Customer {
    private int id;
    private String name;
    private String address;
    private String postalCode;
    private String phoneNumber;
    private int divisionId;
    private Timestamp dateCreated;
    private Timestamp dateModified;
    private String createdBy;
    private String modifiedBy;

    /**
     * Customer constructor.
     * @param id
     * @param name
     * @param address
     * @param postalCode
     * @param phoneNumber
     * @param divisionId
     * @param dateCreated
     * @param dateModified
     * @param createdBy
     * @param modifiedBy
     */
    public Customer(int id, String name, String address, String postalCode, String phoneNumber, int divisionId, Timestamp dateCreated, Timestamp dateModified, String createdBy, String modifiedBy) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.divisionId = divisionId;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
        this.createdBy = createdBy;
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
     * Gets address.
     * @return
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets address.
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets postalCode.
     * @return
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets postalCode.
     * @param postalCode
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Gets phoneNumber.
     * @return
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets phoneNumber.
     * @param phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets divisionId.
     * @return
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Sets divisionId.
     * @param divisionId
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
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
