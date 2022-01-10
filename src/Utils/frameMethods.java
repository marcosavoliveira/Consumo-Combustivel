package Utils;

import javax.swing.*;
import java.awt.*;

public class frameMethods {
    static final int widthFrame = 800;
    static final int heightFrame = 600;
    static final int halfMeasure = 2;

    public void setterParamsFrame(JFrame frame) {
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setSize(widthFrame, heightFrame);
        frame.setPreferredSize(frame.getSize());
        frame.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - widthFrame) / halfMeasure,
                (Toolkit.getDefaultToolkit().getScreenSize().height - heightFrame) / halfMeasure);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
