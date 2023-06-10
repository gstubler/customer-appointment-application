package access;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;
import utils.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * DBUsers class.
 */
public class DBUsers {
    /**
     * Connects to database to retrieve all Users.
     * @return
     */
    public static ObservableList<User> getAllUsers() {
        ObservableList<User> userList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM users";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("User_ID");
                String name = rs.getString("User_Name");
                String password = rs.getString("Password");
                Timestamp dateCreated = rs.getTimestamp("Create_Date");
                String createdBy = rs.getString("Created_By");
                Timestamp dateModified = rs.getTimestamp("Last_Update");
                String modifiedBy = rs.getString("Last_Updated_By");

                User user = new User(id, name, password, dateCreated, createdBy, dateModified, modifiedBy);
                userList.add(user);
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userList;
    }
}
