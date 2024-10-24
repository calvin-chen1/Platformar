package model;

import org.json.JSONObject;
import persistence.Writable;

// The Collectible class represents unique collectibles
// where it can be picked up and stored by the Cube user
public class Collectible implements Writable {
    private Type type;
    private int x1;              // x pos
    private int y1;              // y pos
    private boolean collected;  // false for not collected, true for collected
    private static int idCount; // static number incremented for each Collectible instantiated
    private int id;             // unique id for each collectible

    /* REQUIRES: x and y positions to be within the boundaries of the frame
     * EFFECTS: x and y are set to their given values; collected always 
     *          begins as false; idCount is incremented and id is set to 
     *          idCount
     */
    public Collectible(int x, int y) {
        this.x1 = x;
        this.y1 = y;
        this.collected = false;
        this.id = idCount;
        idCount++;
        this.type = Type.values()[0];
    }

    public int getX1() {
        return this.x1;
    }

    public int getY1() {
        return this.y1;
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
        if (c.getX1() == this.x1 && c.getY1() == this.y1) {
            collected = true;
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("type", type);
        json.put("x value", x1);
        json.put("y value", y1);
        json.put("collected", collected);
        json.put("id", id);
        return json;
    }
}
