import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.*;

public class LeaderboardTest {
    private final String testFilename = "test.txt";

    @BeforeEach
    public void setUp() {
        clearFile(testFilename);
    }

    @org.junit.jupiter.api.Test
    public void testLoadScoresFromFile() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(testFilename))) {
            writer.write("74\n68\n51\n46\n34");
        }
        Leaderboard leaderboard = new Leaderboard();
        leaderboard.loadScoresFromFile();
        assertEquals(5, leaderboard.getScores().size());
        assertEquals(74, leaderboard.getScores().get(0));
        assertEquals(68, leaderboard.getScores().get(1));
        assertEquals(51, leaderboard.getScores().get(2));
        assertEquals(46, leaderboard.getScores().get(3));
        assertEquals(34, leaderboard.getScores().get(4));
    }

    private void clearFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
