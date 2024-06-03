// Author: Corey Dai
// Date: May 21st, 2024
// Description: Manages gameplay

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.awt.event.*;
import javax.imageio.ImageIO;

// Gamemanager class - manages gameplay
public class GameManager {
	public static int GAME_HEIGHT;
	public static int GAME_WIDTH;
	public static int SCORE_HEIGHT;

	// Determines if the game has started or the game is over
	public static boolean hasGameStarted = false;
	public static boolean hasGameEnded = false;
	// whichWay controls which user gets the ball first. Winner of the point does
	public Image title;

	public static int[] score = { 0, 0 };

	// Passes game height, width and scoreboard height
	public GameManager(int h, int w, int s) {
		GAME_HEIGHT = h;
		GAME_WIDTH = w;
		SCORE_HEIGHT = s;
		// Loads title image
		try {
			title = ImageIO.read(new File("images/LePong James.png"));
		} catch (IOException e) {
			GamePanel.setError();
		}
	}

	// Resets the game to initial state
	private static void reset(Ball ball) {
		ball.x = GAME_WIDTH / 2 - Ball.BALL_DIAMETER / 2;
		ball.y = GAME_HEIGHT / 2 - Ball.BALL_DIAMETER;
		ball.angle = 0;
		if (hasGameStarted) {
			ball.speed = 4;
		} else {
			ball.speed = 0;
		}
		// Ball goes to winner of the point
		if (Physics.lastHit == 2) {
			ball.xDirection = -1;
		} else {
			ball.xDirection = 1;
		}
		ball.yDirection = 1;
		ball.xVelocity = ball.xDirection * (int) Math.round((ball.speed * Math.cos(ball.angle)));
		ball.yVelocity = ball.yDirection * (int) Math.round((ball.speed * Math.sin(ball.angle)));
		Physics.firstHit = true;
	}

	// Checks if a player has scored
	public static void checkScored(Ball ball) {
		if (!hasGameEnded) {
			if (ball.x > GAME_WIDTH) {
				score[0]++;
				Physics.lastHit = 2;
				reset(ball);
			}

			if (ball.x + Ball.BALL_DIAMETER < 0) {
				score[1]++;
				Physics.lastHit = 1;
				reset(ball);
			}

		}
	}

	// Checks if a plyer has won
	public static void checkWin(Ball ball) {
		// Wins when a player reaches a score of 10
		if (score[0] == 10 || score[1] == 10) {
			Physics.lastHit = 1;
			reset(ball);
			ball.speed = 0;
			hasGameEnded = true;
		}
	}

	// Handles user input
	public void keyPressed(KeyEvent e, Ball ball) {
		// Space starts the game
		if (!hasGameStarted) {
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				hasGameStarted = true;
				reset(ball);
			}
		}

