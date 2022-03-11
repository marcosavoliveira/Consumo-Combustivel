package User.Permission;

import Database.ConnectDB;
import User.Owner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Permission {
    public Boolean loadPermission(ConnectDB conDB, Owner owner) {
        try {
            Connection con = conDB.getConnection(conDB.getServer(), conDB.getSchema());
            String sql = "select idOwner from Owner where login=? and password=?";
            PreparedStatement preparedSt = con.prepareStatement(sql);
            preparedSt.setString(1, owner.getLogin());
            preparedSt.setString(2, owner.getPassword());
            ResultSet dbResponse = preparedSt.executeQuery();
            if (dbResponse.next()) {
                return true;
            }
            conDB.CloseConnection(con);
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
            return false;
        }
        return true;
    }
        public Boolean savePermission(ConnectDB conDB, Owner owner) {
            try {
                Connection con = conDB.getConnection(conDB.getServer(), conDB.getSchema());
                String sql = "select idOwner from Owner where login=? and password=?";
                PreparedStatement preparedSt = con.prepareStatement(sql);
                preparedSt.setString(1, owner.getLogin());
                preparedSt.setString(2, owner.getPassword());
                ResultSet dbResponse = preparedSt.executeQuery();
                if (dbResponse.next()) {
                    return true;
                }
                conDB.CloseConnection(con);
            } catch (SQLException exception) {
                System.err.println(exception.getMessage());
                return false;
            }
        return false;
    }
}
