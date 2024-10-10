package model;

// The Collectible class represents unique collectibles
// where it can be picked up and stored by the Cube user
public class Collectible {
    private int x;              // x pos
    private int y;              // y pos
    private boolean collected;  // false for not collected, true for collected
    private static int idCount; // static number incremented for each Collectible instantiated
    private int id;             // unique id for each collectible

    /* REQUIRES: x and y positions to be within the boundaries of the frame
     * EFFECTS: x and y are set to their given values; collected always 
     *          begins as false; idCount is incremented and id is set to 
     *          idCount
     */
    public Collectible(int x, int y) {
        this.x = x;
        this.y = y;
        this.collected = false;
        this.id = idCount;
        idCount++;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public boolean getIsCollected() {
        return this.collected;
    }

    public int getId() {
        return this.id;
    }

    /* REQUIRES: a Cube object must collide with the Collectible
     * MODFIES: this
     * EFFECTS: collected is set to true
     */
    public void collect(Cube c) {
        if (c.getX() == this.x && c.getY() == this.y) {
            collected = true;
        }
    }
}
