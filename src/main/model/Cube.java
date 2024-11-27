package model;

import org.json.JSONObject;
import persistence.Writable;

// The Cube class represents the cube that the user will be controlling 
// with set speed, borders, and position 
public class Cube implements Writable {
    private Type type;
    private double speedX; // X velocity of the cube
    private double speedY; // Y velocity of the cube

    private int x1; // the X value position of the cube
    private int y1; // the Y value position of the cube

    /*
     * REQUIRES: x and y positions to be valid positions on the frame
     * EFFECTS: x is set to a given x; y is set to a given y; speedX 
     * and speedY is set to 1.0 at the beginning
     */
    public Cube(int x, int y) {
        this.x1 = x;
        this.y1 = y;
        this.speedX = 1.0;
        this.speedY = 1.0;
        this.type = Type.values()[1];
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
     * EFFECTS: y position is incremented based on velocity speedY
     */
    public void jump() {
        this.speedY = 1.0;
        this.y1 += this.speedY;
    }

    /*
     * MODIFIES: this
     * EFFECTS: y position is decremented based on velocity speedY
     */
    public void fall() {
        this.speedY = -1.0;
        this.y1 += this.speedY;
    }

    /* 
     * REQUIRES: x1 and y1
     * EFFECTS: this
     */
    public void resetPosition() {
        this.x1 = 1;
        this.y1 = 2;
    }

    /* 
     * REQUIRES: JSONObject
     * EFFECTS: converts object data to json formatting
     */
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("type", type);
        json.put("x value", x1);
        json.put("y value", y1);
        return json;
    }
}
