// Author: Corey Dai
// Date: May 21st, 2024
// Description: Continously runs the game and performs necessary commands 

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;

// Continously runs the game and performs necessary commands 
public class GamePanel extends JPanel implements Runnable, KeyListener {

  // Dimensions of window
  public static final int GAME_WIDTH = 1280;
  public static final int GAME_HEIGHT = 677;
  public static final int SCORE_HEIGHT = 50;
  public static boolean error = false;

  public Thread gameThread;
  public Image court;
  public Image image;
  public Graphics graphics;
  public Paddle player1;
  public Paddle player2;
  public Ball ball;
  public GameManager scoreboard;

  // Initializes the players, the ball, the scoreboard and sets up the proper
  // settings
  public GamePanel() {
    player1 = new Paddle(0, GAME_HEIGHT / 2 - Paddle.CHARACTER_HEIGHT / 2, 1); // create a player controlled player1,
                                                                               // set start location to
    player2 = new Paddle(GAME_WIDTH - Paddle.CHARACTER_WIDTH, GAME_HEIGHT / 2 - Paddle.CHARACTER_HEIGHT / 2, 2); // create
                                                                                                                 // a
                                                                                                                 // player
                                                                                                                 // controlled
    ball = new Ball(GAME_WIDTH / 2 - Ball.BALL_DIAMETER / 2, GAME_HEIGHT / 2 - Ball.BALL_DIAMETER);
    scoreboard = new GameManager(GAME_HEIGHT, GAME_WIDTH, SCORE_HEIGHT);

    this.setFocusable(true); // make everything in this class appear on the screen
    this.addKeyListener(this); // start listening for keyboard input

    this.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT + SCORE_HEIGHT));

    // make this class run at the same time as other classes (without this each
    // class would "pause" while another class runs). By using threading we can
    // remove lag, and also allows us to do features like display timers in real
    // time!
    gameThread = new Thread(this);
    gameThread.start();

    // Loads background image
    try {
      court = ImageIO.read(new File("images/court.jpg"));

    } catch (Exception e) {
      error = true;
    }
  }

  // paint is a method in java.awt library that we are overriding. It is a special
  // method - it is called automatically in the background in order to update what
  // appears in the window. You NEVER call paint() yourself
  public void paint(Graphics g) {
    // we are using "double buffering here" - if we draw images directly onto the
    // screen, it takes time and the human eye can actually notice flashes of lag as
    // each pixel on the screen is drawn one at a time. Instead, we are going to
    // draw images OFF the screen, then simply move the image on screen as needed.
    image = createImage(GAME_WIDTH, GAME_HEIGHT + SCORE_HEIGHT); // draw off
    // screen

    graphics = image.getGraphics();
    draw(graphics);// update the positions of everything on the screen
    g.drawImage(image, 0, 0, this); // move the image on the screen

  }

  // Called if an error occurs
  public static void setError() {
    error = true;
  }

  // Call the draw methods in each class to update positions as things move
  public void draw(Graphics g) {
    // Court
    if (!error) {
      g.drawImage(court.getScaledInstance(1280, 677, Image.SCALE_DEFAULT), 0, 0,
          this);
      g.setColor(Color.BLACK);
      g.fillRect(0, 0, Paddle.CHARACTER_WIDTH, GAME_HEIGHT);
      g.fillRect(GAME_WIDTH - Paddle.CHARACTER_WIDTH, 0, Paddle.CHARACTER_WIDTH,
          GAME_HEIGHT);

      // Players
      player1.draw(g);
      player2.draw(g);

      // Ball
      ball.draw(g);
      scoreboard.draw(g);
    }

    // Error screen
    if (error) {
      g.setColor(Color.BLACK);
      g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT + SCORE_HEIGHT);
      g.setColor(Color.RED);
      g.setFont(new Font("timesRoman", Font.PLAIN, 40));
      g.drawString("An Error Occured. Sorry for the inconvenience. ", 150, 400);
    }
  }

  // call the move methods in other classes to update positions
  // this method is constantly called from run(). By doing this, movements appear
  // fluid and natural. If we take this out the movements appear sluggish and
  // laggy
  public void move() {
    player1.move();
    player2.move();
    ball.move();
  }

  // handles all collision detection and responds accordingly
  public void checkCollision() {

    // force player to remain on screen
    if (player1.y <= 0) {
      player1.y = 0;
    }
    if (player1.y >= GAME_HEIGHT - Paddle.CHARACTER_HEIGHT) {
      player1.y = GAME_HEIGHT - Paddle.CHARACTER_HEIGHT;
    }
    if (player2.y <= 0) {
      player2.y = 0;
    }
    if (player2.y >= GAME_HEIGHT - Paddle.CHARACTER_HEIGHT) {
      player2.y = GAME_HEIGHT - Paddle.CHARACTER_HEIGHT;
    }

    // Determines what happens when the ball hits certain areas
    Physics.hitPaddle(ball, player1, player2);
    Physics.hitWall(ball, GAME_HEIGHT);

    // Checks for scoring and wins
    GameManager.checkWin(ball);
    GameManager.checkScored(ball);

  }

  // run() method is what makes the game continue running without end. It calls
  // other methods to move objects, check for collision, and update the screen
  public void run() {
    // the CPU runs our game code too quickly - we need to slow it down! The
    // following lines of code "force" the computer to get stuck in a loop for short
    // intervals between calling other methods to update the screen.
    long lastTime = System.nanoTime();
    double amountOfTicks = 60;
    double ns = 1000000000 / amountOfTicks;
    double delta = 0;
    long now;

    while (true) { // this is the infinite game loop
      now = System.nanoTime();
      delta = delta + (now - lastTime) / ns;
      lastTime = now;

      // only move objects around and update screen if enough time has passed
      if (delta >= 1) {
        move();
        checkCollision();
        repaint();
        delta--;
      }
    }
  }

  // if a key is pressed, we'll send it over to the Paddle and GameManager classes
  // for
  // processing
  public void keyPressed(KeyEvent e) {
    player1.keyPressed(e);
    player2.keyPressed(e);
    scoreboard.keyPressed(e, ball);
  }

  // if a key is released, we'll send it over to the Paddle class for
  // processing
  public void keyReleased(KeyEvent e) {
    player1.keyReleased(e);
    player2.keyReleased(e);
  }

  // left empty because we don't need it; must be here because it is required to
  // be overridded by the KeyListener interface
  public void keyTyped(KeyEvent e) {

  }
}