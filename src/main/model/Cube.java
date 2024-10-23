package model;

import org.junit.jupiter.api.TestMethodOrder;

// The Cube class represents the cube that the user will be controlling 
// with set speed, borders, and position 
public class Cube {
    private double speedX; // X velocity of the cube
    private double speedY; // Y velocity of the cube

    private int x1; // the X value position of the cube
    private int y1; // the Y value position of the cube
    private int width; // width of the cube
    private int height; // height of the cube

    /*
     * REQUIRES: x and y positions to be valid positions on the frame
     * EFFECTS: x is set to a given x; y is set to a given y; width and
     * height are always set to 50 and 100; speedX and speedY is set to
     * 1.0 at the beginning
     */
    public Cube(int x, int y) {
        this.x1 = x;
        this.y1 = y;
        this.width = 50;
        this.height = 100;
        this.speedX = 1.0;
        this.speedY = 1.0;
    }

    public int getX1() {
        return this.x1;
    }

    public int getY1() {
        return this.y1;
    }

    public double getSpeedX() {
        return this.speedX;
    }

    public double getSpeedY() {
        return this.speedY;
    }

    /*
     * MODIFIES: this
     * EFFECTS: x position is incremented based on velocity speedX
     */
    public void moveRight() {
        this.speedX = 1.0;
        this.x1 += this.speedX;
    }

    /*
     * MODIFIES: this
     * EFFECTS: x position is decremented based on velocity speedX
     */
    public void moveLeft() {
        this.speedX = -1.0;
        this.x1 += this.speedX;
    }

    /*
     * MODIFIES: this
     * EFFECTS: y position is incremented based on velocity speedY,
     * but eventually reaches a maximum height and falls
     */
    public void jump() {
        speedY = 1.0;
        int originalY = this.y1;
        this.y1 += this.speedY;
        if (y1 > originalY + 50) {
            fall();
        }
    }

    public void fall() {
        this.speedY = -1.0;
        this.y1 += this.speedY;
    }

    /*
     * REQUIRES: x and y positions are touching a platform and a platform is
     * specified
     * MODIFIES: this
     * EFFECTS: keeps the corresponding speedX or speedY (or both) to 0
     * and returns true or false based on if collision is detected
     */
    public boolean detectCollision(Platform p) {
        if (this.width + this.x1 >= p.getX1() - p.getWidth() && this.speedX > 0) {
            speedX = 0.0;
            return true;
        } else if (this.x1 - this.width <= p.getWidth() + p.getX1() && this.speedX < 0) {
            speedX = 0.0;
            return true;
        } else if (this.height + this.y1 >= p.getY1() - p.getHeight() && this.speedY > 0) {
            speedY = 0.0;
            return true;
        } else if (this.y1 - this.height <= p.getHeight() + p.getY1() && this.speedY < 0) {
            speedY = 0.0;
            return true;
        } else {
            return false;
        }
    }
}
