import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EndScreen extends JPanel {
    private JButton startButton;
    private JButton exitButton;
    private JButton backButton;
    private JLabel scoreLabel;

    public EndScreen(int score) {
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
                closeEndScreen();
                startGame();
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                closeEndScreen();
                new Menu();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(EndScreen.this, "Do you really want to close the window?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    closeEndScreen();
                    System.exit(0);
                }
            }
        });

        buttonPanel.add(startButton);
        buttonPanel.add(backButton);
        buttonPanel.add(exitButton);

        add(gameOverLabel, BorderLayout.NORTH);
        add(scoreLabel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void setScore(int score) {
        scoreLabel.setText("SCORE: " + score);
    }

    private void closeEndScreen() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.dispose();
    }

    private void startGame() {
        JFrame frame = new JFrame("Flappy Bird");
        FlappyBird flappyBird = new FlappyBird();
        frame.getContentPane().add(flappyBird);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        Listener listener = new Listener(frame);
        frame.addWindowListener(listener);
    }
}
