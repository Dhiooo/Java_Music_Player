package custom_component;

import java.awt.*;
import javax.swing.*;

public class RoundedPanel extends JPanel {
    private int arch;

    public RoundedPanel(int arch) {
        this.arch = arch;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground());
        g2.fillRoundRect(0,0, getWidth()-1, getHeight()-1, arch, arch);

//        setBorder(new RoundedBorder(arch));
    }

//    @Override
//    protected void paintBorder(Graphics g) {
//        super.paintBorder(g);
//
//        Dimension arcs = new Dimension(arch, arch);
//
//        Graphics2D g2 = (Graphics2D) g.create();
//        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//
//        g2.setColor(Color.GREEN);
//        g2.setStroke(new BasicStroke(2));
//        g2.drawRoundRect(1,1, getWidth()-3, getHeight()-3, arcs.width, arcs.height);
//    }
}
