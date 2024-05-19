import java.awt.*;

public class Scoreboard {
	public static int GAME_HEIGHT;
	public static int GAME_WIDTH;
	public static int SCORE_HEIGHT;

	public static int[] score = { 0, 0 };

	public Scoreboard(int h, int w, int s) {
		GAME_HEIGHT = h;
		GAME_WIDTH = w;
		SCORE_HEIGHT = s;
	}

	private static void reset(Ball ball) {
		ball.x = GAME_WIDTH / 2 - Ball.BALL_DIAMETER / 2;
		ball.y = GAME_HEIGHT / 2 - Ball.BALL_DIAMETER;
		ball.angle = 0;
		ball.speed = 4;
		ball.xDirection = 1;
		ball.yDirection = 1;
		ball.xVelocity = (int) Math.round((ball.speed * Math.cos(ball.angle)));
		ball.yVelocity = (int) Math.round((ball.speed * Math.sin(ball.angle)));
		Physics.lastHit = 1;
	}

	public static void checkScored(Ball ball) {
		if (ball.x > GAME_WIDTH) {
			score[0]++;
			reset(ball);
		}

		if (ball.x + Ball.BALL_DIAMETER < 0) {
			score[1]++;
			reset(ball);
		}
	}

	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, GAME_HEIGHT, GAME_WIDTH, SCORE_HEIGHT);
		g.setFont(new Font("timesRoman", Font.PLAIN, 30));
		g.setColor(Color.WHITE);
		g.drawString("Player 1 Score: " + score[0], 200, GAME_HEIGHT + 35);
		g.drawString("Player 2 Score: " + score[1], 200 + GAME_WIDTH / 2, GAME_HEIGHT + 35);
	}
}
