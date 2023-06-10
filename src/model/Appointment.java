package model;

import java.sql.Timestamp;

/**
 * Appointment class.
 */
public class Appointment {
    private int id;
    private String title;
    private String description;
    private String location;
    private String type;
    private Timestamp start;
    private Timestamp end;
    private Timestamp dateCreated;
    private String createdBy;
    private Timestamp dateModified;
    private String modifiedBy;
    private int customerId;
    private int userId;
    private int contactId;
    private String contactName;

    /**
     * Appointment constructor.
     * @param id
     * @param title
     * @param description
     * @param location
     * @param type
     * @param start
     * @param end
     * @param dateCreated
     * @param createdBy
     * @param dateModified
     * @param modifiedBy
     * @param customerId
     * @param userId
     * @param contactId
     * @param contactName
     */
    public Appointment(int id, String title, String description, String location, String type, Timestamp start, Timestamp end, Timestamp dateCreated, String createdBy, Timestamp dateModified, String modifiedBy, int customerId, int userId, int contactId, String contactName) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.dateCreated = dateCreated;
        this.createdBy = createdBy;
        this.dateModified = dateModified;
        this.modifiedBy = modifiedBy;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
        this.contactName = contactName;
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
     * Gets title.
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets description.
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets location.
     * @return
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets location.
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets type.
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * Sets type.
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets start.
     * @return
     */
    public Timestamp getStart() {
        return start;
    }

    /**
     * Sets start.
     * @param start
     */
    public void setStart(Timestamp start) {
        this.start = start;
    }

    /**
     * Gets end.
     * @return
     */
    public Timestamp getEnd() {
        return end;
    }

    /**
     * Sets end.
     * @param end
     */
    public void setEnd(Timestamp end) {
        this.end = end;
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

    /**
     * Gets customerId.
     * @return
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Sets customerId.
     * @param customerId
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Gets userId.
     * @return
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets userId.
     * @param userId
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets contactId.
     * @return
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * Sets contactId.
     * @param contactId
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * Gets contactName.
     * @return
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Sets contactName.
     * @param contactName
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
}