		// Space also allows replaying the game
		if (hasGameEnded) {
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				score[0] = 0;
				score[1] = 0;
				hasGameEnded = false;
				reset(ball);
			}
			// press x to quit
			if (e.getKeyChar() == 'x') {
				score[0] = 0;
				score[1] = 0;
				hasGameStarted = false;
				hasGameEnded = false;
				reset(ball);
				ball.speed = 0;
			}
		}

	}

	// Draws the graphics
	public void draw(Graphics g) {

		if (!GamePanel.error) {
			if (!hasGameStarted) {
				// Title
				g.setColor(Color.RED);
				g.fillRect(GAME_WIDTH / 2 - title.getWidth(null) / 2 - 20, 30, title.getWidth(null) + 40,
						title.getHeight(null) + 40);
				g.setColor(Color.BLACK);
				g.fillRect(GAME_WIDTH / 2 - title.getWidth(null) / 2 - 10, 40, title.getWidth(null) + 20,
						title.getHeight(null) + 20);
				g.drawImage(title, GAME_WIDTH / 2 - title.getWidth(null) / 2, 50, null);

				// Player 1 Controls
				g.setColor(Color.RED);
				g.fillRect(GAME_WIDTH / 2 - title.getWidth(null) / 2 - 20, 300, 170,
						270);
				g.setColor(Color.BLACK);
				g.fillRect(GAME_WIDTH / 2 - title.getWidth(null) / 2 - 10, 310, 150,
						250);
				g.setColor(Color.WHITE);
				g.drawString("P1 Controls", GAME_WIDTH / 2 - title.getWidth(null) / 2 - 5, 340);
				g.setFont(new Font("timesRoman", Font.PLAIN, 40));
				g.setColor(Color.CYAN);
				g.drawString("W", GAME_WIDTH / 2 - title.getWidth(null) / 2 + 45, 400);
				g.setColor(Color.WHITE);
				g.setFont(new Font("timesRoman", Font.PLAIN, 20));
				g.drawString("up", GAME_WIDTH / 2 - title.getWidth(null) / 2 + 55, 420);
				g.setFont(new Font("timesRoman", Font.PLAIN, 40));
				g.setColor(Color.CYAN);
				g.drawString("S", GAME_WIDTH / 2 - title.getWidth(null) / 2 + 50, 500);
				g.setColor(Color.WHITE);
				g.setFont(new Font("timesRoman", Font.PLAIN, 20));
				g.drawString("down", GAME_WIDTH / 2 - title.getWidth(null) / 2 + 40, 520);

				// Player 2 Controls
				g.setColor(Color.RED);
				g.fillRect(GAME_WIDTH / 2 + title.getWidth(null) / 2 - 150, 300, 170,
						270);
				g.setColor(Color.BLACK);
				g.fillRect(GAME_WIDTH / 2 + title.getWidth(null) / 2 - 140, 310, 150,
						250);
				g.setColor(Color.WHITE);
				g.drawString("P2 Controls", GAME_WIDTH / 2 + title.getWidth(null) / 2 - 135, 340);
				g.setFont(new Font("timesRoman", Font.PLAIN, 40));
				g.setColor(Color.CYAN);
				g.drawString("⬆", GAME_WIDTH / 2 + title.getWidth(null) / 2 - 75, 400);
				g.setColor(Color.WHITE);
				g.setFont(new Font("timesRoman", Font.PLAIN, 20));
				g.drawString("up", GAME_WIDTH / 2 + title.getWidth(null) / 2 - 75, 420);
				g.setFont(new Font("timesRoman", Font.PLAIN, 40));
				g.setColor(Color.CYAN);
				g.drawString("⬇", GAME_WIDTH / 2 + title.getWidth(null) / 2 - 75, 500);
				g.setColor(Color.WHITE);
				g.setFont(new Font("timesRoman", Font.PLAIN, 20));
				g.drawString("down", GAME_WIDTH / 2 + title.getWidth(null) / 2 - 90, 520);

				// Start Message
				g.setColor(Color.RED);
				g.fillRect(GAME_WIDTH / 2 - 160, 360, 320,
						170);
				g.setColor(Color.BLACK);
				g.fillRect(GAME_WIDTH / 2 - 150, 370, 300,
						150);
				g.setColor(Color.WHITE);
				g.setFont(new Font("timesRoman", Font.PLAIN, 20));
				g.drawString("Press \"SPACE\" to start", GAME_WIDTH / 2 - 100, 445);
				g.drawString("First to 10 WINS!!!", GAME_WIDTH / 2 - 100, 475);
			}

			// Scoreboard
			g.setColor(Color.BLACK);
			g.fillRect(0, GAME_HEIGHT, GAME_WIDTH, SCORE_HEIGHT);
			g.setFont(new Font("timesRoman", Font.PLAIN, 30));
			g.setColor(Color.WHITE);
			g.drawString("LeGoat Score: " + score[0], 200, GAME_HEIGHT + 35);
			g.drawString("Chef Curry Score: " + score[1], 200 + GAME_WIDTH / 2, GAME_HEIGHT + 35);

			// Winner message
			if (hasGameEnded && score[0] == 10) {
				g.setColor(Color.BLUE);
				g.setFont(new Font("timesRoman", Font.PLAIN, 60));
				g.drawString("WINNER!!!", 200, 200);
			}
			if (hasGameEnded && score[1] == 10) {
				g.setColor(Color.BLUE);
				g.setFont(new Font("timesRoman", Font.PLAIN, 60));
				g.drawString("WINNER!!!", 800, 200);
			}

			// Play/quit board
			if (hasGameEnded) {
				g.setColor(Color.RED);
				g.fillRect(GAME_WIDTH / 2 - 160, 360, 320,
						170);
				g.setColor(Color.BLACK);
				g.fillRect(GAME_WIDTH / 2 - 150, 370, 300,
						150);
				g.setColor(Color.WHITE);
				g.setFont(new Font("timesRoman", Font.PLAIN, 20));
				g.drawString("Press \"SPACE\" to play again", GAME_WIDTH / 2 - 130, 445);
				g.drawString("Press \"x\" to quit", GAME_WIDTH / 2 - 130, 465);
			}

		}
	}
}
