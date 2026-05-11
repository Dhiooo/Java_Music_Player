package gui;

import javax.swing.*;

import com.formdev.flatlaf.FlatDarkLaf;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

/*

TO DO LIST
1) Add JFileChooser and the button
 */

class MainGUIFrame extends JFrame {
  private final int FRAME_ARCH = 30;
  private final String MUSIC_PLAYER_ICON = "src/resources/assets/Image/Play Button.png";
  private MainGUIPanel guiPanel;

  public MainGUIFrame() {
    try {
      UIManager.setLookAndFeel(new FlatDarkLaf());
    } catch (UnsupportedLookAndFeelException e) {
      throw new RuntimeException(e);
    }

    guiPanel = new MainGUIPanel(this);

    setTitle("Music Player");
    setSize(490, 490);
    setUndecorated(true);
    setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), FRAME_ARCH, FRAME_ARCH));
    setIconImage(new ImageIcon(MUSIC_PLAYER_ICON).getImage());
    setBackground(new Color(0, 0, 0, 0));
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    add(guiPanel);

    // Add WindowFocusListener to show components when the window gains focus
    addWindowFocusListener(new WindowAdapter() {
      @Override
      public void windowGainedFocus(WindowEvent e) {
        showAllComponent(guiPanel);
      }
    });

    setVisible(true);
  }

  public void showAllComponent(Container parent) {
    for (Component comp : parent.getComponents()) {
      guiPanel.setVisible(true);
      if (comp instanceof Container) {
        showAllComponent((Container) comp);
      }
    }
  }
}

public class MainClass {
  public static void main(String[] args) {
    SwingUtilities.invokeLater(MainGUIFrame::new);
  }
}
