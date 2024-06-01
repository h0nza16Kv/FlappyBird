import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class BirdChooseTest {
    private BirdChoose birdChoose;

    @BeforeEach
    public void setUp() {
        Menu menuPanel = new Menu();
        birdChoose = new BirdChoose(menuPanel);
    }

    @org.junit.jupiter.api.Test
    public void testGetCurrentBirdIndex() {
        assertEquals(0, birdChoose.getCurrentBirdIndex());
        int newCurrentBirdIndex = 2;
        birdChoose.setCurrentBirdIndex(newCurrentBirdIndex);
        assertEquals(newCurrentBirdIndex, birdChoose.getCurrentBirdIndex());
    }
}
