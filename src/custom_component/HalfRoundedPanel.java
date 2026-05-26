package custom_component;

import java.awt.*;
import javax.swing.*;

public class HalfRoundedPanel extends JPanel {
    private int arch;

    public HalfRoundedPanel(int arch) {
        this.arch = arch;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        /*
        1: Arch Down left and right
        2: Arch Up left and right
         */
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground());
        g2.fillRect(0,0,getWidth()+1,getHeight()+1);
    }
}
