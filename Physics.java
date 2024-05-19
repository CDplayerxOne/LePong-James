public class Physics {

	public static int lastHit = 1;
	public static boolean firstHit = true;

	public static void hitPaddle(Ball ball, Paddle player1, Paddle player2) {

		if (ball.intersects(player1) && lastHit == 2) {
			ball.speed += 0.5;
			ball.angle = ball.angle + (Math.round(Math.random()) == 0 ? Math.random() *
					(Math.PI / 16)
					: -Math.random() * (Math.PI / 16));
			ball.setXDirection(-1);
			lastHit = 1;

		}
		if (ball.intersects(player2) && lastHit == 1) {
			ball.angle = ball.angle + (Math.round(Math.random()) == 0 ? Math.random() *
					(Math.PI / 16)
					: -Math.random() * (Math.PI / 16));
			if (firstHit) {
				firstHit = false;
				if (ball.angle >= 0) {
					ball.angle += 0.2;
				} else {
					ball.angle -= 0.2;
				}
			}
			ball.speed += 0.5;
			ball.setXDirection(-1);
			lastHit = 2;
		}
	}

	public static void hitWall(Ball ball, int GAME_HEIGHT) {
		if (ball.y <= 0 || ball.y + Ball.BALL_DIAMETER >= GAME_HEIGHT) {
			ball.setYDirection(-1);
		}
	}
}
