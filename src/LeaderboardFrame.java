import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LeaderboardFrame extends JFrame {
    private JTextArea leaderboardTextArea;

    public LeaderboardFrame() {
        setTitle("Leaderboard");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(Color.BLACK);

        JLabel recordsLabel = new JLabel("RECORDS", SwingConstants.CENTER);
        recordsLabel.setForeground(Color.YELLOW);
        recordsLabel.setFont(new Font("Arial", Font.BOLD, 60));
        add(recordsLabel, BorderLayout.NORTH);

        leaderboardTextArea = new JTextArea();
        leaderboardTextArea.setEditable(false);
        leaderboardTextArea.setFont(new Font("Arial", Font.PLAIN, 55));
        leaderboardTextArea.setBackground(Color.BLACK);
        leaderboardTextArea.setForeground(Color.YELLOW);

        JScrollPane scrollPane = new JScrollPane(leaderboardTextArea);
        scrollPane.getViewport().setBackground(Color.BLACK);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);

        JButton exitButton = createButton("EXIT");
        JButton backButton = createButton("BACK TO MENU");

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(LeaderboardFrame.this, "Do you really want to close the window?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    LeaderboardFrame.this.dispose();
                    System.exit(0);
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Menu();
            }
        });

        buttonPanel.add(backButton);
        buttonPanel.add(exitButton);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        Listener windowListener = new Listener(this);
        addWindowListener(windowListener);

        loadLeaderboardFromFile();
        setVisible(true);
        this.setResizable(false);
    }

    public JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(Color.YELLOW);
        button.setForeground(Color.BLACK);
        button.setPreferredSize(new Dimension(200, 75));
        return button;
    }

    public void loadLeaderboardFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("leaderboard.txt"))) {
            String line;
            StringBuilder leaderboardText = new StringBuilder();
            int count = 0;
            while ((line = reader.readLine()) != null && count < 5) {
                leaderboardText.append(getPlace(count + 1)).append(": ").append(line).append("\n");
                count++;
            }
            leaderboardTextArea.setText(leaderboardText.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getPlace(int number) {
        if (number % 100 >= 11 && number % 100 <= 13) {
            return number + "th";
        }
        switch (number % 10) {
            case 1:
                return number + "st";
            case 2:
                return number + "nd";
            case 3:
                return number + "rd";
            default:
                return number + "th";
        }
    }
}