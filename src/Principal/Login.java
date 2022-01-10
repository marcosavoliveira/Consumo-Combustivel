package Principal;

import Controller.LoginCTR;
import EncryptClasses.SHA256;
import User.Owner;
import Utils.frameMethods;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Login {

    private JButton loginButton;
    private JPasswordField passwordField1;
    private JTextField textField1;
    private JPanel panel1;
    Owner owner = new Owner();

    public Login(JFrame fillFile, int frameWidth, int frameHeight) {

        fillFile.setSize(frameWidth, frameHeight);
        fillFile.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(fillFile, "Deseja realmente sair ?", "Fechar Janela?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        loginButton.addActionListener(e -> {
            owner.setLogin(textField1.getText());
            owner.setPassword(String.valueOf(passwordField1.getPassword()), new SHA256());
            if (new LoginCTR().checkSignIn(owner)) {
                JOptionPane.showMessageDialog(null, "Got it");
            } else {
                JOptionPane.showMessageDialog(null, "Don't get it");
            }
        });
    }

    public static void main(String[] Args) {
        JFrame Login = new JFrame("Consumo Combustivel");
        Utils.frameMethods frameMethods = new frameMethods();
        frameMethods.setterParamsFrame(Login);
        Login.setContentPane(new Login(Login, Login.getWidth(), Login.getHeight()).panel1);
        Login.pack();
    }
}
