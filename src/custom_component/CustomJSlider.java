package custom_component;

import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;

public class CustomJSlider extends JSlider {
    public CustomJSlider(int arcs) {
        setOpaque(false);
        setUI(new CustomJSliderKnob(this, arcs));
    }
}

class CustomJSliderKnob extends BasicSliderUI {
    private int arcs;

    public CustomJSliderKnob(JSlider slider, int arcs) {
        super(slider);
        this.arcs = arcs;
    }

    @Override
    public void paintFocus(Graphics g) { // Disable border when focus
    }

    @Override
    public Dimension getThumbSize() { // The dimension size of the knob
        return new Dimension(15,15);
    }

    @Override
    public void paintThumb(Graphics g) { // Overwrite Knob on slider
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(slider.getForeground());
        g2.fillOval(thumbRect.x, thumbRect.y,thumbRect.width,thumbRect.height);
    }

    @Override
    public void paintTrack(Graphics g) { // Overwrite drawing of track on slider
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(slider.getBackground());
        if(slider.getOrientation() == JSlider.VERTICAL) {
            g2.fillRoundRect(slider.getWidth() / 2 - 2, 2, 5, slider.getHeight(), arcs/2, arcs/2);
        } else {
            g2.fillRoundRect(2, slider.getHeight() / 2 - 3, slider.getWidth() - 5, 5, arcs/2, arcs/2);
        }
    }
}