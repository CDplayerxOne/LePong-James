// Author: Corey Dai
// Date: May 21st, 2024
// Description: Creates and controls the ball

import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;

// Creates and controls the ball
public class Ball extends Rectangle {

	public int xDirection = 1;
	public int yDirection = 1;
	public double yVelocity;
	public double xVelocity;
	public double speed = 0;
	public double angle = 0;
	public static final int BALL_DIAMETER = 30;
	public Image image;

	// constructor creates ball at given location with given dimensions and sets the
	// initial velocity
	public Ball(int x, int y) {
		super(x, y, BALL_DIAMETER, BALL_DIAMETER);
		try {
			image = ImageIO.read(new File("images/Basketball.png"));

		} catch (Exception e) {
			GamePanel.setError();
		}
		// xVelocity is the horizontal component of the speed vector
		xVelocity = xDirection * (int) Math.round((speed * Math.cos(angle)));

		// yVelocity is the vertical component of the speed vector
		yVelocity = yDirection * (int) Math.round((speed * Math.sin(angle)));
	}

	// called whenever the movement of the ball changes in the y-direction (up/down)
	public void setYDirection(int direction) {
		yDirection = direction * yDirection;
		// re-evaulates the speed
		xVelocity = xDirection * (int) Math.round((speed * Math.cos(angle)));
		yVelocity = yDirection * (int) Math.round((speed * Math.sin(angle)));
	}

	// called whenever the movement of the ball changes in the x-direction
	// (left/right)
	public void setXDirection(int direction) {
		xDirection = direction * xDirection;
		xVelocity = xDirection * (int) Math.round((speed * Math.cos(angle)));
		yVelocity = yDirection * (int) Math.round((speed * Math.sin(angle)));
	}

	// updates the current location of the ball
	public void move() {
		y = (int) Math.round((y + yVelocity));
		x = (int) Math.round((x + xVelocity));
	}

	// called frequently from the GamePanel class
	// draws the current location of the ball to the screen
	public void draw(Graphics g) {
		if (!GamePanel.error) {
			g.drawImage(image.getScaledInstance(BALL_DIAMETER, BALL_DIAMETER, Image.SCALE_DEFAULT), x, y, null);
		}
	}

}
