package Controller;

import DAO.UserDAO;
import DAO.VehicleDAO;
import Database.MYSQLConnection;
import User.Owner;
import Vehicle.Vehicle;

import javax.swing.*;
import java.util.List;

public class vehicleCTR {

    public Boolean saveVehile(Vehicle vehicle) {
        VehicleDAO vehicleDAO = new VehicleDAO();
        return vehicleDAO.saveVehicle(new MYSQLConnection(), vehicle);
    }

    public List<Vehicle> listVehile(Vehicle vehicle) {
        VehicleDAO vehicleDAO = new VehicleDAO();
        return vehicleDAO.listVehicle(new MYSQLConnection(), vehicle);
    }

    public Boolean deleteVehile(Vehicle vehicleDTO) {
        VehicleDAO vehicleDAO = new VehicleDAO();
        return vehicleDAO.deleteVehicle(new MYSQLConnection(), vehicleDTO);
    }
}
