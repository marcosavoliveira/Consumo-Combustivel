package User.DAO;

import Database.ConnectDB;
import User.Owner;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    public Boolean signInUser(ConnectDB conDB, Owner owner) {
        try {
            Connection con = conDB.getConnection(conDB.getServer(), conDB.getSchema());
            String sql = "select idOwner from Owner where login=? and password=?";
            PreparedStatement preparedSt = con.prepareStatement(sql);
            preparedSt.setString(1, owner.getLogin());
            preparedSt.setString(2, owner.getPassword());
            ResultSet dbResponse = preparedSt.executeQuery();
            if (dbResponse.next()) {
                return true;
            }
            conDB.CloseConnection(con);
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
            return false;
        }
        return false;
    }

    public List<String> listOwner(ConnectDB conDB) {
        try {
            Connection con = conDB.getConnection(conDB.getServer(), conDB.getSchema());
            String sql = "select login, Name from Owner order by login";
            PreparedStatement preparedSt = con.prepareStatement(sql);
            ResultSet dbResponse = preparedSt.executeQuery();
            List<String> ownersList = new ArrayList<>();
            ownersList.add("Selecione o condutor");
            while (dbResponse.next()) {
                String ownerItem = dbResponse.getString(1)+" ("+dbResponse.getString(2)+')';
                ownersList.add(ownerItem);
            }
            conDB.CloseConnection(con);
            return ownersList;
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
            return new ArrayList<>();
        }
    }

    public Integer getOwnerId(ConnectDB conDB, Owner owner) {
        int id=0;
        try {
            Connection con = conDB.getConnection(conDB.getServer(), conDB.getSchema());
            String sql = "select idOwner from Owner where login=?";
            PreparedStatement preparedSt = con.prepareStatement(sql);
            preparedSt.setString(1, owner.getLogin());
            ResultSet dbResponse = preparedSt.executeQuery();
            if (dbResponse.next()) {
                id = dbResponse.getInt(1);
            }
            conDB.CloseConnection(con);
            return id;
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
            return id;
        }
    }
}