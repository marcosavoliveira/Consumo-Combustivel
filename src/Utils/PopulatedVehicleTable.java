package Utils;

import Controller.vehicleCTR;
import Vehicle.Vehicle;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class PopulatedVehicleTable{
    public DefaultTableModel populate(int idLogged, JTable table1){
        Vehicle vehicle = new Vehicle();
        DefaultTableModel tableModel = null;
        vehicle.setIdOwner(idLogged);
        List<Vehicle> vehicleByOwner = new vehicleCTR().listVehile(vehicle);
        table1.getColumn("Remover").setCellRenderer(new ButtonRenderer());
        tableModel = (DefaultTableModel) table1.getModel();
        int maxRows = tableModel.getRowCount();
        for(int i=0;i<maxRows;i++){
            tableModel.removeRow(i);
        }
        if(vehicleByOwner.size()>0 ){
            for(Vehicle item:vehicleByOwner){
                tableModel.addRow(new Object[]{item.getId(),item.getModel(),item.getLicensePlate(),item.getYear(),item.getNumberSeats()});
            }
        }
        return  tableModel;
    }
}
