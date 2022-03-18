package Controller;

import Database.MYSQLConnection;
import DAO.UserDAO;
import User.Owner;

public class LoginCTR {
    public Owner checkSignIn(Owner owner) {
        UserDAO user = new UserDAO();
        return user.signInUser(new MYSQLConnection(), owner);
    }
}
