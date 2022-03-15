package User.Permission.CTR;

import Database.MYSQLConnection;
import User.DAO.UserDAO;
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
}
