package model;

// The Platform class represents each block that the user as a Cube
// moves on and collides with
public class Platform {
    private int x1;          // x pos
    private int y1;          // y pos
    private int width;      // width of the platform
    private int height;     // height of the platform
    private boolean isLava; // if true and collides with the Cube, 
                            // reset the position of the Cube

    /* REQUIRES: x and y need to be within the boundaries of the frame
     * EFFECTS: x and y positions are set; width and height are given 
     *          dimensions; and is determined if isLava
    */
    public Platform(int x, int y, int width, int height, boolean isLava) {
        this.x1 = x;
        this.y1 = y;
        this.width = width;
        this.height = height;
        this.isLava = isLava;
    }

    public int getX1() {
        return this.x1;
    }

    public int getY1() {
        return this.y1;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
    
    public boolean getIsLava() {
        return this.isLava;
    }

    /* REQUIRES: width >= 0 and height >= 0
     * EFFECTS: gives the right border of the platform
     */
    public int borderRight() {
        return this.x1 + this.width;
    }

    /* REQUIRES: width >= 0 and height >= 0
     * EFFECTS: gives the left border of the platform
     */
    public int borderLeft() {
        return this.x1 - this.width;
    }

    /* REQUIRES: width >= 0 and height >= 0
     * EFFECTS: gives the bottom border of the platform
     */
    public int borderBottom() {
        return this.y1 - this.height;
    }

    /* REQUIRES: width >= 0 and height >= 0
     * EFFECTS: gives the top border of the platform
     */
    public int borderTop() {
        return this.y1 + this.height;
    }
}
