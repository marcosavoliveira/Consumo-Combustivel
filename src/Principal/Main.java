package Principal;

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
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JTextField textField2;
    private JButton salvarButton1;
    private JList list1;
    private JList list2;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JComboBox comboBox1;
    private JPanel permissionPanel;
    private JPanel driverPanel;
    private JPanel VehiclePanel;
    private JPanel MaintencePanel;
    private JPanel RegisterPanel;
    private JButton salvarButton;

    public Main() {

        tabbedPane2.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(tabbedPane2.getSelectedIndex()==1){
                    comboBox1.setModel(new ownerCTR().getOwnersList());
                }
            }
        });
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Owner owner = new Owner();
                owner.setLogin(new ownerCTR().getOwnerLogin(comboBox1.getSelectedItem().toString()));
                list2.setModel(new PermissionCTR().getUserPermissionList(new ownerCTR().getOwnerId(owner)));
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
