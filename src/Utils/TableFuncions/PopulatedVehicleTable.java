package Utils.TableFuncions;

import Controller.vehicleCTR;
import Utils.TableFuncions.ButtonRenderer;
import Vehicle.Vehicle;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class PopulatedVehicleTable{

    public Boolean populate(List<Vehicle> vehicleByOwner, JTable table1){
        table1.getColumn("Remover").setCellRenderer(new ButtonRenderer());
        DefaultTableModel tableModel = (DefaultTableModel) table1.getModel();
        if(TableCleaner(tableModel) != 0){
            JOptionPane.showMessageDialog(null, "Falha ao Carregar Novos Itens","Error",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        table1.setModel(populateVehicleTable(vehicleByOwner,table1));
        return true;
    }
//
//    public Boolean deleteVehicle(JTable table, int idLogged){
//        boolean status = false;
//                    Vehicle vehicleDTO = new Vehicle();
//            vehicleDTO.setId((Integer) table.getValueAt(table.getSelectedRow(), 0));
//            if(new vehicleCTR().deleteVehile(vehicleDTO,table)){
//                vehicleDTO.setIdOwner(idLogged);
//                status = true;
//            }
//        return status;
//    }

    public Vehicle saveVehicle(int idLogged, String[] parameters){
        Vehicle vehicle = new Vehicle();
        vehicle.setIdOwner(idLogged);
        vehicle.setModel(parameters[0]);
        vehicle.setLicensePlate(parameters[1]);
        vehicle.setYear(Integer.parseInt(parameters[2]));
        vehicle.setNumberSeats(Integer.parseInt(parameters[3]));
        return vehicle;
    }
    private DefaultTableModel populateVehicleTable(List<Vehicle> vehicleByOwner,JTable table){
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        if(vehicleByOwner.size()>0 ){
            for(Vehicle item:vehicleByOwner){
                tableModel.addRow(new Object[]{item.getId(),item.getModel(),item.getLicensePlate(),item.getYear(),item.getNumberSeats()});
            }
        }
        return  tableModel;
    }

    private int TableCleaner(DefaultTableModel tableModel){
        int maxRows = tableModel.getRowCount();
        for(int i=0;i<maxRows;i++){
            tableModel.removeRow(0);
        }
        return tableModel.getRowCount();
    }
}
