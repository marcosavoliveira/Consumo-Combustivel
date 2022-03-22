package Principal;

import Controller.MaintenanceCTR;
import Controller.vehicleCTR;
import EncryptClasses.SHA256;
import Controller.ownerCTR;
import User.Owner;
import Controller.PermissionCTR;
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
                comboBoxOwner.setModel(new ownerCTR().getOwnersList());
                listSistemAction.setModel(new PermissionCTR().getSistemActionList());
            }
            if(tabbedPane2.getSelectedIndex()==2){
                Vehicle listVehicle = new Vehicle();
                listVehicle.setIdOwner(idLogged);
                new vehicleCTR().listVehile(listVehicle,table1);
            }
            if(tabbedPane2.getSelectedIndex()==3){
                Vehicle listVehicle = new Vehicle();
                listVehicle.setIdOwner(idLogged);
                new vehicleCTR().listVehileForCombo(listVehicle,comboboxMaintenanceVehicle);
                new MaintenanceCTR().listMaintenanceType(comboboxMaintenanceType);
            }
        });
        comboBoxOwner.addActionListener(e -> {
            Owner owner1 = new Owner();
            owner1.setLogin(new ownerCTR().getOwnerLogin(Objects.requireNonNull(comboBoxOwner.getSelectedItem()).toString()));
            listPermissions.setModel(new PermissionCTR().getUserPermissionList(new ownerCTR().getOwnerId(owner1)));
        });
        buttonSalvarOwner.addActionListener(e -> {
            Owner owner12 = new Owner();
            owner12.setName(textFieldNome.getText());
            owner12.setDriverLicense(textFieldCNH.getText());
            owner12.setLogin(textFieldLogin.getText().toUpperCase());
            owner12.setPassword(String.valueOf(passwordFieldSenha.getPassword()), new SHA256());
            if(new ownerCTR().saveOwner(owner12)){
                JOptionPane.showMessageDialog(null, "Condutor Salvo com Sucesso","Sucesso",JOptionPane.PLAIN_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(null, "Falha ao Salvar Condutor","Error",JOptionPane.ERROR_MESSAGE);
            }
        });
        buttonAddPermission.addActionListener(e -> {
            Owner owner13 = new Owner();
            Permission permission = new Permission();
            permission.setAction(listSistemAction.getSelectedValue().toString());
            permission = new PermissionCTR().getActionId(permission);
            owner13.setLogin(new ownerCTR().getOwnerLogin(Objects.requireNonNull(comboBoxOwner.getSelectedItem()).toString()));
            owner13 = new ownerCTR().getOwnerId(owner13);
            if(new PermissionCTR().existsPermission(owner13,permission)){
                JOptionPane.showMessageDialog(null, "Esta rotina já está atribuída para o usuário selecionado","Sucesso",JOptionPane.WARNING_MESSAGE);
            }else{
                if(new PermissionCTR().savePermission(owner13,permission)){
                    listPermissions.setModel(new PermissionCTR().getUserPermissionList(new ownerCTR().getOwnerId(owner13)));
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
                permission = new PermissionCTR().getActionId(permission);
                owner14.setLogin(new ownerCTR().getOwnerLogin(Objects.requireNonNull(comboBoxOwner.getSelectedItem()).toString()));
                owner14 = new ownerCTR().getOwnerId(owner14);
                if(new PermissionCTR().deletePermission(owner14, permission)){
                    listPermissions.setModel(new PermissionCTR().getUserPermissionList(new ownerCTR().getOwnerId(owner14)));
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
            new vehicleCTR().saveVehile(vehicleDTO,table1);
            new vehicleCTR().listVehile(vehicleDTO,table1);
        });
        saveMaintenance.addActionListener(e -> {
            String[] maintenanceParameters = new String[4];

            int idVehicle = new VehicleMethods().getIdVehicleToInsert(comboboxMaintenanceVehicle.getSelectedItem().toString());
            int idTypeMaintenance =  new MaintenanceMethods().getMaintenanceId(comboboxMaintenanceType.getSelectedItem().toString());
            maintenanceParameters[0] = String.valueOf(idTypeMaintenance);
            maintenanceParameters[1] = formattedTextFieldDate.getText();
            maintenanceParameters[2] = textAreaAnnotation.getText();
            maintenanceParameters[3] = formattedTextFieldReturnDate.getText();
            System.out.println("saveMaintenance"+ Arrays.toString(maintenanceParameters)+" id Vehicle"+idVehicle);
            Maintenance MaintenanceDTO = new PopulatedMaintenanceTable().saveMaintenance(idVehicle,maintenanceParameters);
            new MaintenanceCTR().saveMaintenance(MaintenanceDTO, tableMaintenance);
            new MaintenanceCTR().listMaintenance(MaintenanceDTO, tableMaintenance);
        });
        comboboxMaintenanceVehicle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

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
