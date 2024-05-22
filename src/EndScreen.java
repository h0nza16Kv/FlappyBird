import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EndScreen extends JPanel {
    private JButton restartButton, menuButton, exitButton;
    private JFrame endFrame;

    public EndScreen(int score) {
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);
        setForeground(Color.BLACK);

        JPanel topEmptyPanel = new JPanel();
        topEmptyPanel.setPreferredSize(new Dimension(1, 50));
        add(topEmptyPanel, BorderLayout.NORTH);
        topEmptyPanel.setBackground(Color.BLACK);

        JLabel gameOverLabel = new JLabel("GAME OVER", SwingConstants.CENTER);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 70));
        gameOverLabel.setForeground(Color.YELLOW);
        add(gameOverLabel, BorderLayout.CENTER);

        JLabel scoreLabel = new JLabel("SCORE: " + score, SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 40));
        scoreLabel.setForeground(Color.YELLOW);
        add(scoreLabel, BorderLayout.CENTER);

        JPanel buttonContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        buttonContainer.setOpaque(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 0, 20));
        buttonPanel.setBackground(Color.BLACK);

        restartButton = new JButton("RESTART");
        restartButton.setPreferredSize(new Dimension(360, 85));
        restartButton.setBackground(Color.YELLOW);
        restartButton.addActionListener(e -> buttonActionPerformed(e));
        buttonPanel.add(restartButton);

        menuButton = new JButton("MENU");
        menuButton.setPreferredSize(new Dimension(360, 85));
        menuButton.setBackground(Color.YELLOW);
        menuButton.addActionListener(e -> buttonActionPerformed(e));
        buttonPanel.add(menuButton);

        exitButton = new JButton("EXIT");
        exitButton.setPreferredSize(new Dimension(360, 85));
        exitButton.setBackground(Color.YELLOW);
        exitButton.addActionListener(e -> buttonActionPerformed(e));
        buttonPanel.add(exitButton);

        buttonContainer.add(buttonPanel);
        add(buttonContainer, BorderLayout.SOUTH);

        endFrame = new JFrame("Flappy Bird");
        endFrame.add(this);
        endFrame.setSize(600, 600);
        endFrame.setResizable(false);
        endFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        endFrame.setLocationRelativeTo(null);
        endFrame.setVisible(true);

        Listener listener = new Listener(endFrame);
        endFrame.addWindowListener(listener);
    }

    private void buttonActionPerformed(ActionEvent e) {
        if (e.getSource() == restartButton) {
            endFrame.dispose();
            JFrame frame = new JFrame("Flappy Bird");
            FlappyBird flappyBird = new FlappyBird(new ImageIcon(getClass().getResource("bird_image.png")).getImage()); // Change "bird_image.png" to your actual bird image path
            frame.getContentPane().add(flappyBird);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            Listener listener = new Listener(frame);
            frame.addWindowListener(listener);
        } else if (e.getSource() == menuButton) {
            endFrame.dispose();
            new Menu();
        } else if (e.getSource() == exitButton) {
            int choice = JOptionPane.showConfirmDialog(endFrame, "Do you really want to close the window?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                endFrame.dispose();
                System.exit(0);
            }
        }
    }
}
