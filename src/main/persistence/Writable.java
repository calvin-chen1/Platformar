package persistence;

import org.json.JSONObject;

/* 
 * implements JSONObject's toJson method for any class
 */
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
