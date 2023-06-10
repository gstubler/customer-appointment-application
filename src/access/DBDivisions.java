package access;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Division;
import utils.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DBDivisions class.
 */
public class DBDivisions {
    /**
     * Connects to database to retrieve all Divisions.
     * @return
     */
    public static ObservableList<Division> getAllDivisions() {
        ObservableList<Division> divisionList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM first_level_divisions";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int divisionId = rs.getInt("DIVISION_ID");
                String divisionName = rs.getString("DIVISION");
                int countryId = rs.getInt("COUNTRY_ID");

                Division division = new Division(divisionId, divisionName, countryId);
                divisionList.add(division);
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return divisionList;
    }
}
