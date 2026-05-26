package custom_component;

import java.awt.*;
import javax.swing.border.Border;

public class RoundedBorder implements Border {
    private final int radius;

    public RoundedBorder(int radius) {
        this.radius = radius;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Border color and stroke
        g2.setColor(Color.BLACK); // Change to your preferred color
        g2.setStroke(new BasicStroke(2)); // Border thickness

        // Draw rounded rectangle
        g2.drawRoundRect(x+1, y+1, height-3, height-3, radius, radius);
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(7, 7, 7, 7);
    }

    @Override
    public boolean isBorderOpaque() {
        return false; // Allow transparency in the rounded corners
    }
}