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
    private ConnectDB conDB;
    private Connection con;
    private String sql;
    public PermissionDAO(ConnectDB conDB){
        this.conDB = conDB;
    }

    public List<String> loadPermission(Owner owner) {
        try {
            con = conDB.getConnection(conDB.getServer(), conDB.getSchema());
            sql = "SELECT idpermission,a.sistemaction FROM refuel.permission p\n" +
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

    public List<String> loadSistemActions() {
        try {
            Connection con = conDB.getConnection(conDB.getServer(), conDB.getSchema());
            String sql = "SELECT sistemaction FROM refuel.sistemaction;";
            PreparedStatement preparedSt = con.prepareStatement(sql);
            ResultSet dbResponse = preparedSt.executeQuery();
            List<String> sistemActions = new ArrayList<>();
            while (dbResponse.next()) {
                sistemActions.add(dbResponse.getString(1));
            }
            conDB.CloseConnection(con);
            return sistemActions;
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
            return new ArrayList<>();
        }
    }

    public Boolean existsPermission(Owner owner, Permission permission) {
        boolean status = false;
        try {
            Connection con = conDB.getConnection(conDB.getServer(), conDB.getSchema());
            String sql = "SELECT count(`idpermission`) FROM `refuel`.`permission` where idowner = ? and idsistempanel = ?;";
            PreparedStatement preparedSt = con.prepareStatement(sql);
            preparedSt.setInt(1, owner.getId());
            preparedSt.setInt(2, permission.getIdSystemAction());
            ResultSet dbResponse = preparedSt.executeQuery();
            if (dbResponse.next() && dbResponse.getInt(1) > 0) {
                status = true;
            }
            conDB.CloseConnection(con);
            return status;
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
            return status;
        }
    }
        public Boolean savePermission(Owner owner, Permission permission) {
            try {
                Connection con = conDB.getConnection(conDB.getServer(), conDB.getSchema());
                String sql = "INSERT INTO `refuel`.`permission`(`idowner`,`idsistempanel`) VALUES (?,?);";
                PreparedStatement preparedSt = con.prepareStatement(sql);
                preparedSt.setInt(1, owner.getId());
                preparedSt.setInt(2, permission.getIdSystemAction());
                preparedSt.execute();
                preparedSt.close();
                conDB.CloseConnection(con);
                return true;
            } catch (SQLException exception) {
                System.err.println(exception.getMessage());
                return false;
            }
    }

    public Boolean deletePermission(Owner owner, Permission permission) {
        try {
            Connection con = conDB.getConnection(conDB.getServer(), conDB.getSchema());
            String sql = "DELETE FROM `refuel`.`permission` WHERE permission.idowner = ? and permission.idsistempanel = ?";
            PreparedStatement preparedSt = con.prepareStatement(sql);
            preparedSt.setInt(1, owner.getId());
            preparedSt.setInt(2, permission.getIdSystemAction());
            preparedSt.execute();
            conDB.CloseConnection(con);
            return true;
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
            return false;
        }
    }

    public int getActionId(Permission permission) {
        try {
            Connection con = conDB.getConnection(conDB.getServer(), conDB.getSchema());
            String sql = "SELECT `idsistemaction` FROM `refuel`.`sistemaction` where sistemaction = ?;";
            PreparedStatement preparedSt = con.prepareStatement(sql);
            preparedSt.setString(1, permission.getAction());
            ResultSet dbResponse = preparedSt.executeQuery();
            if (dbResponse.next()) {
                return dbResponse.getInt(1);
            }
            conDB.CloseConnection(con);
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
            return 0;
        }
        return 0;
    }
}
