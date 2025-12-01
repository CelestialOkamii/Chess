package dataaccess;

import java.sql.*;
import java.util.*;

public class DatabaseManager {
    private static String databaseName;
    private static String dbUsername;
    private static String dbPassword;
    private static String connectionUrl;


    public DatabaseManager() throws DataAccessException {
        try {
            createDatabase();
        } catch (DataAccessException ex) {
            throw new DataAccessException("failed to create database", ex);
        }
    }

    /*
     * Load the database information for the db.properties file.
     */
    static {
        loadPropertiesFromResources();
    }


    public UserDatabaseAccess getUserData() {
        return new UserDatabaseAccess();
    }

    public AuthDatabaseAccess getAuthData() {
        return new AuthDatabaseAccess();
    }

    public GameDatabaseAccess getGameData() {
        return new GameDatabaseAccess();
    }


    private static final String[] databaseTables = {
            """
        CREATE TABLE IF NOT EXISTS `user_data` (
          `username` varchar(255) NOT NULL,
          `password` varchar(255) DEFAULT NULL,
          `email` varchar(255) DEFAULT NULL,
          PRIMARY KEY (`username`),
          UNIQUE KEY `username_UNIQUE` (`username`)
        ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
        """,
            """
        CREATE TABLE IF NOT EXISTS `auth_data` (
          `auth_token` varchar(36) NOT NULL,
          `username` varchar(255) DEFAULT NULL,
          PRIMARY KEY (`auth_token`),
          UNIQUE KEY `auth_token_UNIQUE` (`auth_token`),
          KEY `username_idx` (`username`),
          CONSTRAINT `username` FOREIGN KEY (`username`) REFERENCES `user_data` (`username`)
        ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
        """,
            """
        CREATE TABLE IF NOT EXISTS `game_data` (
          `game_id` int NOT NULL,
          `white_username` varchar(255) DEFAULT NULL,
          `black_username` varchar(255) DEFAULT NULL,
          `game_name` varchar(255) DEFAULT NULL,
          `game` json NOT NULL,
          PRIMARY KEY (`game_id`),
          UNIQUE KEY `game_id_UNIQUE` (`game_id`),
          KEY `white_username_idx` (`white_username`),
          KEY `black_username_idx` (`black_username`),
          CONSTRAINT `black_username` FOREIGN KEY (`black_username`) REFERENCES `user_data` (`username`),
          CONSTRAINT `white_username` FOREIGN KEY (`white_username`) REFERENCES `user_data` (`username`)
        ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
        """
    };


    /**
     * Creates the database if it does not already exist.
     */
    static public void createDatabase() throws DataAccessException {
        var statement = "CREATE DATABASE IF NOT EXISTS " + databaseName;
        try (var conn = DriverManager.getConnection(connectionUrl, dbUsername, dbPassword);
             var preparedStatement = conn.prepareStatement(statement)) {
            preparedStatement.executeUpdate();
            for (String table : databaseTables) {
                try (var stmt = conn.prepareStatement(table)) {
                    stmt.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException("failed to create database", ex);
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
    static Connection getConnection() throws DataAccessException {
        try {
            //do not wrap the following line with a try-with-resources
            var conn = DriverManager.getConnection(connectionUrl, dbUsername, dbPassword);
            conn.setCatalog(databaseName);
            return conn;
        } catch (SQLException ex) {
            throw new DataAccessException("failed to get connection", ex);
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
