import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class FlappyBird extends JPanel implements ActionListener, KeyListener, MouseListener {
    private final int boardHeight = 600;
    private final int boardWidth = 600;
    private final int birdWidth = 40;
    private final int birdHeight = 40;
    private final int pipeWidth = 64;

    private Leaderboard leaderboard = new Leaderboard();
    private Image backgroundImg;
    private Image topPipeImg;
    private Image bottomPipeImg;
    private Image powerUpImg;

    private Bird bird;
    private ArrayList<Pipe> pipes;
    private ArrayList<PowerUp> powerUps;
    private Timer gameLoop;
    private Timer placePipeTimer;
    private Timer secondTimer;
    private Timer powerUpTimer;
    private boolean gameOver;
    private boolean paused;
    private boolean collisionDisabled;
    private boolean isPowerUpActive;
    private double score;
    private double gameSpeed = 1.0;
    private Random random = new Random();
    private int secondsElapsed;
    private int clickCount;
    private int pipeCounter;
    private int powerUpTimeLeft;
    private Image birdImage;

    public FlappyBird(Image birdImage) {
        this.birdImage = birdImage;
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);

        topPipeImg = new ImageIcon(getClass().getResource("/toppipe.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("/bottompipe.png")).getImage();
        powerUpImg = new ImageIcon(getClass().getResource("/powerup.png")).getImage();
        backgroundImg = new ImageIcon(getClass().getResource("/background.png")).getImage();

        start();

        placePipeTimer = new Timer(1700, e -> placePipes());

        gameLoop = new Timer(1000 / 50, this);
        secondTimer = new Timer(1000, e -> updateTimer());
    }

    public void start() {
        bird = new Bird(birdImage, boardHeight / 8, boardWidth / 2, birdWidth, birdHeight);
        pipes = new ArrayList<>();
        powerUps = new ArrayList<>();
        gameOver = false;
        paused = false;
        collisionDisabled = false;
        isPowerUpActive = false;
        score = 0;
        gameSpeed = 1.0;
        secondsElapsed = 0;
        clickCount = 0;
        pipeCounter = 0;
        powerUpTimeLeft = 0;
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
        gameLoop.stop();
        placePipeTimer.stop();
        secondTimer.stop();
        if (powerUpTimer != null) {
            powerUpTimer.stop();
        }
        leaderboard.addScore((int) score);

        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.getContentPane().removeAll();
        EndScreen endScreen = new EndScreen((int) score, birdImage);
        frame.add(endScreen);
        frame.revalidate();
        frame.repaint();
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

        for (PowerUp powerUp : powerUps) {
            g.drawImage(powerUp.getImg(), powerUp.getX(), powerUp.getY(), powerUp.getWidth(), powerUp.getHeight(), null);
        }

        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN, 32));
        String scoreString = "Score: " + (int) this.score;
        int scoreX = (boardWidth - g.getFontMetrics().stringWidth(scoreString)) / 2;
        g.drawString(scoreString, scoreX, 35);

        String timeString = String.format("%02d:%02d", secondsElapsed / 60, secondsElapsed % 60);
        g.drawString(timeString, 10, 35);

        String clickString = "Clicks: " + clickCount;
        int clickX = boardWidth - g.getFontMetrics().stringWidth(clickString) - 10;
        g.drawString(clickString, clickX, 35);

        if (isPowerUpActive) {
            g.setColor(Color.red);
            g.setFont(new Font("Arial", Font.PLAIN, 24));
            String powerUpString = "Power-Up: " + powerUpTimeLeft;
            int powerUpX = (boardWidth - g.getFontMetrics().stringWidth(powerUpString)) / 2;
            g.drawString(powerUpString, powerUpX, 70);
        }

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
            pipe.setX(pipe.getX() + (int) (-3 * gameSpeed));
            if (!pipe.isPassed() && bird.getX() > pipe.getX() + pipe.getWidth()) {
                score += 0.5;
                pipe.setPassed(true);
            }
            if (!collisionDisabled && collisionPipe(bird, pipe)) {
                gameOver = true;
            }
        }

        for (PowerUp powerUp : powerUps) {
            powerUp.setX(powerUp.getX() + (int) (-3 * gameSpeed));
            if (collisionPoweUP(bird, powerUp)) {
                powerUps.remove(powerUp);
                activatePowerUp();
                break;
            }
        }

        if (bird.getY() > boardHeight || bird.getY() < 0) {
            gameOver = true;
        }
    }

    public boolean collisionPipe(Bird a, Pipe b) {
        return a.getX() < b.getX() + b.getWidth() && a.getX() + a.getWidth() > b.getX() &&
                a.getY() < b.getY() + b.getHeight() && a.getY() + a.getHeight() > b.getY();
    }

    public boolean collisionPoweUP(Bird a, PowerUp b) {
        return a.getX() < b.getX() + b.getWidth() && a.getX() + a.getWidth() > b.getX() &&
                a.getY() < b.getY() + b.getHeight() && a.getY() + a.getHeight() > b.getY();
    }

    public void updateTimer() {
        if (!paused && !gameOver) {
            secondsElapsed++;
            if (isPowerUpActive) {
                powerUpTimeLeft--;
                if (powerUpTimeLeft <= 0) {
                    collisionDisabled = false;
                    isPowerUpActive = false;
                }
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (!gameLoop.isRunning() && !placePipeTimer.isRunning() && !secondTimer.isRunning()) {
                placePipeTimer.start();
                secondTimer.start();
                gameLoop.start();
            }
            bird.setVelocityY(-10);
            clickCount++;
        } else if (e.getKeyCode() == KeyEvent.VK_P) {
            pause();
        }
    }

    public void pause() {
        paused = !paused;
        if (paused) {
            placePipeTimer.stop();
            secondTimer.stop();
            if (powerUpTimer != null) {
                powerUpTimer.stop();
            }
        } else {
            placePipeTimer.start();
            secondTimer.start();
            if (isPowerUpActive && powerUpTimeLeft > 0) {
                powerUpTimer = new Timer(1000, e -> {
                    powerUpTimeLeft--;
                    if (powerUpTimeLeft <= 0) {
                        collisionDisabled = false;
                        isPowerUpActive = false;
                    }
                });
                powerUpTimer.start();
            }
        }
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!gameLoop.isRunning() && !placePipeTimer.isRunning() && !secondTimer.isRunning()) {
            placePipeTimer.start();
            secondTimer.start();
            gameLoop.start();
        }
        bird.setVelocityY(-10);
        clickCount++;
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    public void placePipes() {
        int gap = 200;
        int minHeight = 50;
        int maxHeight = boardHeight - gap - 2 * minHeight;
        if (maxHeight < 0) {
            maxHeight = 0;
        }
        int topPipeHeight = random.nextInt(maxHeight) + minHeight;
        int bottomPipeHeight = boardHeight - topPipeHeight - gap;
        if (bottomPipeHeight < minHeight) {
            bottomPipeHeight = minHeight;
            topPipeHeight = boardHeight - bottomPipeHeight - gap;
        }
        int startX = boardWidth + pipeWidth;
        Pipe topPipe = new Pipe(topPipeImg, startX, 0, pipeWidth, topPipeHeight);
        Pipe bottomPipe = new Pipe(bottomPipeImg, startX, topPipeHeight + gap, pipeWidth, bottomPipeHeight);
        pipes.add(topPipe);
        pipes.add(bottomPipe);

        pipeCounter++;
        if (pipeCounter >= 10 && pipeCounter % 10 == 0) {
            int powerUpX = startX + pipeWidth / 2 - birdWidth / 2;
            int powerUpY = topPipeHeight + (gap - birdHeight) / 2;
            PowerUp powerUp = new PowerUp(powerUpImg, powerUpX, powerUpY, birdWidth, birdHeight);
            powerUps.add(powerUp);
        }
    }

    public void activatePowerUp() {
        collisionDisabled = true;
        isPowerUpActive = true;
        powerUpTimeLeft = 5;
        if (!paused) {
            powerUpTimer = new Timer(1000, e -> {
                powerUpTimeLeft--;
                if (powerUpTimeLeft <= 0) {
                    collisionDisabled = false;
                    isPowerUpActive = false;
                    powerUpTimer.stop();
                }
            });
            powerUpTimer.start();
        }
    }
}

