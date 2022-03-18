package Principal;

import Controller.vehicleCTR;
import EncryptClasses.SHA256;
import Controller.ownerCTR;
import User.Owner;
import Controller.PermissionCTR;
import User.Permission.Permission;
import Utils.ButtonEditor;
import Utils.ButtonRenderer;
import Utils.PopulatedVehicleTable;
import Utils.frameMethods;
import Vehicle.Vehicle;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Main {
    public JPanel panel1;
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
    private JPanel permissionPanel;
    private JPanel driverPanel;
    private JPanel VehiclePanel;
    private JPanel MaintencePanel;
    private JPanel RegisterPanel;
    private JTextField textFieldNome;
    private JButton buttonRemovePermission;
    private JTextField textFieldModel;
    private JTextField textFieldPlate;
    private JButton salvarButton;
    private JSpinner spinnerNumberSeats;
    private JComboBox comboBox1;
    private JFormattedTextField formattedTextField1;
    private JFormattedTextField formattedTextField2;
    private JTextArea textArea1;
    private JButton salvarButton1;
    private JTextField textField4;
    private JComboBox comboBox2;
    private JSpinner spinner2;
    private JButton calcularButton;
    private JSpinner spinnerYear;
    private JLabel labelWelcome;
    private JTable table1;
    public Main(Owner owner) {
        final int idLogged = owner.getId();
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

        labelWelcome.setText(labelWelcome.getText()+' '+owner.getName());
        tabbedPane2.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                System.out.println(tabbedPane2.getSelectedComponent());
                if(tabbedPane2.getSelectedIndex()==1){
                    comboBoxOwner.setModel(new ownerCTR().getOwnersList());
                    listSistemAction.setModel(new PermissionCTR().getSistemActionList());
                }
                if(tabbedPane2.getSelectedIndex()==2){
                    table1.setModel(new PopulatedVehicleTable().populate(idLogged,table1));
                }
            }
        });
        comboBoxOwner.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Owner owner = new Owner();
                owner.setLogin(new ownerCTR().getOwnerLogin(comboBoxOwner.getSelectedItem().toString()));
                listPermissions.setModel(new PermissionCTR().getUserPermissionList(new ownerCTR().getOwnerId(owner)));
            }
        });
        buttonSalvarOwner.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Owner owner = new Owner();
                owner.setName(textFieldNome.getText());
                owner.setDriverLicense(textFieldCNH.getText());
                owner.setLogin(textFieldLogin.getText().toUpperCase());
                owner.setPassword(String.valueOf(passwordFieldSenha.getPassword()), new SHA256());
                if(new ownerCTR().saveOwner(owner)){
                    JOptionPane.showMessageDialog(null, "Condutor Salvo com Sucesso","Sucesso",JOptionPane.PLAIN_MESSAGE);
                }else{
                    JOptionPane.showMessageDialog(null, "Falha ao Salvar Condutor","Error",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        buttonAddPermission.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Owner owner = new Owner();
                Permission permission = new Permission();
                permission.setAction(listSistemAction.getSelectedValue().toString());
                permission = new PermissionCTR().getActionId(permission);
                owner.setLogin(new ownerCTR().getOwnerLogin(comboBoxOwner.getSelectedItem().toString()));
                owner = new ownerCTR().getOwnerId(owner);
                if(new PermissionCTR().existsPermission(owner,permission)){
                    JOptionPane.showMessageDialog(null, "Esta rotina já está atribuída para o usuário selecionado","Sucesso",JOptionPane.WARNING_MESSAGE);
                }else{
                    if(new PermissionCTR().savePermission(owner,permission)){
                        listPermissions.setModel(new PermissionCTR().getUserPermissionList(new ownerCTR().getOwnerId(owner)));
                    }else{
                        JOptionPane.showMessageDialog(null, "Falha ao Salvar Permissão","Error",JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        buttonRemovePermission.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(listPermissions.getSelectedIndex() == -1){
                    JOptionPane.showMessageDialog(null, "Uma permissão deve ser selecionada para remoção","Sucesso",JOptionPane.WARNING_MESSAGE);
                }else{
                    Owner owner = new Owner();
                    Permission permission = new Permission();
                    permission.setAction(listPermissions.getSelectedValue().toString());
                    permission = new PermissionCTR().getActionId(permission);
                    owner.setLogin(new ownerCTR().getOwnerLogin(comboBoxOwner.getSelectedItem().toString()));
                    owner = new ownerCTR().getOwnerId(owner);
                    if(new PermissionCTR().deletePermission(owner, permission)){
                        listPermissions.setModel(new PermissionCTR().getUserPermissionList(new ownerCTR().getOwnerId(owner)));
                    }else{
                        JOptionPane.showMessageDialog(null, "Falha ao Excluir Permissão","Error",JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Vehicle vehicle = new Vehicle();
                vehicle.setIdOwner(idLogged);
                vehicle.setModel(textFieldModel.getText());
                vehicle.setLicensePlate(textFieldPlate.getText());
                vehicle.setYear(Integer.parseInt(spinnerYear.getValue().toString()));
                vehicle.setNumberSeats(Integer.parseInt(spinnerNumberSeats.getValue().toString()));
                if(new vehicleCTR().saveVehile(vehicle)){
                    JOptionPane.showMessageDialog(null, "Veículo Cadastrado com Sucesso","Sucesso",JOptionPane.PLAIN_MESSAGE);
                    table1.setModel(new PopulatedVehicleTable().populate(idLogged,table1));
                }else{
                    JOptionPane.showMessageDialog(null, "Falha ao Cadastrar Veículo","Error",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public void setFrame(JFrame frame, Owner owner) {
        Utils.frameMethods frameMethods = new frameMethods();
        frameMethods.setterParamsFrame(frame);
        frameMethods.defineCloseMethod(frame);
        frame.setContentPane(new Main(owner).panel1);
        frame.pack();
    }
}
