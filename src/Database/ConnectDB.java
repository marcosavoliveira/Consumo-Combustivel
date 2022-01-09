package Database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public interface ConnectDB {
    java.sql.Connection getConnection(String serverName, String database);
}
