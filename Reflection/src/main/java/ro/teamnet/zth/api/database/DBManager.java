package ro.teamnet.zth.api.database;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Author: Ovidiu
 * Date:   4/28/2015
 */
public class DBManager {

    private static final String CONNECTION_STRING =
            "jdbc:mysql://" + DProperties.IP + "/" + DProperties.SCHEMA;

    private DBManager() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    /**
     * Registers the JDBC driver
     */
    private static void registerDriver() {
        try {
            Class.forName(DProperties.DRIVER_CLASS);
        } catch (ClassNotFoundException e) {
            System.out.println("Error registering JDBC driver!");
            e.printStackTrace();
        }
    }

    /**
     * Opens a connection to the database
     * @return database connection
     */
    public static Connection getConnection() {
        registerDriver();

        try {
            return DriverManager.getConnection(CONNECTION_STRING, DProperties.USER, DProperties.PASS);
        } catch (SQLException e) {
            System.out.println("Failed to get database connection!");
            e.printStackTrace();

            return null;
        }
    }

    /**
     * Checks the given database connection
     * @param connection
     * @return <b>true</b> if connection is successful
     */
    public static boolean checkConnection(Connection connection) {
        try {
            connection.createStatement().execute("SELECT 1 FROM dual;");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
