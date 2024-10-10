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

    public int getWidth() {
        // TODO: implement
        return 0;
    }

    public int getHeight() {
        // TODO: implement
        return 0;
    }
    public boolean getIsLava() {
        // TODO: implement
        return false;
    }

    /* REQUIRES: width >= 0 and height >= 0
     * EFFECTS: gives the right border of the platform
     */
    public int borderRight() {
        // TODO: implement
        return 0;
    }

    /* REQUIRES: width >= 0 and height >= 0
     * EFFECTS: gives the left border of the platform
     */
    public int borderLeft() {
        // TODO: implement
        return 0;
    }

    /* REQUIRES: width >= 0 and height >= 0
     * EFFECTS: gives the bottom border of the platform
     */
    public int borderBottom() {
        // TODO: implement
        return 0;
    }

    /* REQUIRES: width >= 0 and height >= 0
     * EFFECTS: gives the top border of the platform
     */
    public int borderTop() {
        // TODO: implement
        return 0;
    }
}
