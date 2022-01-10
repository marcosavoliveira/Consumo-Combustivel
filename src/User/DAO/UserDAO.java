package User.DAO;

import Database.ConnectDB;
import User.Owner;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDAO {
    public Boolean loginUser(ConnectDB conDB, Owner owner){
        try{
            Connection con = conDB.getConnection(conDB.getServer(), conDB.getSchema());
            Statement checkLogin = con.createStatement();
            String sql = "select idOwner from Owner where login = '"+owner.getLogin()+"' and password ='"+owner.getPassword()+"';";
            System.out.println("SQL: "+sql);
            ResultSet dbResponse = checkLogin.executeQuery(sql);
            if(dbResponse.next()){
                return true;
            }
            con.close();
        }catch(SQLException exception){
            System.err.println(exception.getMessage());
            return false;
        }
    return false;
    }
}
