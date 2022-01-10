package Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class frameMethods {
    static final int widthFrame = 800;
    static final int heightFrame = 600;
    static final int halfMeasure = 2;
    static final int exitValue = 0;

    public void setterParamsFrame(JFrame frame) {
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setSize(widthFrame, heightFrame);
        frame.setPreferredSize(frame.getSize());
        frame.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - widthFrame) / halfMeasure,
                (Toolkit.getDefaultToolkit().getScreenSize().height - heightFrame) / halfMeasure);
        frame.setResizable(false);
        frame.setVisible(true);
    }
    public void defineCloseMethod(JFrame frame){
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(frame, "Deseja realmente sair ?", "Fechar Janela?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    System.exit(exitValue);
                }
            }
        });
    }

}
