package Vehicle.Maintenance;

import Controller.MaintenanceController;
import Utils.TableFuncions.ButtonEditor;
import Utils.TableFuncions.ButtonRenderer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.List;

public class MaintenanceMethods {

    public DefaultComboBoxModel getMaintenanceType(List<String> maintenanceType){
        DefaultComboBoxModel defaultComboBoxModel = new DefaultComboBoxModel();
        for (String item : maintenanceType){
            defaultComboBoxModel.addElement(item);
        }
        return defaultComboBoxModel;
    }
    public int getMaintenanceId(String maintenanceType){
        Maintenance maintenanceDTO = new Maintenance();
        maintenanceDTO.setType(maintenanceType);
        new MaintenanceController().getMaintenanceTypeId(maintenanceDTO);
        return maintenanceDTO.getIdType();
    }

    public void setupMaintenanceTable(JTable tableMaintenance, int idLogged) {
        final String[] columnNames = {"ID","Veículo","Tipo Manutenção","Data","Retorno","Observações","Remover"};
        TableModel model = new DefaultTableModel(columnNames, 0);
        tableMaintenance.setName("Maintenance");
        tableMaintenance.setModel(model);
        tableMaintenance.getColumn("Remover").setCellRenderer(new ButtonRenderer());
        tableMaintenance.getColumn("Remover").setCellEditor(new ButtonEditor(new JTextField(),idLogged));
        tableMaintenance.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableMaintenance.getColumnModel().getColumn(0).setMinWidth(0);
        tableMaintenance.getColumnModel().getColumn(0).setMaxWidth(0);
        tableMaintenance.getColumnModel().getColumn(0).setWidth(0);
    }
}

