package User.Permission.CTR;

import Database.MYSQLConnection;
import User.Owner;
import User.Permission.DAO.PermissionDAO;

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

    public Boolean deletePermission(Owner owner){
        PermissionDAO permission = new PermissionDAO();
        return permission.deletePermission(new MYSQLConnection(), owner);
    }

    public Boolean savePermission(Owner owner){
        PermissionDAO permission = new PermissionDAO();
        return permission.savePermission(new MYSQLConnection(), owner);
    }
}

