package Principal;

import Controller.MaintenanceController;
import Controller.VehicleController;
import EncryptClasses.SHA256;
import Controller.OwnerController;
import User.Owner;
import Controller.PermissionController;
import User.Permission.Permission;
import Utils.TableFuncions.PopulatedMaintenanceTable;
import Utils.TableFuncions.PopulatedVehicleTable;
import Utils.Forms.frameMethods;
import Vehicle.Maintenance.Maintenance;
import Vehicle.Maintenance.MaintenanceMethods;
import Vehicle.Vehicle;
import Vehicle.VehicleMethods;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Main {
    public JPanel panel1;
    public JPanel RegisterPanel;
    public JPanel driverPanel;
    public JPanel permissionPanel;
    public JPanel VehiclePanel;
    public JPanel MaintenancePanel;
    private JTabbedPane CalcPanel;
    private JTabbedPane tabbedPane2;
    private JTextField textFieldLogin;
    private JPasswordField passwordFieldSenha;
    private JTextField textFieldCNH;
    private JButton buttonSalvarOwner;
    private JList listSistemAction;
    protected JList listPermissions;
    private JButton buttonAddPermission;
    private JComboBox comboBoxOwner;
    private JTextField textFieldNome;
    private JButton buttonRemovePermission;
    private JTextField textFieldModel;
    private JTextField textFieldPlate;
    private JButton saveVehicle;
    private JSpinner spinnerNumberSeats;
    private JComboBox comboboxMaintenanceType;
    private JFormattedTextField formattedTextFieldReturnDate;
    private JTextArea textAreaAnnotation;
    private JButton saveMaintenance;
    private JTextField textField4;
    private JComboBox comboBoxVehicleCalc;
    private JSpinner spinner2;
    private JButton calcularButton;
    private JSpinner spinnerYear;
    private JLabel labelWelcome;
    private JTable table1;
    private JFormattedTextField formattedTextFieldDate;
    private JTable tableMaintenance;
    private JComboBox comboboxMaintenanceVehicle;
    private JScrollPane tableVehicle;

    public Main(Owner owner) {
        final int idLogged = owner.getId();
        new VehicleMethods().setupVehicleTable(table1,idLogged);
        new MaintenanceMethods().setupMaintenanceTable(tableMaintenance,idLogged);
        labelWelcome.setText(labelWelcome.getText()+' '+owner.getName());
        tabbedPane2.addChangeListener(e -> {
            if(tabbedPane2.getSelectedIndex()==1){
                comboBoxOwner.setModel(new OwnerController().getOwnersList());
                if(comboBoxOwner.getSelectedIndex()>0){
                    listSistemAction.setModel(new PermissionController().getSystemActionList());
                }
            }
            if(tabbedPane2.getSelectedIndex()==2){
                Vehicle listVehicle = new Vehicle();
                listVehicle.setIdOwner(idLogged);
                new VehicleController().listVehile(listVehicle,table1);
            }
            if(tabbedPane2.getSelectedIndex()==3){
                Vehicle listVehicle = new Vehicle();
                listVehicle.setIdOwner(idLogged);
                new VehicleController().listVehileForCombo(listVehicle,comboboxMaintenanceVehicle);
                new MaintenanceController().listMaintenanceType(comboboxMaintenanceType);
            }
        });
        comboBoxOwner.addActionListener(e -> {
            Owner owner1 = new Owner();
            owner1.setLogin(new OwnerController().getOwnerLogin(Objects.requireNonNull(comboBoxOwner.getSelectedItem()).toString()));
            listPermissions.setModel(new PermissionController().getUserPermissionList(new OwnerController().getOwnerId(owner1)));
        });
        buttonSalvarOwner.addActionListener(e -> {
            Owner owner12 = new Owner();
            owner12.setName(textFieldNome.getText());
            owner12.setDriverLicense(textFieldCNH.getText());
            owner12.setLogin(textFieldLogin.getText().toUpperCase());
            owner12.setPassword(String.valueOf(passwordFieldSenha.getPassword()), new SHA256());
            if(new OwnerController().saveOwner(owner12)){
                JOptionPane.showMessageDialog(null, "Condutor Salvo com Sucesso","Sucesso",JOptionPane.PLAIN_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(null, "Falha ao Salvar Condutor","Error",JOptionPane.ERROR_MESSAGE);
            }
        });
        buttonAddPermission.addActionListener(e -> {
            Owner owner13 = new Owner();
            Permission permission = new Permission();
            permission.setAction(listSistemAction.getSelectedValue().toString());
            permission = new PermissionController().getActionId(permission);
            owner13.setLogin(new OwnerController().getOwnerLogin(Objects.requireNonNull(comboBoxOwner.getSelectedItem()).toString()));
            owner13 = new OwnerController().getOwnerId(owner13);
            if(new PermissionController().existsPermission(owner13,permission)){
                JOptionPane.showMessageDialog(null, "Esta rotina já está atribuída para o usuário selecionado","Sucesso",JOptionPane.WARNING_MESSAGE);
            }else{
                if(new PermissionController().savePermission(owner13,permission)){
                    listPermissions.setModel(new PermissionController().getUserPermissionList(new OwnerController().getOwnerId(owner13)));
                }else{
                    JOptionPane.showMessageDialog(null, "Falha ao Salvar Permissão","Error",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        buttonRemovePermission.addActionListener(e -> {
            if(listPermissions.getSelectedIndex() == -1){
                JOptionPane.showMessageDialog(null, "Uma permissão deve ser selecionada para remoção","Sucesso",JOptionPane.WARNING_MESSAGE);
            }else{
                Owner owner14 = new Owner();
                Permission permission = new Permission();
                permission.setAction(listPermissions.getSelectedValue().toString());
                permission = new PermissionController().getActionId(permission);
                owner14.setLogin(new OwnerController().getOwnerLogin(Objects.requireNonNull(comboBoxOwner.getSelectedItem()).toString()));
                owner14 = new OwnerController().getOwnerId(owner14);
                if(new PermissionController().deletePermission(owner14, permission)){
                    listPermissions.setModel(new PermissionController().getUserPermissionList(new OwnerController().getOwnerId(owner14)));
                }else{
                    JOptionPane.showMessageDialog(null, "Falha ao Excluir Permissão","Error",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        saveVehicle.addActionListener(e -> {
            String[] vehilceParameters = new String[4];
            vehilceParameters[0] = textFieldModel.getText();
            vehilceParameters[1] = textFieldPlate.getText();
            vehilceParameters[2] = spinnerYear.getValue().toString();
            vehilceParameters[3] = spinnerNumberSeats.getValue().toString();
            Vehicle vehicleDTO = new PopulatedVehicleTable().saveVehicle(idLogged,vehilceParameters);
            new VehicleController().saveVehile(vehicleDTO,table1);
            new VehicleController().listVehile(vehicleDTO,table1);
        });
        saveMaintenance.addActionListener(e -> {
            if(comboboxMaintenanceVehicle.getSelectedIndex()>0){
            String[] maintenanceParameters = new String[5];
            int idTypeMaintenance = new MaintenanceMethods().getMaintenanceId(comboboxMaintenanceType.getSelectedItem().toString());
            maintenanceParameters[0] = String.valueOf(idTypeMaintenance);
            maintenanceParameters[1] = formattedTextFieldDate.getText();
            maintenanceParameters[2] = textAreaAnnotation.getText();
            maintenanceParameters[3] = formattedTextFieldReturnDate.getText();
            maintenanceParameters[4] = comboboxMaintenanceVehicle.getSelectedItem().toString();
            int idVehicle = new VehicleMethods().getIdVehicleToInsert(maintenanceParameters[4]);
            System.out.println("saveMaintenance"+ Arrays.toString(maintenanceParameters)+" id Vehicle"+idVehicle);
            Maintenance MaintenanceDTO = new PopulatedMaintenanceTable().saveMaintenance(idVehicle,maintenanceParameters);
            new MaintenanceController().saveMaintenance(MaintenanceDTO, tableMaintenance);
            new MaintenanceController().listMaintenance(MaintenanceDTO, tableMaintenance);
            }else{
                JOptionPane.showMessageDialog(null, "Selecione um veículo.","Alerta",JOptionPane.WARNING_MESSAGE);
            }
        });
        comboboxMaintenanceVehicle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(comboboxMaintenanceVehicle.getSelectedIndex()>0){
                    Maintenance maintenance = new Maintenance();
                    maintenance.setIdVehicle(new VehicleMethods().getIdVehicleToInsert(comboboxMaintenanceVehicle.getSelectedItem().toString()));
                    new MaintenanceController().listMaintenance(maintenance,tableMaintenance);
                }else{
                    new PopulatedMaintenanceTable().populate(new ArrayList<>(),tableMaintenance);
                }
            }
        });
    }

    public void setFrame(JFrame frame, Owner owner) {
        frameMethods frameMethods = new frameMethods();
        frameMethods.setterParamsFrame(frame);
        frameMethods.defineCloseMethod(frame);
        frame.setContentPane(new Main(owner).panel1);
        frame.pack();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
