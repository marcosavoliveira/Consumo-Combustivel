package Vehicle;

import Controller.VehicleController;
import Utils.TableFuncions.ButtonEditor;
import Utils.TableFuncions.ButtonRenderer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.List;

public class VehicleMethods {
    public void setupVehicleTable(JTable table1,int idLogged){
        final String[] columnNames = {"ID","Modelo","Placa","Ano Fabricação","Assentos","Remover"};
        TableModel model = new DefaultTableModel(columnNames, 0);
        table1.setName("Vehicle");
        table1.setModel(model);
        table1.getColumn("Remover").setCellRenderer(new ButtonRenderer());
        table1.getColumn("Remover").setCellEditor(new ButtonEditor(new JTextField(),idLogged));
        table1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table1.getColumnModel().getColumn(0).setMinWidth(0);
        table1.getColumnModel().getColumn(0).setMaxWidth(0);
        table1.getColumnModel().getColumn(0).setWidth(0);
    }
    public int getIdVehicleToInsert(String comboBoxVehicle){
        Vehicle vehicleDTO = new Vehicle();
        String model = comboBoxVehicle.substring(0,comboBoxVehicle.indexOf('-')-1);
        String licence = comboBoxVehicle.substring(comboBoxVehicle.indexOf('-')+2);
        vehicleDTO.setModel(model);
        vehicleDTO.setLicensePlate(licence);
        new VehicleController().listIDVehicle(vehicleDTO);
        return vehicleDTO.getId();

    }
    public DefaultComboBoxModel getModelLicenceFromVehicle(List<Vehicle> vehicleList){
        DefaultComboBoxModel defaultComboBoxModel = new DefaultComboBoxModel();
        defaultComboBoxModel.addElement("Selecione o Veículo");
        for (Vehicle item : vehicleList){
            defaultComboBoxModel.addElement(item.getModel()+" - "+item.getLicensePlate());
        }
        return defaultComboBoxModel;
    }
}
