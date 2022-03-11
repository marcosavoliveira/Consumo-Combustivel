package User.Permission;

import Database.ConnectDB;
import User.Owner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class sistemAction {
    public Boolean loadPermission(ConnectDB conDB, Owner owner) {
        Boolean status = true;
        try {
            Connection con = conDB.getConnection(conDB.getServer(), conDB.getSchema());
            String sql = "SELECT idpermission,a.sistemaction FROM refuel.permission p\n" +
                    "join sistemaction a on a.idsistemaction = p.idsistempanel\n" +
                    "where p.idowner = ?;";
            PreparedStatement preparedSt = con.prepareStatement(sql);
            preparedSt.setInt(1, owner.getId());
            ResultSet dbResponse = preparedSt.executeQuery();
            if (dbResponse.next()) {
                return status;
            }
            conDB.CloseConnection(con);
        } catch (SQLException exception) {
            status = false;
            System.err.println(exception.getMessage());
            return status;
        }
        return status;
}
}
