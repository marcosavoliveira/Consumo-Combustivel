package Controller;

import DAO.UserDAO;
import DAO.VehicleDAO;
import Database.MYSQLConnection;
import User.Owner;
import Utils.TableFuncions.PopulatedVehicleTable;
import Vehicle.Vehicle;

import javax.swing.*;
import java.util.List;

public class vehicleCTR {

    public void saveVehile(Vehicle vehicle,JTable table) {
        VehicleDAO vehicleDAO = new VehicleDAO();
        if(vehicleDAO.saveVehicle(new MYSQLConnection(), vehicle)){
            JOptionPane.showMessageDialog(null, "Veículo Cadastrado com Sucesso","Sucesso",JOptionPane.PLAIN_MESSAGE);
            listVehile(vehicle,table);
        }else{
            JOptionPane.showMessageDialog(null, "Falha ao Cadastrar Veículo","Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    public void listVehile(Vehicle vehicle,JTable vehicleTable) {
        new PopulatedVehicleTable().populate(new VehicleDAO().listVehicle(new MYSQLConnection(), vehicle),vehicleTable);
    }

    public void deleteVehile(Vehicle vehicleDTO,JTable vehicleTable) {
        VehicleDAO vehicleDAO = new VehicleDAO();
        System.out.println(vehicleDTO.getIdOwner()+" CTR DELETE");
        if(vehicleDAO.deleteVehicle(new MYSQLConnection(), vehicleDTO)){
            listVehile(vehicleDTO,vehicleTable);
        }else{
            JOptionPane.showMessageDialog(null, "Falha ao Remover Veículo","Error",JOptionPane.ERROR_MESSAGE);
        }
    }

}
