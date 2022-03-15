package User.Permission.DAO;

import Database.ConnectDB;
import User.Owner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PermissionDAO {
    public List<String> loadPermission(ConnectDB conDB, Owner owner) {
        try {
            Connection con = conDB.getConnection(conDB.getServer(), conDB.getSchema());
            String sql = "SELECT idpermission,a.sistemaction FROM refuel.permission p\n" +
                    "join sistemaction a on a.idsistemaction = p.idsistempanel\n" +
                    "where p.idowner = ?;";
            PreparedStatement preparedSt = con.prepareStatement(sql);
            preparedSt.setInt(1, owner.getId());
            ResultSet dbResponse = preparedSt.executeQuery();
            List<String> userPermission = new ArrayList<>();
            while (dbResponse.next()) {
                userPermission.add(dbResponse.getString(2));
            }
            conDB.CloseConnection(con);
            return userPermission;
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
            return new ArrayList<>();
        }
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
