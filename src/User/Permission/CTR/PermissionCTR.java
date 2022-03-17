package User.Permission.CTR;

import Database.MYSQLConnection;
import User.Owner;
import User.Permission.DAO.PermissionDAO;
import User.Permission.Permission;

import javax.swing.*;
import java.util.List;

public class PermissionCTR {
    public DefaultListModel getUserPermissionList(Owner owner) {
        PermissionDAO userPermission = new PermissionDAO();
        List<String> actionList=userPermission.loadPermission(new MYSQLConnection(), owner);
        DefaultListModel permissionListModel = new DefaultListModel();
        for (String item: actionList) {
            permissionListModel.addElement(item);
        }
        return permissionListModel;
    }

    public DefaultListModel getSistemActionList() {
        PermissionDAO sistemActions = new PermissionDAO();
        List<String> actionList=sistemActions.loadSistemActions(new MYSQLConnection());
        DefaultListModel actionListModel = new DefaultListModel();
        for (String item: actionList) {
            actionListModel.addElement(item);
        }
        return actionListModel;
    }

    public Boolean existsPermission(Owner owner, Permission permission){
        PermissionDAO permissionDAO = new PermissionDAO();
        return permissionDAO.existsPermission(new MYSQLConnection(), owner,permission);
    }

    public Boolean deletePermission(Owner owner, Permission permission){
        PermissionDAO permissionDAO = new PermissionDAO();
        return permissionDAO.deletePermission(new MYSQLConnection(), owner,permission);
    }

    public Boolean savePermission(Owner owner, Permission permission){
        PermissionDAO permissionDAO = new PermissionDAO();
        return permissionDAO.savePermission(new MYSQLConnection(), owner,permission);
    }

    public Permission getActionId(Permission permission) {
        PermissionDAO permissionDAO = new PermissionDAO();
        permission.setIdSistemAction(permissionDAO.getActionId(new MYSQLConnection(), permission));
        return permission;
    }
}

