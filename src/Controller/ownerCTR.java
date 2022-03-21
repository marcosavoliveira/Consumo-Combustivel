package Controller;

import Database.MYSQLConnection;
import DAO.UserDAO;
import User.Owner;

import javax.swing.*;
import java.util.List;

public class ownerCTR {
    public DefaultComboBoxModel getOwnersList() {
        UserDAO user = new UserDAO();
        List<String> actionList=user.listOwner(new MYSQLConnection());
        DefaultComboBoxModel ownerComboBoxModel = new DefaultComboBoxModel();
        for (String item: actionList) {
            ownerComboBoxModel.addElement(item);
        }
        return ownerComboBoxModel;
    }
    public Owner getOwnerId(Owner owner) {
        UserDAO user = new UserDAO();
        Integer id = user.getOwnerId(new MYSQLConnection(), owner);
        owner.setId(id);
        return owner;
    }

    public String getOwnerLogin(String ownerComboValue) {
        String login="";
        if(!ownerComboValue.equals("Selecione o condutor")){
            login = ownerComboValue.substring(0,ownerComboValue.indexOf('(')-1);
        }
        return login;
    }

    public Boolean saveOwner(Owner owner) {
        UserDAO user = new UserDAO();
        return user.saveOwner(new MYSQLConnection(), owner);
    }
}
