import java.awt.*;

public class Bird {
    private Image img;
    private int x;
    private int y;
    private int width;
    private int height;
    private int velocityY;
    private int gravity;

    public Bird(Image img, int x, int y, int width, int height) {
        this.img = img;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.gravity = 1;
    }

    public Image getImg() {
        return img;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(int velocityY) {
        this.velocityY = velocityY;
    }

    public int getGravity() {
        return gravity;
    }

    public void setX(int x) {
        this.x = x;
    }
}


