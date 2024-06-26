package it.hivecampuscompany.hivecampus.manager;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * This class provides a connection to the database.
 * It uses the Singleton pattern to ensure that only one connection is created.
 * The connection is established using the properties file db.properties.
 * The properties file contains the connection URL, driver class name and credentials for the database.
 *
 * @author Marina Sotiropoulos
 */

public class ConnectionManager {
    private static ConnectionManager instance;
    private static Connection connection;
    private final Properties properties;
    private static final Logger LOGGER = Logger.getLogger(ConnectionManager.class.getName());

    // Prevent instantiation of the utility class with a private constructor
    private ConnectionManager() {
        properties = new Properties();
        try (InputStream input = new FileInputStream("properties/db.properties")) {
            properties.load(input);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error loading database properties.", e);
        }
    }

    /**
     * This method returns the instance of the ConnectionManager.
     * If an instance has not been created, it creates a new instance.
     *
     * @return The instance of the ConnectionManager.
     * @author Marina Sotiropoulos
     */

    public static synchronized ConnectionManager getInstance() {
        if (instance == null) {
            instance = new ConnectionManager();
        }
        return instance;
    }

    /**
     * This method returns a connection to the database.
     * If a connection has not been established, it creates a new connection.
     *
     * @return The connection to the database.
     * @author Marina Sotiropoulos
     */
    public synchronized Connection getConnection() {
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
                LOGGER.severe("Error while connecting to the database.");
                System.exit(1);
            }

        }
        return connection;
    }

    /**
     * This method closes the connection to the database.
     *
     * @throws SQLException if an error occurs while closing the connection.
     * @author Marina Sotiropoulos
     */

    public synchronized void closeConnection() throws SQLException {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new SQLException("Error while closing database connection.");
            } finally {
                connection = null; // Reset connection to null
            }
        }
    }
}
