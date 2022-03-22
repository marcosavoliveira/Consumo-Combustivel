package Controller;


import DAO.VehicleDAO;
import Database.MYSQLConnection;
import Utils.TableFuncions.PopulatedVehicleTable;
import Vehicle.Vehicle;
import Vehicle.VehicleMethods;

import javax.swing.*;

public class vehicleCTR {

    public void saveVehile(Vehicle vehicle,JTable table) {
        if(new VehicleDAO().saveVehicle(new MYSQLConnection(), vehicle)){
            JOptionPane.showMessageDialog(null, "Veículo Cadastrado com Sucesso","Sucesso",JOptionPane.PLAIN_MESSAGE);
            listVehile(vehicle,table);
        }else{
            JOptionPane.showMessageDialog(null, "Falha ao Cadastrar Veículo","Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    public void listVehile(Vehicle vehicle,JTable vehicleTable) {
        new PopulatedVehicleTable().populate(new VehicleDAO().listVehicle(new MYSQLConnection(), vehicle),vehicleTable);
    }

    public void listVehileForCombo(Vehicle vehicle,JComboBox combo) {
        combo.setModel(new VehicleMethods().getModelLicenceFromVehicle(new VehicleDAO().listVehicle(new MYSQLConnection(), vehicle)));
    }
    public void deleteVehile(Vehicle vehicleDTO,JTable vehicleTable) {

        System.out.println(vehicleDTO.getIdOwner()+" CTR DELETE");
        if(new VehicleDAO().deleteVehicle(new MYSQLConnection(), vehicleDTO)){
            listVehile(vehicleDTO,vehicleTable);
        }else{
            JOptionPane.showMessageDialog(null, "Falha ao Remover Veículo","Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    public void listIDVehicle(Vehicle vehicleDTO){
        if(new VehicleDAO().listIDVehicle(new MYSQLConnection(), vehicleDTO)){
            System.out.println("listIDVehicle"+ vehicleDTO.getId());
        }else{
            JOptionPane.showMessageDialog(null, "Falha ao Retornar ID do Veículo","Error",JOptionPane.ERROR_MESSAGE);
        }
    }
}
