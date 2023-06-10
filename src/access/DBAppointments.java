package access;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import utils.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * DBAppointments class.
 */
public class DBAppointments {
    /**
     * Connects to database to retrieve all Appointments.
     * @return
     */
    public static ObservableList<Appointment> getAllAppointments() {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM appointments";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                Timestamp dateCreated = rs.getTimestamp("Create_Date");
                String createdBy = rs.getString("Created_By");
                Timestamp dateModified = rs.getTimestamp("Last_Update");
                String modifiedBy = rs.getString("Last_Updated_By");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                String contactName = "null";
                for (int i = 0; i < DBContacts.getAllContacts().size(); i++) {
                    if (contactId == DBContacts.getAllContacts().get(i).getId()) {
                        contactName = DBContacts.getAllContacts().get(i).getName();
                    }
                }

                Appointment appointment = new Appointment(id, title, description, location, type, start, end, dateCreated,createdBy, dateModified, modifiedBy, customerId, userId, contactId, contactName);
                appointmentList.add(appointment);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentList;
    }

    /**
     * Connects to database to add new Appointment.
     * @param title
     * @param description
     * @param location
     * @param type
     * @param start
     * @param end
     * @param dateCreated
     * @param dateModified
     * @param createdBy
     * @param modifiedBy
     * @param customerId
     * @param userId
     * @param contactId
     */
    public static void addAppointment(String title, String description, String location, String type, Timestamp start, Timestamp end, Timestamp dateCreated, Timestamp dateModified, String createdBy, String modifiedBy, int customerId, int userId, int contactId) {
        try {
            String sql =    "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, start);
            ps.setTimestamp(6, end);
            ps.setTimestamp(7, dateCreated);
            ps.setString(8, createdBy);
            ps.setTimestamp(9, dateModified);
            ps.setString(10, modifiedBy);
            ps.setInt(11, customerId);
            ps.setInt(12, userId);
            ps.setInt(13, contactId);

            ps.execute();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Connects to database to update existing Appointment.
     * @param id
     * @param title
     * @param description
     * @param location
     * @param type
     * @param start
     * @param end
     * @param dateModified
     * @param modifiedBy
     * @param customerId
     * @param userId
     * @param contactId
     */
    public static void updateAppointment(int id,String title, String description, String location, String type, Timestamp start, Timestamp end,  Timestamp dateModified,  String modifiedBy, int customerId, int userId, int contactId) {
        try {
            String sql =    "UPDATE appointments " +
                            "SET Title = ?, Description= ?, Location = ?, Type = ?, Start = ?, End = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? " +
                            "WHERE Appointment_ID = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, start);
            ps.setTimestamp(6, end);
            ps.setTimestamp(7, dateModified);
            ps.setString(8, modifiedBy);
            ps.setInt(9, customerId);
            ps.setInt(10, userId);
            ps.setInt(11, contactId);
            ps.setInt(12, id);

            ps.execute();

        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Connects to database to delete Appointment.
     * @param appointment
     */
    public static void deleteAppointment(Appointment appointment) {
        try {
            String sql = "DELETE FROM appointments WHERE Appointment_ID = '" + appointment.getId() + "'";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.execute();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
