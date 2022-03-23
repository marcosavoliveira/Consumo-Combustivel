package Controller;


import DAO.VehicleDAO;
import Database.MYSQLConnection;
import Utils.TableFuncions.PopulatedVehicleTable;
import Vehicle.Vehicle;
import Vehicle.VehicleMethods;

import javax.swing.*;

public class VehicleController {

    public void saveVehicle(Vehicle vehicle,JTable table) {
        if(new VehicleDAO(new MYSQLConnection()).saveVehicle(vehicle)){
            JOptionPane.showMessageDialog(null, "Veículo Cadastrado com Sucesso","Sucesso",JOptionPane.PLAIN_MESSAGE);
            listVehicle(vehicle,table);
        }else{
            JOptionPane.showMessageDialog(null, "Falha ao Cadastrar Veículo","Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    public void listVehicle(Vehicle vehicle,JTable vehicleTable) {
        new PopulatedVehicleTable().populate(new VehicleDAO(new MYSQLConnection()).listVehicle(vehicle),vehicleTable);
    }

    public void listVehicleForCombo(Vehicle vehicle,JComboBox combo) {
        combo.setModel(new VehicleMethods().getModelLicenceFromVehicle(new VehicleDAO(new MYSQLConnection()).listVehicle(vehicle)));
    }
    public void deleteVehicle(Vehicle vehicleDTO,JTable vehicleTable) {

        if(new VehicleDAO(new MYSQLConnection()).deleteVehicle(vehicleDTO)){
            listVehicle(vehicleDTO,vehicleTable);
        }else{
            JOptionPane.showMessageDialog(null, "Falha ao Remover Veículo","Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    public void listIDVehicle(Vehicle vehicleDTO){
        if(!new VehicleDAO(new MYSQLConnection()).getIDVehicle(vehicleDTO)){
            JOptionPane.showMessageDialog(null, "Falha ao Retornar ID do Veículo","Error",JOptionPane.ERROR_MESSAGE);
        }
    }
}
