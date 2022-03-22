package Controller;

import Database.MYSQLConnection;
import DAO.UserDAO;
import User.Owner;

public class LoginController {
    public Owner checkSignIn(Owner owner) {
        UserDAO user = new UserDAO(new MYSQLConnection());
        return user.signInUser(owner);
    }
}
