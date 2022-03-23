package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;

public class MYSQLConnection implements ConnectDB {

    public String getServer() {
        return "localhost:3307";
    }

    public String getSchema() {
        return "refuel";
    }

    public java.sql.Connection getConnection(String serverName, String database) {
        Connection connection;

        try {
            String url = "jdbc:mysql://" + serverName + "/" + database;
            connection = DriverManager.getConnection(url, "root", "root123");
            return connection;
        } catch (SQLException e) {
            System.err.println("Cannot connect to specified database");
            return null;
        }
    }

    public void CloseConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage()+"Stack: "+ Arrays.toString(e.getStackTrace()));
        }
    }
}
