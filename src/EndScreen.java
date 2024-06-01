import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EndScreen extends JPanel {
    private JButton startButton;
    private JButton exitButton;
    private JButton backButton;
    private JLabel scoreLabel;

    private Image birdImage;

    /**
     * Constructor for EndScreen
     *
     * @param score Score that we got in last game
     * @param birdImage Bird that we used in last game
     */
    public EndScreen(int score, Image birdImage) {
        this.birdImage = birdImage;

        setPreferredSize(new Dimension(600, 600));
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);
        setForeground(Color.BLACK);

        JLabel gameOverLabel = new JLabel("GAME OVER", SwingConstants.CENTER);
        gameOverLabel.setForeground(Color.YELLOW);
        gameOverLabel.setBackground(Color.BLACK);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 80));

        scoreLabel = new JLabel("SCORE: " + score, SwingConstants.CENTER);
        scoreLabel.setForeground(Color.YELLOW);
        scoreLabel.setBackground(Color.BLACK);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 60));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);

        startButton = new JButton("RESTART");
        backButton = new JButton("BACK TO MENU");
        exitButton = new JButton("EXIT");

        Dimension buttonSize = new Dimension(180, 100);

        startButton.setPreferredSize(buttonSize);
        backButton.setPreferredSize(buttonSize);
        exitButton.setPreferredSize(buttonSize);

        startButton.setBackground(Color.YELLOW);
        backButton.setBackground(Color.YELLOW);
        exitButton.setBackground(Color.YELLOW);

        startButton.setForeground(Color.BLACK);
        backButton.setForeground(Color.BLACK);
        exitButton.setForeground(Color.BLACK);

        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                restartGame();
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                backToMenu();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exitGame();
            }
        });

        buttonPanel.add(startButton);
        buttonPanel.add(backButton);
        buttonPanel.add(exitButton);

        add(gameOverLabel, BorderLayout.NORTH);
        add(scoreLabel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Restart game after press button "RESTART".
     */
    public void restartGame() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.dispose();
        JFrame newFrame = new JFrame("Flappy Bird");
        newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        FlappyBird flappyBird = new FlappyBird(birdImage);
        newFrame.getContentPane().add(flappyBird);
        newFrame.pack();
        newFrame.setResizable(false);
        newFrame.setLocationRelativeTo(null);
        newFrame.setVisible(true);
        Listener listener = new Listener(newFrame);
        newFrame.addWindowListener(listener);
    }

    /**
     * Back to menu after press button "BACK TO MENU"
     */
    public void backToMenu() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.dispose();
        new Menu();
    }

    /**
     * Exit from the gamen after press button "EXIT"
     */
    public void exitGame() {
        int choice = JOptionPane.showConfirmDialog(this, "Do you really want to close the window?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (frame != null) {
                frame.dispose();
                System.exit(0);
            }
        }
    }
}
