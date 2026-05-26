package custom_component;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import java.awt.*;

public class CustomFlatSVGIcon extends FlatSVGIcon {
    public CustomFlatSVGIcon(String name) {
        super(name);
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        // Custom painting logic
        Graphics2D g2d = (Graphics2D) g.create();

        // Enable anti-aliasing for smoother rendering
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Custom rendering (e.g., apply a color filter or transformation)
        // This example adds a custom color overlay
        g2d.setColor(new Color(255, 0, 0, 100)); // Red with transparency
        g2d.fillOval(x, y, getIconWidth(), getIconHeight());

//        // Call the super method to paint the original SVG
//        super.paintIcon(c, g2d, x, y);

        g2d.dispose();
    }
}