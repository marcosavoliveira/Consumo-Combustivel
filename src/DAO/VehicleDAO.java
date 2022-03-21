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
    public Boolean saveVehicle(ConnectDB conDB, Vehicle vehicle) {
        try {
            Connection con = conDB.getConnection(conDB.getServer(), conDB.getSchema());
            String sql = "INSERT INTO `refuel`.`vehicle`(`idOwner`,`Model`,`licensePlate`,`year`,`numberSeats`) VALUES(?,?,?,?,?);";
            PreparedStatement preparedSt = con.prepareStatement(sql);
            preparedSt.setInt(1, vehicle.getIdOwner());
            preparedSt.setString(2, vehicle.getModel());
            preparedSt.setString(3, vehicle.getLicensePlate());
            preparedSt.setInt(4, vehicle.getYear());
            preparedSt.setInt(5, vehicle.getNumberSeats());
            preparedSt.execute();
            preparedSt.close();
            conDB.CloseConnection(con);
            return true;
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
            return false;
        }
    }
    public List<Vehicle> listVehicle(ConnectDB conDB, Vehicle vehicle) {
        List<Vehicle> vehiclesByOwner = new ArrayList<>();
        try {
            Connection con = conDB.getConnection(conDB.getServer(), conDB.getSchema());
            String sql = "SELECT `idvehicle`,`Model`,`licensePlate`,`year`,`numberSeats` FROM `refuel`.`vehicle` WHERE idOwner =?";
            PreparedStatement preparedSt = con.prepareStatement(sql);
            preparedSt.setInt(1, vehicle.getIdOwner());
            ResultSet resultSet = preparedSt.executeQuery();
            while (resultSet.next()){
                Vehicle item = new Vehicle();
                item.setId(resultSet.getInt(1));
                item.setModel(resultSet.getString(2));
                item.setLicensePlate(resultSet.getString(3));
                item.setYear(resultSet.getInt(4));
                item.setNumberSeats(resultSet.getInt(5));
                vehiclesByOwner.add(item);
            }
            preparedSt.close();
            conDB.CloseConnection(con);
            return vehiclesByOwner;
        } catch (SQLException exception) {
            System.err.println(exception.getMessage()+" Stack: "+Arrays.toString(exception.getStackTrace()));
            return new ArrayList<>();
        }
    }
    public Boolean deleteVehicle(ConnectDB conDB, Vehicle vehicle) {
        try {
            Connection con = conDB.getConnection(conDB.getServer(), conDB.getSchema());
            String sql = "DELETE FROM `refuel`.`vehicle` WHERE idVehicle =?;";
            PreparedStatement preparedSt = con.prepareStatement(sql);
            preparedSt.setInt(1, vehicle.getId());
            preparedSt.execute();
            preparedSt.close();
            conDB.CloseConnection(con);
            return true;
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
            return false;
        }
    }
}
