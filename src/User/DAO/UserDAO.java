package User.DAO;

import Database.ConnectDB;
import User.Owner;

import java.sql.*;

public class UserDAO {
    public Boolean loginUser(ConnectDB conDB, Owner owner){
        try{
            Connection con = conDB.getConnection(conDB.getServer(), conDB.getSchema());
            String sql = "select idOwner from Owner where login=? and password=?";
            PreparedStatement preparedSt = con.prepareStatement(sql);
            preparedSt.setString(1, owner.getLogin());
            preparedSt.setString(2, owner.getPassword());
            ResultSet dbResponse = preparedSt.executeQuery();
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