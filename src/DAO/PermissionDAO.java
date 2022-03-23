package DAO;

import Database.ConnectDB;
import User.Owner;
import User.Permission.Permission;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PermissionDAO {
    private final ConnectDB conDB;
    private String sql;

    public PermissionDAO(ConnectDB conDB){
        this.conDB = conDB;
    }

    private PreparedStatement setupConnection (String sql) throws SQLException {
        Connection con = conDB.getConnection(conDB.getServer(), conDB.getSchema());
        return con.prepareStatement(sql);
    }

    public List<String> loadPermission(Owner owner) {
        try {
            sql = "SELECT idPermission,a.systemAction FROM refuel.permission p\n" +
                    "join systemAction a on a.idSystemAction = p.idSystemPanel\n" +
                    "where p.idOwner = ?;";
            PreparedStatement preparedSt = setupConnection(sql);
            preparedSt.setInt(1, owner.getId());
            ResultSet dbResponse = preparedSt.executeQuery();
            List<String> userPermission = new ArrayList<>();
            while (dbResponse.next()) {
                userPermission.add(dbResponse.getString(2));
            }
            conDB.CloseConnection(preparedSt.getConnection());
            return userPermission;
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
            return new ArrayList<>();
        }
    }

    public List<String> loadSystemActions() {
        try {
            sql = "ELECT systemAction FROM refuel.systemAction;";
            PreparedStatement preparedSt = setupConnection(sql);
            ResultSet dbResponse = preparedSt.executeQuery();
            List<String> systemActions = new ArrayList<>();
            while (dbResponse.next()) {
                systemActions.add(dbResponse.getString(1));
            }
            conDB.CloseConnection(preparedSt.getConnection());
            return systemActions;
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
            return new ArrayList<>();
        }
    }

    public Boolean existsPermission(Owner owner, Permission permission) {
        boolean status = false;
        try {
            sql = "SELECT count(`idPermission`) FROM `refuel`.`permission` where idOwner = ? and idSystemPanel = ?;";
            PreparedStatement preparedSt = setupConnection(sql);
            preparedSt.setInt(1, owner.getId());
            preparedSt.setInt(2, permission.getIdSystemAction());
            ResultSet dbResponse = preparedSt.executeQuery();
            if (dbResponse.next() && dbResponse.getInt(1) > 0) {
                status = true;
            }
            conDB.CloseConnection(preparedSt.getConnection());
            return status;
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
            return status;
        }
    }

    public Boolean savePermission(Owner owner, Permission permission) {
        try {
            sql = "INSERT INTO `refuel`.`permission`(`idOwner`,`idSystemPanel`) VALUES (?,?);";
            PreparedStatement preparedStatement = setupConnection(sql);
            preparedStatement.setInt(1, owner.getId());
            preparedStatement.setInt(2, permission.getIdSystemAction());
            preparedStatement.execute();
            conDB.CloseConnection(preparedStatement.getConnection());
            return true;
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
            return false;
        }
    }

    public Boolean deletePermission(Owner owner, Permission permission) {
        try {
            sql = "DELETE FROM `refuel`.`permission` WHERE permission.idOwner = ? and permission.idSystemPanel = ?";
            PreparedStatement preparedStatement = setupConnection(sql);
            preparedStatement.setInt(1, owner.getId());
            preparedStatement.setInt(2, permission.getIdSystemAction());
            preparedStatement.execute();
            conDB.CloseConnection(preparedStatement.getConnection());
            return true;
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
            return false;
        }
    }

    public int getActionId(Permission permission) {
        try {
            sql = "SELECT `idSystemAction` FROM `refuel`.`systemAction` where systemAction = ?;";
            PreparedStatement preparedStatement = setupConnection(sql);
            preparedStatement.setString(1, permission.getAction());
            ResultSet dbResponse = preparedStatement.executeQuery();
            if (dbResponse.next()) {
                return dbResponse.getInt(1);
            }
            conDB.CloseConnection(preparedStatement.getConnection());
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
            return 0;
        }
        return 0;
    }
}
