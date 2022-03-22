package Controller;

import DAO.MaintenanceDAO;
import DAO.VehicleDAO;
import Database.MYSQLConnection;
import Utils.TableFuncions.PopulatedMaintenanceTable;
import Utils.TableFuncions.PopulatedVehicleTable;
import Vehicle.Maintenance.Maintenance;
import Vehicle.Maintenance.MaintenanceMethods;

import javax.swing.*;
import java.util.List;

public class MaintenanceCTR {
    public void saveMaintenance(Maintenance maintenanceDTO, JTable table2) {
        MaintenanceDAO maintenanceDAO = new MaintenanceDAO();
        if(maintenanceDAO.saveMaintenance(new MYSQLConnection(), maintenanceDTO)){
            JOptionPane.showMessageDialog(null, "Veículo Cadastrado com Sucesso","Sucesso",JOptionPane.PLAIN_MESSAGE);
            listMaintenance(maintenanceDTO,table2);
        }else{
            JOptionPane.showMessageDialog(null, "Falha ao Cadastrar Registro de Manutenção","Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    public void listMaintenance(Maintenance maintenance, JTable maintenanceTable) {
        new PopulatedMaintenanceTable().populate(new MaintenanceDAO().listMaintenance(new MYSQLConnection(), maintenance),maintenanceTable);
    }

    public void listMaintenanceType(JComboBox maintenanceType) {
        List<String> maintenanceTypeList = new MaintenanceDAO().listMaintenanceType(new MYSQLConnection());
        maintenanceType.setModel(new MaintenanceMethods().getMaintenanceType(maintenanceTypeList));
    }

    public void getMaintenanceTypeId(Maintenance maintenance) {
        if(new MaintenanceDAO().listIDMaintenanceType(new MYSQLConnection(), maintenance)){
            System.out.println("getMaintenanceTypeId"+ maintenance.getIdType());
        }else{
            JOptionPane.showMessageDialog(null, "Falha ao Retornar ID do Veículo","Error",JOptionPane.ERROR_MESSAGE);
        }
    }
}
