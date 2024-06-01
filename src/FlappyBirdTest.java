import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

public class FlappyBirdTest {
    private FlappyBird flappyBird;
    private JFrame testFrame;

    @BeforeEach
    public void setUp() {
        Image birdImage = new BufferedImage(40, 40, BufferedImage.TYPE_INT_ARGB);
        flappyBird = new FlappyBird(birdImage);

        flappyBird.getPipes().clear();
        flappyBird.getPowerUps().clear();
        flappyBird.setPipeCounter(0);

        testFrame = new JFrame();
        testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        testFrame.getContentPane().add(flappyBird);
        testFrame.pack();
        testFrame.setVisible(true);
    }

    @org.junit.jupiter.api.Test
    public void testPlacePipes() {
        assertEquals(0, flappyBird.getPipes().size());

        flappyBird.placePipes();

        assertEquals(2, flappyBird.getPipes().size());

        Pipe topPipe = flappyBird.getPipes().get(0);
        Pipe bottomPipe = flappyBird.getPipes().get(1);

        assertEquals(flappyBird.getBoardWidth() + flappyBird.getPipeWidth(), topPipe.getX());
        assertEquals(0, topPipe.getY());
        assertEquals(flappyBird.getPipeWidth(), topPipe.getWidth());

        int gap = 200;
        assertEquals(flappyBird.getBoardWidth() + flappyBird.getPipeWidth(), bottomPipe.getX());
        assertEquals(topPipe.getHeight() + gap, bottomPipe.getY());
        assertEquals(flappyBird.getPipeWidth(), bottomPipe.getWidth());
    }

    @org.junit.jupiter.api.Test
    public void testPlacePipesWithPowerUp() {
        flappyBird.setPipeCounter(14);
        flappyBird.placePipes();
        assertEquals(2, flappyBird.getPipes().size());
        assertEquals(1, flappyBird.getPowerUps().size());
    }

}


