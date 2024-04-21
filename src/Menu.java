import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Menu extends JFrame implements ActionListener {
    private JButton startButton, leaderboardButton, exitButton;

    public Menu() {
        setTitle("Flappy Bird");
        setSize(400, 600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        Listener listener = new Listener(this);
        addWindowListener(listener);

        JPanel panel = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel("FLAPPY BIRD", SwingConstants.CENTER);
        titleLabel.setForeground(Color.BLUE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setPreferredSize(new Dimension(300, 50));
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1));

        startButton = new JButton("START");
        startButton.addActionListener(this);
        buttonPanel.add(startButton);

        leaderboardButton = new JButton("LEADERBOARD");
        leaderboardButton.addActionListener(this);
        buttonPanel.add(leaderboardButton);

        exitButton = new JButton("EXIT");
        exitButton.addActionListener(this);
        buttonPanel.add(exitButton);

        panel.add(buttonPanel, BorderLayout.CENTER);

        add(panel);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {

        } else if (e.getSource() == leaderboardButton) {

        } else if (e.getSource() == exitButton) {
            if (JOptionPane.showConfirmDialog(this, "Do you really want to close the window?", "Confirm Exit", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                dispose();
            }
        }
    }
}