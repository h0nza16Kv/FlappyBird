import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {

    private final int boardHeight = 600;
    private final int boardWidth = 600;

    private Leaderboard leaderboard = new Leaderboard();
    private Image backgroundImg;
    private Image topPipeImg;
    private Image bottomPipeImg;

    private Bird bird;
    private ArrayList<Pipe> pipes;
    private Timer gameLoop;
    private Timer placePipeTimer;
    private boolean gameOver;
    private boolean paused;
    private double score;
    private double gameSpeed = 1.0;
    private Random random = new Random();

    public FlappyBird() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setFocusable(true);
        addKeyListener(this);

        topPipeImg = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();

        loadImages();
        start();

        placePipeTimer = new Timer(1750, e -> placePipes());
        placePipeTimer.start();

        gameLoop = new Timer(1000 / 45, this);
        gameLoop.start();
    }

    public void loadImages() {
        backgroundImg = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
    }

    public void start() {
        bird = new Bird(new ImageIcon(getClass().getResource("./flappybird.png")).getImage(), boardHeight / 8, boardWidth / 2, 34, 24);
        pipes = new ArrayList<>();
        gameOver = false;
        paused = false;
        score = 0;
        gameSpeed = 1.0;
    }

    public void actionPerformed(ActionEvent e) {
        if (!paused && !gameOver) {
            movement();
        }
        repaint();
        if (gameOver) {
            endGame();
        }
    }

    public void endGame() {
        removeKeyListener(this);
        gameLoop.stop();
        placePipeTimer.stop();
        removeAll();
        EndScreen endScreen = new EndScreen((int) score);
        add(endScreen);
        revalidate();
        repaint();
        leaderboard.addScore((int) score);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        graphics(g);
    }

    public void graphics(Graphics g) {
        g.drawImage(backgroundImg, 0, 0, boardWidth, boardHeight, null);
        g.drawImage(bird.getImg(), bird.getX(), bird.getY(), bird.getWidth(), bird.getHeight(), null);
        for (Pipe pipe : pipes) {
            g.drawImage(pipe.getImg(), pipe.getX(), pipe.getY(), pipe.getWidth(), pipe.getHeight(), null);
        }
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN, 32));
        String scoreString = String.valueOf((int) score);
        int scoreX = (boardWidth - g.getFontMetrics().stringWidth(scoreString)) / 2;
        g.drawString(scoreString, scoreX, 35);

        if (paused) {
            g.setFont(new Font("Arial", Font.BOLD, 72));
            String pauseString = "PAUSED";
            int pauseX = (boardWidth - g.getFontMetrics().stringWidth(pauseString)) / 2;
            int pauseY = boardHeight / 2;
            g.drawString(pauseString, pauseX, pauseY);
        }
    }

    public void movement() {
        bird.setY(bird.getY() + (int) (bird.getVelocityY() * gameSpeed));
        bird.setVelocityY(bird.getVelocityY() + bird.getGravity());

        for (Pipe pipe : pipes) {
            pipe.setX(pipe.getX() + (int) (pipe.getVelocityX() * gameSpeed));
            if (!pipe.isPassed() && bird.getX() > pipe.getX() + pipe.getWidth()) {
                score += 0.5;
                pipe.setPassed(true);
            }
            if (collision(bird, pipe)) {
                gameOver = true;
            }
        }

        if (bird.getY() > boardHeight || bird.getY() < 0) {
            gameOver = true;
        }

        if ((int) score % 25 == 0 && (int) score != 0) {
            gameSpeed += 0.02;
        }
    }

    public boolean collision(Bird a, Pipe b) {
        return a.getX() < b.getX() + b.getWidth() &&
                a.getX() + a.getWidth() > b.getX() &&
                a.getY() < b.getY() + b.getHeight() &&
                a.getY() + a.getHeight() > b.getY();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            bird.setVelocityY(-7);
            if (gameOver) {
                restartGame();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_P) {
            pause();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public void pause() {
        paused = !paused;
        if (paused) {
            gameLoop.stop();
            placePipeTimer.stop();
        } else {
            gameLoop.start();
            placePipeTimer.start();
        }
        repaint();
    }

    public void restartGame() {
        start();
        gameLoop.start();
        placePipeTimer.start();
    }

    public void placePipes() {
        int gap = 200;

        int topPipeHeight = random.nextInt(boardHeight - gap);
        int bottomPipeHeight = boardHeight - topPipeHeight - gap;

        Pipe topPipe = new Pipe(topPipeImg, boardWidth, 0, 64, topPipeHeight);
        Pipe bottomPipe = new Pipe(bottomPipeImg, boardWidth, topPipeHeight + gap, 64, bottomPipeHeight);

        pipes.add(topPipe);
        pipes.add(bottomPipe);
    }
}
