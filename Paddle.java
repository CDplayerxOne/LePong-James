// Author: Corey Dai
// Date: May 21st, 2024
// Description: Forms the paddle controlled by each user

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.imageio.ImageIO;

// Forms the paddle controlled by each user
public class Paddle extends Rectangle {

  public int yVelocity;
  public final int SPEED = 5; // movement speed of ball
  public static final int CHARACTER_HEIGHT = 150; // size of ball
  public static final int CHARACTER_WIDTH = 75;
  public int player;
  public Image image;

  // constructor creates a paddle at given location with given dimensions at a
  // given location and loads images
  public Paddle(int x, int y, int p) {
    super(x, y, CHARACTER_WIDTH, CHARACTER_HEIGHT);
    player = p;
    try {
      if (player == 1) {
        image = ImageIO.read(new File("images/Lebron.png"));
      }

      if (player == 2) {
        image = ImageIO.read(new File("images/Steph.png"));
      }
    } catch (Exception e) {
      GamePanel.setError();
    }
  }

  // called from GamePanel when any keyboard input is detected
  // updates the direction of the paddle based on user input
  public void keyPressed(KeyEvent e) {
    // Player 1 controls
    if (player == 1) {
      if (e.getKeyChar() == 'w') {
        setYDirection(SPEED * -1);
        move();
      }

      if (e.getKeyChar() == 's') {
        setYDirection(SPEED);
        move();
      }
    }

    // Player 2 controls
    if (player == 2) {
      if (e.getKeyCode() == KeyEvent.VK_UP) {
        setYDirection(SPEED * -1);
        move();
      }

      if (e.getKeyCode() == KeyEvent.VK_DOWN) {
        setYDirection(SPEED);
        move();
      }
    }
  }

  // called from GamePanel when any key is released (no longer being pressed down)
  // Makes the paddle stop moving in that direction
  public void keyReleased(KeyEvent e) {
    if (player == 1) {
      if (e.getKeyChar() == 'w') {
        setYDirection(0);
        move();
      }

      if (e.getKeyChar() == 's') {
        setYDirection(0);
        move();
      }
    }

    if (player == 2) {
      if (e.getKeyCode() == KeyEvent.VK_UP) {
        setYDirection(0);
        move();
      }

      if (e.getKeyCode() == KeyEvent.VK_DOWN) {
        setYDirection(0);
        move();
      }
    }
  }

  // Called whenever the movement of the ball changes in the y-direction (up/down)
  public void setYDirection(int yDirection) {
    yVelocity = yDirection;
  }

  // Updates the current location of the ball
  public void move() {
    y = y + yVelocity;
  }

  // called frequently from the GamePanel class
  // draws the current location of the Paddle to the screen
  public void draw(Graphics g) {
    if (!GamePanel.error) {
      g.setColor(Color.black);
      g.drawImage(image.getScaledInstance(CHARACTER_WIDTH, CHARACTER_HEIGHT,
          Image.SCALE_DEFAULT), x, y, null);
    }
  }

}