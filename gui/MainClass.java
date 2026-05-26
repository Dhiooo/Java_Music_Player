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
  private JPanel mainPanel;

  public MainGUIFrame(JPanel panelToBeRendered) {
    try {
      UIManager.setLookAndFeel(new FlatDarkLaf());
    } catch (UnsupportedLookAndFeelException e) {
      throw new RuntimeException(e);
    }

    this.mainPanel = panelToBeRendered;
    setTitle("Music Player");
    setSize(490, 490);
    setUndecorated(true);
    setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), FRAME_ARCH, FRAME_ARCH));
    setIconImage(new ImageIcon(MUSIC_PLAYER_ICON).getImage());
    setBackground(new Color(0, 0, 0, 0));
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    // Add the injected panel
    add(mainPanel);

    // Add WindowFocusListener to show components when the window gains focus
    addWindowFocusListener(new WindowAdapter() {
      @Override
      public void windowGainedFocus(WindowEvent e) {
        if (mainPanel != null) {
          showAllComponent(mainPanel);
        }
      }
    });

    setVisible(true);
  }

  // Removed setMainPanel to enforce Constructor Injection as per DIP


  public void showAllComponent(Container parent) {
    for (Component comp : parent.getComponents()) {
      mainPanel.setVisible(true);
      if (comp instanceof Container) {
        showAllComponent((Container) comp);
      }
    }
  }
}

public class MainClass {
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      AudioFormatValidator validator = new DefaultAudioValidator();
      // Composition Root: Instantiate dependencies first
      MainGUIPanel panel = new MainGUIPanel(validator); 
      // Inject the panel (abstraction) into the frame (high-level module)
      MainGUIFrame frame = new MainGUIFrame(panel);
    });
  }
}
