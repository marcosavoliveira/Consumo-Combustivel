package Controller;

import Database.MYSQLConnection;
import DAO.UserDAO;
import User.Owner;

import javax.swing.*;
import java.util.List;

public class OwnerController {
    public DefaultComboBoxModel getOwnersList() {
        List<String> actionList=new UserDAO(new MYSQLConnection()).listOwner();
        DefaultComboBoxModel ownerComboBoxModel = new DefaultComboBoxModel();
        for (String item: actionList) {
            ownerComboBoxModel.addElement(item);
        }
        return ownerComboBoxModel;
    }
    public Owner getOwnerId(Owner owner) {
        Integer id = new UserDAO(new MYSQLConnection()).getOwnerId(owner);
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
        return new UserDAO(new MYSQLConnection()).saveOwner(owner);
    }
}
