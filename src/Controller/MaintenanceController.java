package Controller;

import DAO.MaintenanceDAO;
import Database.MYSQLConnection;
import Utils.TableFuncions.PopulatedMaintenanceTable;
import Vehicle.Maintenance.Maintenance;
import Vehicle.Maintenance.MaintenanceMethods;

import javax.swing.*;
import java.util.List;

public class MaintenanceController {
    //private maintenanceService servico;
    public void saveMaintenance(Maintenance maintenanceDTO, JTable table2) {
        //if(servico.save()){
        MaintenanceDAO maintenanceDAO = new MaintenanceDAO(new MYSQLConnection());
        if(maintenanceDAO.saveMaintenance(maintenanceDTO)){
            JOptionPane.showMessageDialog(null, "Veículo Cadastrado com Sucesso","Sucesso",JOptionPane.PLAIN_MESSAGE);
            listMaintenance(maintenanceDTO,table2);
        }else{
            JOptionPane.showMessageDialog(null, "Falha ao Cadastrar Registro de Manutenção","Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    public void listMaintenance(Maintenance maintenance, JTable maintenanceTable) {
        new PopulatedMaintenanceTable().populate(new MaintenanceDAO(new MYSQLConnection()).listMaintenance(maintenance),maintenanceTable);
    }

    public void listMaintenanceType(JComboBox maintenanceType) {
        List<String> maintenanceTypeList = new MaintenanceDAO(new MYSQLConnection()).listMaintenanceType();
        maintenanceType.setModel(new MaintenanceMethods().getMaintenanceType(maintenanceTypeList));
    }

    public void getMaintenanceTypeId(Maintenance maintenance) {
        if(!new MaintenanceDAO(new MYSQLConnection()).listIDMaintenanceType(maintenance)){
            JOptionPane.showMessageDialog(null, "Falha ao Retornar ID do Veículo","Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteMaintenance(Maintenance maintenanceDTO, JTable maintenanceTable) {
        if(new MaintenanceDAO(new MYSQLConnection()).deleteMaintenance(maintenanceDTO)){
            listMaintenance(maintenanceDTO,maintenanceTable);
        }else{
            JOptionPane.showMessageDialog(null, "Falha ao Remover Registro de Manutenção","Error",JOptionPane.ERROR_MESSAGE);
        }
    }
}
