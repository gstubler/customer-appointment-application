package access;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import utils.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DBCountries class.
 */
public class DBCountries {
    /**
     * Connects to database to retrieve all Countries.
     * @return
     */
    public static ObservableList<Country> getAllCountries() {
        ObservableList<Country> countryList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM countries";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int countryId = rs.getInt("COUNTRY_ID");
                String countryName = rs.getString("COUNTRY");

                Country country = new Country (countryId, countryName);
                countryList.add(country);
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return countryList;
    }
}
