package persistence;
import ui.LevelFrame;

import org.json.JSONObject;
import java.io.*;

public class JsonWriter {
    private PrintWriter writer; // writes to file
    private String destination; // given destination address to write to

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }
    
    // MODIFIES: this
    // EFFECTS: writes JSON representation of workroom to file
    public void write(LevelFrame lf) {
        JSONObject json = lf.toJson();
        writer.print(json.toString(3));
    }
}
