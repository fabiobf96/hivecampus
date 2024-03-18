package it.hivecampuscompany.hivecampus.manager;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {
    private static Connection connection;
    private static final Properties properties;

    private ConnectionManager(){
        //Private Constructor
    }

    static {
        properties = new Properties();
        try (InputStream input = new FileInputStream("properties/db.properties")) {
            properties.load(input);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error loading database properties.", e);
        }
    }

    public static Connection getConnection() {
        if (connection == null) {
            String dbUrl = properties.getProperty("CONNECTION_URL");
            String driver = properties.getProperty("DRIVER_CLASS_NAME");
            String user = properties.getProperty("LOGIN_USER");
            String pass = properties.getProperty("LOGIN_PASS");

            try {
                // Load the JDBC driver
                Class.forName(driver);
                // Create the connection
                connection = DriverManager.getConnection(dbUrl, user, pass);

            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("Connection to the database failed.");
                System.exit(1);
            }

        }
        return connection;
    }

    public static void closeConnection() throws SQLException {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connection to the database closed.");
            } catch (SQLException e) {
                throw new SQLException("Error while closing database connection.");
            } finally {
                connection = null; // Reset connection to null
            }
        }
    }
}
