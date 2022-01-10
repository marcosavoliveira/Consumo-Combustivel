package Principal;

import Utils.frameMethods;

import javax.swing.*;

public class Main {
    public JPanel panel1;

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
