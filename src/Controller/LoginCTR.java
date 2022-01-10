package Controller;

import Database.MYSQLConnection;
import User.DAO.UserDAO;
import User.Owner;

public class LoginCTR {
    public boolean checkSignIn(Owner owner) {
        UserDAO user = new UserDAO();
        return user.signInUser(new MYSQLConnection(), owner);
    }
}
