package User.DAO;

import User.Owner;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDAO {
    public Boolean loginUser(Connection con, Owner owner){
        try{
            Statement checkLogin = con.createStatement();
            String sql = "select idOwner from Owner where login = '"+owner.getLogin()+"' and password ='"+owner.getPassword()+"';";
            System.out.println("SQL: "+sql);
            ResultSet dbResponse = checkLogin.executeQuery(sql);
            if(dbResponse.next()){
                return true;
            }
        }catch(SQLException exception){
            System.err.println(exception.getMessage());
            return false;
        }
    return false;
    }
}
