package model;

import org.junit.jupiter.api.TestMethodOrder;

// The Cube class represents the cube that the user will be controlling 
// with set speed, borders, and position 
public class Cube {
    private double speedX; // X velocity of the cube
    private double speedY; // Y velocity of the cube

    private int x; // the X value position of the cube
    private int y; // the Y value position of the cube
    private int width; // width of the cube
    private int height; // height of the cube

    /*
     * REQUIRES: x and y positions to be valid positions on the frame
     * EFFECTS: x is set to a given x; y is set to a given y; width and
     * height are always set to 50 and 100
     */
    public Cube(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 50;
        this.height = 100;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
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
        this.x += this.speedX;
    }

    /*
     * MODIFIES: this
     * EFFECTS: x position is decremented based on velocity speedX
     */
    public void moveLeft() {
        this.x -= this.speedX;
    }

    /*
     * MODIFIES: this
     * EFFECTS: y position is incremented based on velocity speedY,
     * but eventually reaches a maximum height and falls
     */
    public void jump() {
        int originalY = this.y;
        this.y += this.speedY;
        if (y > originalY + 50) {
            this.y -= this.speedY;
        }
    }

    /*
     * REQUIRES: x and y positions are touching a platform and a platform is
     * specified
     * MODIFIES: this
     * EFFECTS: keeps the corresponding speedX or speedY (or both) to 0
     * and returns true or false based on if collision is detected
     */
    public boolean detectCollision(Platform p) {
        if (this.width + this.x >= p.getX() - p.getWidth() && this.speedX > 0) {
            speedX = 0.0;
            return true;
        } else if (this.x - this.width <= p.getWidth() + p.getX() && this.speedX < 0) {
            speedX = 0.0;
            return true;
        } else if (this.height + this.y >= p.getY() - p.getHeight() && this.speedY > 0) {
            speedY = 0.0;
            return true;
        } else if (this.y - this.height <= p.getHeight() + p.getY() && this.speedY < 0) {
            speedY = 0.0;
            return true;
        } else {
            return false;
        }
    }
}
