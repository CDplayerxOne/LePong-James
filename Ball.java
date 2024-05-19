
/* PlayerBall class defines behaviours for the player-controlled ball  

child of Rectangle because that makes it easy to draw and check for collision

In 2D GUI, basically everything is a rectangle even if it doesn't look like it!
*/
import java.awt.*;
import java.io.File;

import javax.imageio.ImageIO;

public class Ball extends Rectangle {

	public int xDirection = 1;
	public int yDirection = 1;
	public double yVelocity;
	public double xVelocity;
	public double speed = 4; // movement speed of ball
	public double angle = 0;
	public static final int BALL_DIAMETER = 30;
	public Image image;

	// constructor creates ball at given location with given dimensions
	public Ball(int x, int y) {
		super(x, y, BALL_DIAMETER, BALL_DIAMETER);
		try {
			image = ImageIO.read(new File("images/Basketball.png"));

		} catch (Exception e) {
		}
		xVelocity = (int) Math.round((speed * Math.cos(angle)));
		yVelocity = (int) Math.round((speed * Math.sin(angle)));
	}

	// called from GamePanel when any keyboard input is detected
	// updates the direction of the ball based on user input
	// if the keyboard input isn't any of the options (d, a, w, s), then nothing
	// happens

	// called from GamePanel when any key is released (no longer being pressed down)
	// Makes the ball stop moving in that direction

	// called from GamePanel whenever a mouse click is detected
	// changes the current location of the ball to be wherever the mouse is located
	// on the screen

	// called whenever the movement of the ball changes in the y-direction (up/down)
	public void setYDirection(int direction) {
		// System.out.println(Math.sin(angle));
		// System.out.println(yDirection);
		yDirection = direction * yDirection;
		xVelocity = xDirection * (int) Math.round((speed * Math.cos(angle)));
		yVelocity = yDirection * (int) Math.round((speed * Math.sin(angle)));
	}

	public void setXDirection(int direction) {
		xDirection = direction * xDirection;
		xVelocity = xDirection * (int) Math.round((speed * Math.cos(angle)));
		yVelocity = yDirection * (int) Math.round((speed * Math.sin(angle)));
	}

	// called whenever the movement of the ball changes in the x-direction
	// (left/right)

	// called frequently from both PlayerBall class and GamePanel class
	// updates the current location of the ball
	public void move() {
		y = (int) Math.round((y + yVelocity));
		x = (int) Math.round((x + xVelocity));
	}

	// called frequently from the GamePanel class
	// draws the current location of the ball to the screen
	public void draw(Graphics g) {
		g.drawImage(image.getScaledInstance(BALL_DIAMETER, BALL_DIAMETER, Image.SCALE_DEFAULT), x, y, null);
	}

}
