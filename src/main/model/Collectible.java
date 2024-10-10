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
        //TODO: implement   
    }

    public int getX() {
        // TODO: implement
        return 0;
    }

    public int getY() {
        // TODO: implement
        return 0;
    }

    public boolean getIsCollected() {
        // TODO: implement
        return false;
    }

    public int getId() {
        // TODO: implement
        return 0;
    }

    /* REQUIRES: a Cube object must collide with the Collectible
     * MODFIES: this
     * EFFECTS: collected is set to true
     */
    public void collect() {
        // TODO: implement
    }
}
