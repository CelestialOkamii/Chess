package dataaccess;

import java.sql.*;
import java.util.Properties;

public class DatabaseManager {
    private static String databaseName;
    private static String dbUsername;
    private static String dbPassword;
    private static String connectionUrl;


    public DatabaseManager() throws InputException {
        try {
            createDatabase();
            addTables();
        } catch (InputException ex) {
            throw new InputException(String.valueOf(ex.getErrorCode()), "failed to create database");
        }
    }


    public UserDataAccess getUserData() {
        return new UserDataAccess();
    }

    public AuthDataAccess getAuthData() {
        return new AuthDataAccess();
    }

    public GameDataAccess getGameData() {
        return new GameDataAccess();
    }


    private static final String[] databaseTables = {
            """
        CREATE TABLE IF NOT EXISTS `user_data` (
          `username` varchar(255) NOT NULL,
          `password` varchar(255) DEFAULT NULL,
          `email` varchar(255) DEFAULT NULL,
          PRIMARY KEY (`username`)
        ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
        """,
            """
        CREATE TABLE IF NOT EXISTS `auth_data` (
          `auth_token` varchar(36) NOT NULL,
          `username` varchar(255) NOT NULL,
          PRIMARY KEY (`auth_token`),
          KEY `username_idx` (`username`),
          CONSTRAINT `username` FOREIGN KEY (`username`) REFERENCES `user_data` (`username`) ON DELETE CASCADE
        ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
        """,
            """
        CREATE TABLE IF NOT EXISTS `game_data` (
          `game_id` INT AUTO_INCREMENT NOT NULL,
          `white_username` varchar(255) DEFAULT NULL,
          `black_username` varchar(255) DEFAULT NULL,
          `game_name` varchar(255) NOT NULL,
          `game` text NOT NULL,
          PRIMARY KEY (`game_id`),
          KEY `white_username_idx` (`white_username`),
          KEY `black_username_idx` (`black_username`),
          CONSTRAINT `black_username` FOREIGN KEY (`black_username`) REFERENCES `user_data` (`username`) ON DELETE SET NULL,
          CONSTRAINT `white_username` FOREIGN KEY (`white_username`) REFERENCES `user_data` (`username`) ON DELETE SET NULL
        ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
        """
    };


    static private void addTables() throws InputException {
        for (String table : databaseTables) {
            try (Connection conn = DatabaseManager.getConnection()) {
                PreparedStatement stmt = conn.prepareStatement(table);
                stmt.executeUpdate();
            } catch (SQLException ex) {
                throw new InputException("500", "Error: Internal error");
            }
        }
    }


    /*
     * Load the database information for the db.properties file.
     */
    static {
        loadPropertiesFromResources();
    }

    /**
     * Creates the database if it does not already exist.
     */
    static public void createDatabase() throws InputException {
        var statement = "CREATE DATABASE IF NOT EXISTS " + databaseName;
        try (var conn = DriverManager.getConnection(connectionUrl, dbUsername, dbPassword);
             var preparedStatement = conn.prepareStatement(statement)) {
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new InputException(String.valueOf(ex.getErrorCode()), "failed to create database");
        }
    }

    /**
     * Create a connection to the database and sets the catalog based upon the
     * properties specified in db.properties. Connections to the database should
     * be short-lived, and you must close the connection when you are done with it.
     * The easiest way to do that is with a try-with-resource block.
     * <br/>
     * <code>
     * try (var conn = DatabaseManager.getConnection()) {
     * // execute SQL statements.
     * }
     * </code>
     */
    static Connection getConnection() throws InputException {
        try {
            //do not wrap the following line with a try-with-resources
            var conn = DriverManager.getConnection(connectionUrl, dbUsername, dbPassword);
            conn.setCatalog(databaseName);
            return conn;
        } catch (SQLException ex) {
            throw new InputException(String.valueOf(ex.getErrorCode()), "failed to get connection");
        }
    }

    private static void loadPropertiesFromResources() {
        try (var propStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties")) {
            if (propStream == null) {
                throw new Exception("Unable to load db.properties");
            }
            Properties props = new Properties();
            props.load(propStream);
            loadProperties(props);
        } catch (Exception ex) {
            throw new RuntimeException("unable to process db.properties", ex);
        }
    }

    private static void loadProperties(Properties props) {
        databaseName = props.getProperty("db.name");
        dbUsername = props.getProperty("db.user");
        dbPassword = props.getProperty("db.password");

        var host = props.getProperty("db.host");
        var port = Integer.parseInt(props.getProperty("db.port"));
        connectionUrl = String.format("jdbc:mysql://%s:%d", host, port);
    }
}
