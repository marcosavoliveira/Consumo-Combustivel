package Controller;

import Database.ConnectDB;
import Database.MYSQLConnection;
import User.DAO.UserDAO;
import User.Owner;

import java.sql.Connection;

public class LoginCTR {
    public boolean checkSignIn(Owner owner){
        UserDAO user = new UserDAO();
        return user.loginUser(new MYSQLConnection().getConnection("localhost:3307","refuel"), owner);
    }
}
