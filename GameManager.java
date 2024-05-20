import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.awt.event.*;
import javax.imageio.ImageIO;

// TODO - Fix the endgame

public class GameManager {
	public static int GAME_HEIGHT;
	public static int GAME_WIDTH;
	public static int SCORE_HEIGHT;

	public static boolean hasGameStarted = false;
	public static boolean hasGameEnded = false;
	public Image title;

	public static int[] score = { 0, 9 };

	public GameManager(int h, int w, int s) {
		GAME_HEIGHT = h;
		GAME_WIDTH = w;
		SCORE_HEIGHT = s;
		try {
			title = ImageIO.read(new File("images/LePong James.png"));
		} catch (IOException e) {
		}
	}

	private static void reset(Ball ball) {
		ball.x = GAME_WIDTH / 2 - Ball.BALL_DIAMETER / 2;
		ball.y = GAME_HEIGHT / 2 - Ball.BALL_DIAMETER;
		ball.angle = 0;
		if (hasGameStarted) {
			ball.speed = 4;
		} else {
			ball.speed = 0;
		}
		ball.xDirection = 1;
		ball.yDirection = 1;
		ball.xVelocity = (int) Math.round((ball.speed * Math.cos(ball.angle)));
		ball.yVelocity = (int) Math.round((ball.speed * Math.sin(ball.angle)));
		Physics.lastHit = 1;
	}

	public static void checkScored(Ball ball) {
		if (!hasGameEnded) {
			if (ball.x > GAME_WIDTH) {
				score[0]++;
				reset(ball);
			}

			if (ball.x + Ball.BALL_DIAMETER < 0) {
				score[1]++;
				reset(ball);
			}

		}
	}

	public static void checkWin(Ball ball) {
		if (score[0] == 10 || score[1] == 10) {
			reset(ball);
			ball.speed = 0;
			hasGameEnded = true;
		}
	}

	public void keyPressed(KeyEvent e, Ball ball) {
		if (!hasGameStarted) {
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				hasGameStarted = true;
				reset(ball);
			}
		}

		if (hasGameEnded) {
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				score[0] = 0;
				score[1] = 0;
				hasGameEnded = false;
				reset(ball);
			}
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

	// called from GamePanel when any key is released (no longer being pressed down)
	// Makes the ball stop moving in that direction
	public void draw(Graphics g) {

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
			g.drawString("⬆️", GAME_WIDTH / 2 + title.getWidth(null) / 2 - 75, 400);
			g.setColor(Color.WHITE);
			g.setFont(new Font("timesRoman", Font.PLAIN, 20));
			g.drawString("up", GAME_WIDTH / 2 + title.getWidth(null) / 2 - 75, 420);
			g.setFont(new Font("timesRoman", Font.PLAIN, 40));
			g.setColor(Color.CYAN);
			g.drawString("⬇️", GAME_WIDTH / 2 + title.getWidth(null) / 2 - 75, 500);
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
		}

		// Scoreboard
		g.setColor(Color.BLACK);
		g.fillRect(0, GAME_HEIGHT, GAME_WIDTH, SCORE_HEIGHT);
		g.setFont(new Font("timesRoman", Font.PLAIN, 30));
		g.setColor(Color.WHITE);
		g.drawString("LeGoat 🐐 Score: " + score[0], 200, GAME_HEIGHT + 35);
		g.drawString("Chef Curry Score: " + score[1], 200 + GAME_WIDTH / 2, GAME_HEIGHT + 35);

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
