package Database;

import java.sql.Connection;

public interface ConnectDB {
    java.sql.Connection getConnection(String serverName, String database);

    String getServer();

    String getSchema();

    default void CloseConnection(Connection connection) {
    }
}
