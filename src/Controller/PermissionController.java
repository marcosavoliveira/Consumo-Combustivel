package Controller;

import Database.MYSQLConnection;
import User.Owner;
import DAO.PermissionDAO;
import User.Permission.Permission;

import javax.swing.*;
import java.util.List;

public class PermissionController {
    public DefaultListModel getUserPermissionList(Owner owner) {
        PermissionDAO userPermission = new PermissionDAO(new MYSQLConnection());
        List<String> actionList=userPermission.loadPermission(owner);
        DefaultListModel permissionListModel = new DefaultListModel();
        for (String item: actionList) {
            permissionListModel.addElement(item);
        }
        return permissionListModel;
    }

    public DefaultListModel getSystemActionList() {
        PermissionDAO sistemActions = new PermissionDAO(new MYSQLConnection());
        List<String> actionList=sistemActions.loadSystemActions();
        DefaultListModel actionListModel = new DefaultListModel();
        for (String item: actionList) {
            actionListModel.addElement(item);
        }
        return actionListModel;
    }

    public Boolean existsPermission(Owner owner, Permission permission){
        PermissionDAO permissionDAO = new PermissionDAO(new MYSQLConnection());
        return permissionDAO.existsPermission(owner,permission);
    }

    public Boolean deletePermission(Owner owner, Permission permission){
        PermissionDAO permissionDAO = new PermissionDAO(new MYSQLConnection());
        return permissionDAO.deletePermission(owner,permission);
    }

    public Boolean savePermission(Owner owner, Permission permission){
        PermissionDAO permissionDAO = new PermissionDAO(new MYSQLConnection());
        return permissionDAO.savePermission(owner,permission);
    }

    public Permission getActionId(Permission permission) {
        PermissionDAO permissionDAO = new PermissionDAO(new MYSQLConnection());
        permission.setIdSystemAction(permissionDAO.getActionId(permission));
        return permission;
    }
}

