import static org.junit.jupiter.api.Assertions.*;

public class MenuTest {


    @org.junit.jupiter.api.Test
    public void testSetSelectedBirdImage() {
        Menu menu = new Menu();
        String birdImage = "1.png";
        menu.setSelectedBirdImage(birdImage);
        assertEquals(birdImage, menu.getSelectedBirdImage());
    }

}
