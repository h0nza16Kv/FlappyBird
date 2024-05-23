import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Menu extends JPanel {
    private JButton startButton, selectBirdButton, leaderboardButton, exitButton;
    private JFrame menuFrame;
    private String selectedBirdImage = "";

    public Menu() {
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);
        setForeground(Color.BLACK);

        JPanel topEmptyPanel = new JPanel();
        topEmptyPanel.setPreferredSize(new Dimension(1, 50));
        add(topEmptyPanel, BorderLayout.NORTH);
        topEmptyPanel.setBackground(Color.BLACK);

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel titleLabel = new JLabel("FLAPPY BIRD", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 70));
        titleLabel.setForeground(Color.YELLOW);
        titlePanel.setBackground(Color.BLACK);
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.CENTER);

        JPanel buttonContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        buttonContainer.setOpaque(false);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1, 0, 20));
        buttonPanel.setBackground(Color.BLACK);

        startButton = new JButton("START");
        startButton.setPreferredSize(new Dimension(360, 85));
        startButton.setBackground(Color.YELLOW);
        startButton.addActionListener(e -> buttonActionPerformed(e));
        buttonPanel.add(startButton);

        selectBirdButton = new JButton("SELECT BIRD");
        selectBirdButton.setPreferredSize(new Dimension(360, 85));
        selectBirdButton.setBackground(Color.YELLOW);
        selectBirdButton.addActionListener(e -> selectBird());
        buttonPanel.add(selectBirdButton);

        leaderboardButton = new JButton("LEADERBOARD");
        leaderboardButton.setPreferredSize(new Dimension(360, 85));
        leaderboardButton.setBackground(Color.YELLOW);
        leaderboardButton.addActionListener(e -> buttonActionPerformed(e));
        buttonPanel.add(leaderboardButton);

        exitButton = new JButton("EXIT");
        exitButton.setPreferredSize(new Dimension(360, 85));
        exitButton.setBackground(Color.YELLOW);
        exitButton.addActionListener(e -> buttonActionPerformed(e));
        buttonPanel.add(exitButton);

        buttonContainer.add(buttonPanel);
        add(buttonContainer, BorderLayout.SOUTH);

        menuFrame = new JFrame("Flappy Bird");
        menuFrame.add(this);
        menuFrame.setSize(600, 600);
        menuFrame.setResizable(false);
        menuFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        menuFrame.setLocationRelativeTo(null);
        menuFrame.setVisible(true);

        Listener listener = new Listener(menuFrame);
        menuFrame.addWindowListener(listener);
    }

    public void buttonActionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            if (!selectedBirdImage.equals("")) {
                menuFrame.dispose();
                JFrame frame = new JFrame("Flappy Bird");
                FlappyBird flappyBird = new FlappyBird(new ImageIcon(getClass().getResource(selectedBirdImage)).getImage());
                frame.getContentPane().add(flappyBird);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setResizable(false);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                Listener listener = new Listener(frame);
                frame.addWindowListener(listener);
            } else {
                JOptionPane.showMessageDialog(menuFrame, "Please select a bird image first.", "No Bird Image Selected", JOptionPane.WARNING_MESSAGE);
            }
        } else if (e.getSource() == leaderboardButton) {
            menuFrame.dispose();
            new LeaderboardFrame();
        } else if (e.getSource() == exitButton) {
            int choice = JOptionPane.showConfirmDialog(menuFrame, "Do you really want to close the window?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                menuFrame.dispose();
                System.exit(0);
            }
        }
    }

    public void selectBird() {
        menuFrame.dispose();
        JFrame frame = new JFrame("Bird Chooser");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BirdChoose birdChoose = new BirdChoose(this);
        frame.add(birdChoose);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
    }

    public void setSelectedBirdImage(String selectedBirdImage) {
        this.selectedBirdImage = selectedBirdImage;
        menuFrame.setVisible(true);
    }
}
