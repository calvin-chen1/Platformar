package model;

// The Platform class represents each block that the user as a Cube
// moves on and collides with
public class Platform {
    private int x;          // x pos
    private int y;          // y pos
    private int width;      // width of the platform
    private int height;     // height of the platform
    private boolean isLava; // if true and collides with the Cube, 
                            // reset the position of the Cube

    /* REQUIRES: x and y need to be within the boundaries of the frame
     * EFFECTS: x and y positions are set; width and height are given 
     *          dimensions; and is determined if isLava
    */
    public Platform(int x, int y, int width, int height, boolean isLava) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.isLava = isLava;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
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
        return this.x + this.width;
    }

    /* REQUIRES: width >= 0 and height >= 0
     * EFFECTS: gives the left border of the platform
     */
    public int borderLeft() {
        return this.x - this.width;
    }

    /* REQUIRES: width >= 0 and height >= 0
     * EFFECTS: gives the bottom border of the platform
     */
    public int borderBottom() {
        return this.y - this.width;
    }

    /* REQUIRES: width >= 0 and height >= 0
     * EFFECTS: gives the top border of the platform
     */
    public int borderTop() {
        return this.y + this.width;
    }
}
