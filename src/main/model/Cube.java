package model;

import org.junit.jupiter.api.TestMethodOrder;

// The Cube class represents the cube that the user will be controlling 
// with set speed, borders, and position 
public class Cube {
    private double speedX;     // X velocity of the cube
    private double speedY;     // Y velocity of the cube

    private int x;             // the X value position of the cube
    private int y;             // the Y value position of the cube
    private int width;         // width of the cube
    private int height;        // height of the cube

    /* REQUIRES: x and y positions to be valid positions on the frame
     * EFFECTS: x is set to a given x; y is set to a given y; width and 
     *          height are always set to 50 and 100
    */
    public Cube(int x, int y) {
        // TODO: implement
    }

    public int getX() {
        // TODO: implement
        return 0;
    }

    public int getY() {
        // TODO: implement
        return 0;
    }
    
    public double getSpeedX() {
        // TODO: implement
        return 0.0;
    }

    public double getSpeedY() {
        // TODO: implement
        return 0.0;
    }
    
    /* MODIFIES: this
     * EFFECTS: x position is incremented based on velocity speedX  
    */
    public void moveRight() {
        // TODO: implement
    }

    /* MODIFIES: this
     * EFFECTS: x position is decremented based on velocity speedX 
    */
    public void moveLeft() {
        // TODO: implement
    }

    /* MODIFIES: this
     * EFFECTS: y position is incremented based on velocity speedY,
     *          but eventually reaches a maximum height and falls 
    */
    public void jump() {
        // TODO: implement
    }

    /* REQUIRES: x and y positions are touching a platform
     * MODIFIES: this
     * EFFECTS: keeps the corresponding speedX or speedY (or both) to 0
     *          and returns true or false based on if collision is detected 
    */
    public boolean detectCollision() {
        // TODO: implement
        return false;
    }
}
