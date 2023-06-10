package model;

/**
 * Division class.
 */
public class Division {
    private int divisionId;
    private String divisionName;
    private int countryId;

    /**
     * Division constructor.
     * @param divisionId
     * @param divisionName
     * @param countryId
     */
    public Division(int divisionId, String divisionName, int countryId) {
        this.divisionId = divisionId;
        this.divisionName = divisionName;
        this.countryId = countryId;
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
     * Gets divisionName.
     * @return
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     * Sets divisionName.
     * @param divisionName
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    /**
     * Gets countryId.
     * @return
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * Sets countryId.
     * @param countryId
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }
}
