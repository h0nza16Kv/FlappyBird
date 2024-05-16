import java.awt.*;

public class Pipe {
    private Image img;
    private int x;
    private int y;
    private int width;
    private int height;
    private int velocityX;
    private boolean passed;

    public Pipe(Image img, int x, int y, int width, int height) {
        this.img = img;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.velocityX = -4;
        this.passed = false;
    }

    public Image getImg() {
        return img;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getVelocityX() {
        return velocityX;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }
}