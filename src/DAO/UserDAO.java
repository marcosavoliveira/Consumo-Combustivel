package DAO;

import Database.ConnectDB;
import User.Owner;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private ConnectDB conDB;
    public UserDAO(ConnectDB conDB){
        this.conDB = conDB;
    }
    public Owner signInUser(Owner owner) {
        owner.setId(0);
        try {
            Connection con = conDB.getConnection(conDB.getServer(), conDB.getSchema());
            String sql = "select idOwner, Name from Owner where login=? and password=?";
            PreparedStatement preparedSt = con.prepareStatement(sql);
            preparedSt.setString(1, owner.getLogin());
            preparedSt.setString(2, owner.getPassword());
            ResultSet dbResponse = preparedSt.executeQuery();
            if (dbResponse.next()) {
                owner.setId(dbResponse.getInt(1));
                owner.setName(dbResponse.getString(2));
            }
            conDB.CloseConnection(con);
            return  owner;
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
            owner.setId(-1);
            return owner;
        }
    }

    public List<String> listOwner() {
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

    public Integer getOwnerId(Owner owner) {
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

    public Boolean saveOwner(Owner owner) {
        Boolean status = true;
            try {
                Connection con = conDB.getConnection(conDB.getServer(), conDB.getSchema());
                String sql = "INSERT INTO `refuel`.`owner`\n" +
                        "(`Login`,`Password`,`DriverLicense`,`Name`)\n" +
                        "VALUES\n" +
                        "(?,?,?,?)";
                PreparedStatement preparedSt = con.prepareStatement(sql);
                preparedSt.setString(1, owner.getLogin());
                preparedSt.setString(2, owner.getPassword());
                preparedSt.setString(3, owner.getDriverLicense());
                preparedSt.setString(4, owner.getName());
                preparedSt.execute();
                preparedSt.close();
                conDB.CloseConnection(con);
                return status;
            } catch (SQLException exception) {
                System.err.println(exception.getMessage());
                return false;
            }
        }
}