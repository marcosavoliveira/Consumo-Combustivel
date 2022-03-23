package DAO;

import Database.ConnectDB;
import Vehicle.Vehicle;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VehicleDAO {
    private final ConnectDB conDB;
    private String sql;

    public VehicleDAO(ConnectDB conDB){
        this.conDB = conDB;
    }

    private PreparedStatement setupConnection (String sql) throws SQLException {
        Connection con = conDB.getConnection(conDB.getServer(), conDB.getSchema());
        return con.prepareStatement(sql);
    }

    public Boolean saveVehicle(Vehicle vehicle) {
        try {
            sql = "INSERT INTO `refuel`.`vehicle`(`idOwner`,`Model`,`licensePlate`,`year`,`numberSeats`) VALUES(?,?,?,?,?);";
            PreparedStatement preparedStatement = setupConnection(sql);
            preparedStatement.setInt(1, vehicle.getIdOwner());
            preparedStatement.setString(2, vehicle.getModel());
            preparedStatement.setString(3, vehicle.getLicensePlate());
            preparedStatement.setInt(4, vehicle.getYear());
            preparedStatement.setInt(5, vehicle.getNumberSeats());
            preparedStatement.execute();
            conDB.CloseConnection(preparedStatement.getConnection());
            return true;
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
            return false;
        }
    }
    public List<Vehicle> listVehicle(Vehicle vehicle) {
        List<Vehicle> vehiclesByOwner = new ArrayList<>();
        try {
            sql = "SELECT `idVehicle`,`Model`,`licensePlate`,`year`,`numberSeats` FROM `refuel`.`vehicle` WHERE idOwner =?";
            PreparedStatement preparedStatement = setupConnection(sql);
            preparedStatement.setInt(1, vehicle.getIdOwner());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Vehicle item = new Vehicle();
                item.setId(resultSet.getInt(1));
                item.setModel(resultSet.getString(2));
                item.setLicensePlate(resultSet.getString(3));
                item.setYear(resultSet.getInt(4));
                item.setNumberSeats(resultSet.getInt(5));
                vehiclesByOwner.add(item);
            }
            conDB.CloseConnection(preparedStatement.getConnection());
            return vehiclesByOwner;
        } catch (SQLException exception) {
            System.err.println(exception.getMessage()+" Stack: "+Arrays.toString(exception.getStackTrace()));
            return new ArrayList<>();
        }
    }

    public boolean getIDVehicle(Vehicle vehicle) {

        try {
            sql = "SELECT `idVehicle` FROM `refuel`.`vehicle` WHERE `Model`=? AND `licensePlate`=?";
            PreparedStatement preparedStatement = setupConnection(sql);
            preparedStatement.setString(1, vehicle.getModel());
            preparedStatement.setString(2, vehicle.getLicensePlate());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                vehicle.setId(resultSet.getInt(1));
            }
            conDB.CloseConnection(preparedStatement.getConnection());
            return true;
        } catch (SQLException exception) {
            System.err.println(exception.getMessage()+" Stack: "+Arrays.toString(exception.getStackTrace()));
            vehicle.setId(-1);
            return false;
        }
    }

    public Boolean deleteVehicle(Vehicle vehicle) {
        try {
            sql = "DELETE FROM `refuel`.`vehicle` WHERE idVehicle =?;";
            PreparedStatement preparedStatement = setupConnection(sql);
            preparedStatement.setInt(1, vehicle.getId());
            preparedStatement.execute();
            conDB.CloseConnection(preparedStatement.getConnection());
            return true;
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
            return false;
        }
    }
}
