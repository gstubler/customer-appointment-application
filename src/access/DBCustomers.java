package access;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import utils.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * DBCustomers class.
 */
public class DBCustomers {
    /**
     * Connects to database to retrieve all Customers.
     * @return
     */
    public static ObservableList<Customer> getAllCustomers() {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM customers";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("CUSTOMER_ID");
                String name = rs.getString("CUSTOMER_NAME");
                String address = rs.getString("ADDRESS");
                String postalCode = rs.getString("POSTAL_CODE");
                String phoneNumber = rs.getString("PHONE");
                int divisionId = rs.getInt("DIVISION_ID");
                Timestamp dateCreated = rs.getTimestamp("CREATE_DATE");
                Timestamp dateModified = rs.getTimestamp("LAST_UPDATE");
                String createdBy = rs.getString("CREATED_BY");
                String modifiedBy = rs.getString("LAST_UPDATED_BY");

                Customer customer = new Customer(id, name, address, postalCode, phoneNumber, divisionId, dateCreated, dateModified, createdBy, modifiedBy);
                customerList.add(customer);
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customerList;
    }

    /**
     * Connects to database to add new Customer.
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
    public static void addCustomer(String name, String address, String postalCode, String phoneNumber, int divisionId, Timestamp dateCreated, Timestamp dateModified, String createdBy, String modifiedBy) {
        try {
            String sql =    "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phoneNumber);
            ps.setTimestamp(5, dateCreated);
            ps.setString(6, createdBy);
            ps.setTimestamp(7, dateModified);
            ps.setString(8, modifiedBy);
            ps.setInt(9, divisionId);

            ps.execute();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Connects to database to update existing Customer.
     * @param id
     * @param name
     * @param address
     * @param postalCode
     * @param phoneNumber
     * @param divisionId
     * @param dateModified
     * @param modifiedBy
     */
    public static void updateCustomer(int id, String name, String address, String postalCode, String phoneNumber, int divisionId, Timestamp dateModified, String modifiedBy) {
        try {
            String sql =    "UPDATE customers " +
                            "SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? " +
                            "WHERE Customer_ID = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phoneNumber);
            ps.setTimestamp(5, dateModified);
            ps.setString(6, modifiedBy);
            ps.setInt(7, divisionId);
            ps.setInt(8, id);

            System.out.println(ps);

            ps.executeUpdate();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Connects to database to delete Customer.
     * @param customer
     */
    public static void deleteCustomer(Customer customer) {
        try {
            String sql = "DELETE FROM customers WHERE Customer_ID = '" + customer.getId() + "'";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.execute();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
