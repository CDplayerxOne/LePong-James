
/* PlayerBall class defines behaviours for the player-controlled ball  

child of Rectangle because that makes it easy to draw and check for collision

In 2D GUI, basically everything is a rectangle even if it doesn't look like it!
*/
import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.imageio.ImageIO;

public class Paddle extends Rectangle {

  public int yVelocity;
  public final int SPEED = 5; // movement speed of ball
  public static final int CHARACTER_HEIGHT = 150; // size of ball
  public static final int CHARACTER_WIDTH = 75;
  public int player;
  public Image image;

  // constructor creates ball at given location with given dimensions
  public Paddle(int x, int y, int p) {
    super(x, y, CHARACTER_HEIGHT, CHARACTER_WIDTH);
    player = p;
    try {
      if (player == 1) {
        image = ImageIO.read(new File("images/Lebron.png"));
      }

      if (player == 2) {
        image = ImageIO.read(new File("images/Steph.png"));
      }
    } catch (Exception e) {
    }
  }

  // called from GamePanel when any keyboard input is detected
  // updates the direction of the ball based on user input
  // if the keyboard input isn't any of the options (d, a, w, s), then nothing
  // happens
  public void keyPressed(KeyEvent e) {
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
  // Makes the ball stop moving in that direction
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

  // called from GamePanel whenever a mouse click is detected
  // changes the current location of the ball to be wherever the mouse is located
  // on the screen

  // called whenever the movement of the ball changes in the y-direction (up/down)
  public void setYDirection(int yDirection) {
    yVelocity = yDirection;
  }

  // called whenever the movement of the ball changes in the x-direction
  // (left/right)

  // called frequently from both PlayerBall class and GamePanel class
  // updates the current location of the ball
  public void move() {
    y = y + yVelocity;
  }

  // called frequently from the GamePanel class
  // draws the current location of the ball to the screen
  public void draw(Graphics g) {
    g.setColor(Color.black);
    // g.fillRect(x, y, CHARACTER_WIDTH, CHARACTER_HEIGHT);
    g.drawImage(image.getScaledInstance(CHARACTER_WIDTH, CHARACTER_HEIGHT,
        Image.SCALE_DEFAULT), x, y, null);
  }

}