package Principal;

import EncryptClasses.SHA256;
import User.CTR.ownerCTR;
import User.Owner;
import User.Permission.CTR.PermissionCTR;
import Utils.frameMethods;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public JPanel panel1;
    private JTabbedPane CalcPanel;
    private JTabbedPane tabbedPane2;
    private JTextField textFieldLogin;
    private JPasswordField passwordFieldSenha;
    private JTextField textFieldCNH;
    private JButton buttonSalvarOwner;
    private JList listSistemAction;
    private JList listPermissions;
    private JButton buttonAddPermission;
    private JComboBox comboBoxOwner;
    private JPanel permissionPanel;
    private JPanel driverPanel;
    private JPanel VehiclePanel;
    private JPanel MaintencePanel;
    private JPanel RegisterPanel;
    private JTextField textFieldNome;
    private JButton buttonRemovePermission;

    public Main() {

        tabbedPane2.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(tabbedPane2.getSelectedIndex()==1){
                    comboBoxOwner.setModel(new ownerCTR().getOwnersList());
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
        buttonRemovePermission.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public void setFrame(JFrame frame) {
        Utils.frameMethods frameMethods = new frameMethods();
        frameMethods.setterParamsFrame(frame);
        frameMethods.defineCloseMethod(frame);
        frame.setContentPane(new Main().panel1);
        frame.pack();
    }
}
