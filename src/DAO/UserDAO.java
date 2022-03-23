package DAO;

import Database.ConnectDB;
import User.Owner;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private final ConnectDB conDB;
    private String sql;

    public UserDAO(ConnectDB conDB){
        this.conDB = conDB;
    }

    private PreparedStatement setupConnection (String sql) throws SQLException {
        Connection con = conDB.getConnection(conDB.getServer(), conDB.getSchema());
        return con.prepareStatement(sql);
    }

    public Owner signInUser(Owner owner) {
        try {
            sql = "select idOwner, Name from Owner where login=? and password=?";
            PreparedStatement preparedStatement = setupConnection(sql);
            preparedStatement.setString(1, owner.getLogin());
            preparedStatement.setString(2, owner.getPassword());
            ResultSet dbResponse = preparedStatement.executeQuery();
            if (dbResponse.next()) {
                owner.setId(dbResponse.getInt(1));
                owner.setName(dbResponse.getString(2));
            }
            conDB.CloseConnection(preparedStatement.getConnection());
            return  owner;
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
            owner.setId(-1);
            return owner;
        }
    }

    public List<String> listOwner() {
        try {
            sql = "select login, Name from Owner order by login";
            PreparedStatement preparedStatement = setupConnection(sql);
            ResultSet dbResponse = preparedStatement.executeQuery();
            List<String> ownersList = new ArrayList<>();
            while (dbResponse.next()) {
                String ownerItem = dbResponse.getString(1)+" ("+dbResponse.getString(2)+')';
                ownersList.add(ownerItem);
            }
            conDB.CloseConnection(preparedStatement.getConnection());
            return ownersList;
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
            return new ArrayList<>();
        }
    }

    public Integer getOwnerId(Owner owner) {
        int id=0;
        try {
            sql = "select idOwner from Owner where login=?";
            PreparedStatement preparedStatement = setupConnection(sql);
            preparedStatement.setString(1, owner.getLogin());
            ResultSet dbResponse = preparedStatement.executeQuery();
            if (dbResponse.next()) {
                id = dbResponse.getInt(1);
            }
            conDB.CloseConnection(preparedStatement.getConnection());
            return id;
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
            return id;
        }
    }

    public Boolean saveOwner(Owner owner) {
        try {
            sql = "INSERT INTO `refuel`.`owner`\n" +
                    "(`Login`,`Password`,`DriverLicense`,`Name`)\n" +
                    "VALUES\n" +
                    "(?,?,?,?)";
            PreparedStatement preparedStatement = setupConnection(sql);
            preparedStatement.setString(1, owner.getLogin());
            preparedStatement.setString(2, owner.getPassword());
            preparedStatement.setString(3, owner.getDriverLicense());
            preparedStatement.setString(4, owner.getName());
            preparedStatement.execute();
            conDB.CloseConnection(preparedStatement.getConnection());
            return true;
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
            return false;
        }
    }
}