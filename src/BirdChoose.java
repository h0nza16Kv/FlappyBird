import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class BirdChoose extends JPanel {
    private String[] birdImage;
    private int currentBirdIndex;
    private JLabel birdLabel;
    private Menu menuPanel;

    public int getCurrentBirdIndex() {
        return currentBirdIndex;
    }

    public void setCurrentBirdIndex(int currentBirdIndex) {
        this.currentBirdIndex = currentBirdIndex;
    }


    /**
     * Constructor to create a bird image selection panel
     *
     * @param menuPanel The main menu panel
     */
    public BirdChoose(Menu menuPanel) {
        this.menuPanel = menuPanel;
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(600, 600));

        birdImage = new String[]{
                "1.png", "2.png", "3.png", "4.png",
                "5.png", "6.png", "7.png", "8.png"
        };

        currentBirdIndex = 0;
        birdLabel = new JLabel(createImageIcon(birdImage[currentBirdIndex]), JLabel.CENTER);

        JPanel imagePanel = new JPanel(new GridBagLayout());
        imagePanel.setBackground(Color.BLACK);
        imagePanel.add(birdLabel, new GridBagConstraints());
        add(imagePanel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        controlPanel.setBackground(Color.BLACK);
        controlPanel.setLayout(new GridBagLayout());

        JButton selectButton = new JButton("SELECT");
        selectButton.setPreferredSize(new Dimension(360, 85));
        selectButton.setBackground(Color.YELLOW);
        selectButton.setOpaque(true);
        selectButton.setBorderPainted(false);
        selectButton.addActionListener(e -> selectActionPerformed());

        controlPanel.add(selectButton, new GridBagConstraints());
        add(controlPanel, BorderLayout.SOUTH);

        setFocusable(true);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                KeyEvent(e);
            }
        });
    }

    /**
     * Handles a keyboard event.
     *
     * @param e The keyboard event.
     */
    public void KeyEvent(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                currentBirdIndex = (currentBirdIndex - 1 + birdImage.length) % birdImage.length;
                break;
            case KeyEvent.VK_RIGHT:
                currentBirdIndex = (currentBirdIndex + 1) % birdImage.length;
                break;
        }
        updateBirdImage();
    }

    /**
     * Updates the displayed bird image according to the current index.
     */
    //ChatGPT
    public void updateBirdImage() {
        birdLabel.setIcon(createImageIcon(birdImage[currentBirdIndex]));
    }

    /**
     * Handles the bird image selection event and passes the selected image to the main menu.
     */
    public void selectActionPerformed() {
        String selectedBirdImageName = birdImage[currentBirdIndex];
        menuPanel.setSelectedBirdImage(selectedBirdImageName);
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.dispose();
    }

    /**
     * Creates an ImageIcon from the given file
     *
     * @param fileName name of file with image
     * @return An ImageIcon created from the given file
     */
    //ChatGPT
    public ImageIcon createImageIcon(String fileName) {
        URL imgURL = getClass().getResource(fileName);
        return new ImageIcon(imgURL);
    }
}
