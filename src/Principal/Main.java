package Principal;

import Utils.frameMethods;

import javax.swing.*;

public class Main {
    public JPanel panel1;
    private JTabbedPane tabbedPane1;
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
    private JButton salvarButton;
    private JComboBox comboBox1;

    public Main() {

    }

    public void setFrame(JFrame frame) {
        Utils.frameMethods frameMethods = new frameMethods();
        frameMethods.setterParamsFrame(frame);
        frameMethods.defineCloseMethod(frame);
        frame.setContentPane(new Main().panel1);
        frame.pack();
    }
}
