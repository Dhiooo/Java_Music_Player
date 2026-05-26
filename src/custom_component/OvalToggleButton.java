package custom_component;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class OvalToggleButton extends JToggleButton {
    private final Color defaultBg = new Color(0,0,0,0);
    private final Color hoverBg = new Color(88,88,88);
    private final Color pressedBg = new Color(61, 61, 61);

    private Image icon;
    private Dimension size;

    // Static Button
    public OvalToggleButton(Dimension size, String buttonIcon) {
        this.size = size;

        icon = new ImageIcon(buttonIcon).getImage();

        ovalButtonProperty();
    }

    private void ovalButtonProperty() {
        setFocusable(false);
        setBackground(defaultBg);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverBg);
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(defaultBg);
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                setBackground(pressedBg);

                setOpaque(false);
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setBackground(hoverBg);
                repaint();
            }
        });
    }

    @Override //-> Original
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground());
//        System.out.println("background: " + getBackground());
        g2.fillOval(0,0, size.width, size.height);

        g2.drawImage(icon, 5,5, size.width-10, size.height-10, null);
    }
}