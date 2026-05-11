package custom_component;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class NormalButton extends JButton {
    private ImageIcon icon;
    private String text;
    private Color defaultBg, hoverBg, pressedBg;

    public NormalButton(String text, Color defaultBg, Color hoverBg, Color pressedBg) {
        super(text);

        this.text = text;
        this.defaultBg = defaultBg;
        this.hoverBg = hoverBg;
        this.pressedBg = pressedBg;

        customization();
    }

    public NormalButton(ImageIcon icon, Color defaultBg, Color hoverBg, Color pressedBg) {
        this.icon = icon;
        this.defaultBg = defaultBg;
        this.hoverBg = hoverBg;
        this.pressedBg = pressedBg;

        customization();
    }

    void customization() {
        setOpaque(false);
        setFocusable(false);

        setBackground(defaultBg);
        setForeground(Color.WHITE);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverBg);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(defaultBg); // Reset to default
            }

            @Override
            public void mousePressed(MouseEvent e) {
                setBackground(pressedBg);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setBackground(hoverBg);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw the rounded background
        g2.setColor(getBackground());
        g2.fillRect(0, 0, getWidth()-1, getHeight()-1);

        // Draw the text manually to avoid the rectangular background issue
        FontMetrics fm = g2.getFontMetrics();
        int textX = (getWidth() - fm.stringWidth(getText())) / 2;
        int textY = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;

        g2.setColor(getForeground());
        g2.drawString(getText(), textX, textY);
        g2.dispose();
    }
}
