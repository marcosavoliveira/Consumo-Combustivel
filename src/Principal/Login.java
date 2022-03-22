package Principal;

import Controller.LoginCTR;
import EncryptClasses.SHA256;
import User.Owner;
import Utils.Forms.frameMethods;

import javax.swing.*;

public class Login {
    static final String MAIN_FRAME_TITLE = "Login";
    final String LOGIN_FRAME_TITLE = "Consumo Combustivel";
    private JButton loginButton;
    private JPasswordField passwordField1;
    private JTextField textField1;
    private JPanel panel1;
    Owner owner = new Owner();
    public static JFrame login;
    public Login() {
        textField1.setText("marcos");
        passwordField1.setText("123456");
        loginButton.setRequestFocusEnabled(true);
        loginButton.addActionListener(e -> {
            owner.setLogin(textField1.getText().toUpperCase());
            owner.setPassword(String.valueOf(passwordField1.getPassword()), new SHA256());
            if (new LoginCTR().checkSignIn(owner).getId()>0) {
                login.setVisible(false);
                JFrame main = new JFrame(LOGIN_FRAME_TITLE);
                Main mainFrame = new Main(owner);
                mainFrame.setFrame(main,owner);

            } else {
                JOptionPane.showMessageDialog(null, "Falha na autenticação","Error",JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public static void main(String[] Args) {
        login = new JFrame(MAIN_FRAME_TITLE);
        frameMethods frameMethods = new frameMethods();
        frameMethods.setterParamsFrame(login);
        frameMethods.defineCloseMethod(login);
        login.setContentPane(new Login().panel1);
        login.pack();
    }
}
