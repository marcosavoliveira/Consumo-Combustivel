package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MYSQLConnection implements ConnectDB{
String server = "localhost:3307";
String schema = "refuel";

    public String getServer() {
        return server;
    }

    public String getSchema() {
        return schema;
    }

    public java.sql.Connection getConnection(String serverName, String database) {
        Connection connection;

        try {
            String url = "jdbc:mysql://" + serverName + "/" + database;
            connection = DriverManager.getConnection(url, "root", "root123");
            return connection;
        } catch (SQLException e) {
            System.out.println("Cannot connect to specified database");
            return null;
        }
    }

    private boolean CloseConnection(Connection connection) {
        try {
            connection.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
