package Utils.TableFuncions;

import Principal.Main;
import Vehicle.Maintenance.Maintenance;
import Vehicle.Vehicle;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class PopulatedMaintenanceTable {

    public void populate(List<Maintenance> vehicleMaintenances, JTable table1){
        table1.getColumn("Remover").setCellRenderer(new ButtonRenderer());
        DefaultTableModel tableModel = (DefaultTableModel) table1.getModel();
        if(TableCleaner(tableModel) != 0){
            JOptionPane.showMessageDialog(null, "Falha ao Carregar Novos Itens","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        table1.setModel(populateMaintenanceTable(vehicleMaintenances,table1));
    }

    public Maintenance saveMaintenance(int idVehicle, String[] parameters){
        Maintenance maintenance = new Maintenance();
        maintenance.setIdVehicle(idVehicle);
        maintenance.setIdType(Integer.valueOf(parameters[0]));
        maintenance.setMaintenanceDate(parameters[1]);
        maintenance.setAnnotation(parameters[2]);
        maintenance.setDateReturn(parameters[3]);
        return maintenance;
    }
    private DefaultTableModel populateMaintenanceTable(List<Maintenance> vehicleMaintenances, JTable table){
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        if(vehicleMaintenances.size()>0 ){
            for(Maintenance item:vehicleMaintenances){
                tableModel.addRow(new Object[]{item.getId(),
                                                item.getVehiclePlate(),
                                                item.getType(),
                                                item.getMaintenanceDate(),
                                                item.getDateReturn(),
                                                item.getAnnotation()});
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
