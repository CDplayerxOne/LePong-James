
/* GameFrame class establishes the frame (window) for the game
It is a child of JFrame because JFrame manages frames
Runs the constructor in GamePanel class

*/
import java.awt.*;
import javax.swing.*;

public class GameFrame extends JFrame {

  GamePanel panel;
  JPanel court;

  public GameFrame() {
    panel = new GamePanel(); // run GamePanel constructor
    court = new JPanel();
    court.setBackground(Color.BLUE);
    court.setPreferredSize(new Dimension(1000, 600));
    panel.setOpaque(false);
    this.add(court);
    this.add(panel);
    this.setTitle("LePong James"); // set title for frame
    this.setResizable(false); // frame can't change size
    this.setBackground(Color.white);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // X button will stop program execution
    this.pack();// makes components fit in window - don't need to set JFrame size, as it will
                // adjust accordingly
    this.setVisible(true); // makes window visible to user
    this.setLocationRelativeTo(null);// set window in middle of screen
  }

}