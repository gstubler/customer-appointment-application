package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DBConnection class.
 */
public class DBConnection {
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//wgudb.ucertify.com:3306/";
    private static final String dbName = "WJ0836q";

    private static final String jdbcURL = protocol + vendorName + ipAddress + dbName;

    private static final String mySQLJDBCDriver = "com.mysql.cj.jdbc.Driver";
    private static Connection conn = null;

    private static final String userName = "U0836q";
    private static final String password = "53689201067";

    public static Connection startConnection() {
        try {
            Class.forName(mySQLJDBCDriver);
            conn = DriverManager.getConnection(jdbcURL, userName, password);
            System.out.println("Connection established.");
        }
        catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * Opens database connection.
     * @return
     */
    public static Connection getConnection() {
        return conn;
    }

    /**
     * Closes database connection.
     * @throws SQLException
     */
    public static void closeConnection() throws SQLException {
        try {
            conn.close();
            System.out.println("Connection terminated.");
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
