// Author: Corey Dai
// Date: May 21st, 2024
// Description: Manages the physics of the game 

// Manages the physics of the game 
public class Physics {

	// lastHit makes sure it hits the other player before it hits yourself.
	// Otherwise, because of the size of the ball relative to the paddle, the ball
	// may bounce within the player. Also helps keep track of who the ball goes to
	// when it resets
	public static int lastHit = 1;
	public static boolean firstHit = true;

	// Controls the behaviour of the ball when it hits the paddle
	public static void hitPaddle(Ball ball, Paddle player1, Paddle player2) {

		// When the ball hits player 1
		if (ball.intersects(player1) && lastHit == 2) {
			// Increases the speed, deflects at the angle with an error of +/- pi/30

			ball.angle = ball.angle + (Math.random() < 0.5 ? Math.random() *
					(Math.PI / 30)
					: -Math.random() * (Math.PI / 30));
			// Increases the error of the angle on the first hit to increase variance
			if (firstHit) {
				firstHit = false;
				if (ball.angle >= 0) {
					ball.angle += 0.25;
				} else {
					ball.angle -= 0.25;
				}
			}
			ball.speed += 0.5;
			ball.setXDirection(-1);
			lastHit = 1;
		}

		// When the ball hits player 2, same thing as when it hits player 1
		if (ball.intersects(player2) && lastHit == 1) {
			ball.angle = ball.angle + (Math.random() < 0.5 ? Math.random() *
					(Math.PI / 30)
					: -Math.random() * (Math.PI / 30));
			if (firstHit) {
				firstHit = false;
				if (ball.angle >= 0) {
					ball.angle += 0.25;
				} else {
					ball.angle -= 0.25;
				}
			}
			ball.speed += 0.5;
			ball.setXDirection(-1);
			lastHit = 2;
		}
	}

	// Determines what happens when the ball hits the wall -> makes it bounce
	public static void hitWall(Ball ball, int GAME_HEIGHT) {
		if (ball.y <= 0 || ball.y + Ball.BALL_DIAMETER >= GAME_HEIGHT) {
			ball.setYDirection(-1);
		}
	}
}
