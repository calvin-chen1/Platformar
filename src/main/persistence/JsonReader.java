package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import org.json.*;
import java.util.HashMap;
import java.util.ArrayList;

import model.*;

public class JsonReader {
    private String source; // source address of the file\

    /* REQUIRES: source is a valid file address
     * EFFECTS: source is set to the source to read from
     */
    public JsonReader(String source) {
        this.source = source;
    }

    /*  
     * EFFECTS: reads the 2D array from the given file and returns a LevelFrame;
     * throws IOException if an error occurs reading data from file
     */
    public LevelFrame read() throws IOException {
        String jsonData = readFile(source);
        JSONArray jsonArray = new JSONArray(jsonData);
        return new LevelFrame(parseLevelFrame(jsonArray));
    }

    /* REQUIRES: autosave.json
     * EFFECTS: on application run, read the autosave.json file and store in clist and
     * levels
     */
    public void autoRead() throws IOException {
        ArrayList<Collectible> clist = LevelFrame.getClist();
        ArrayList<Object[][]> levels = LevelFrame.getLevels();
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        JSONArray jsonArray = jsonObject.getJSONArray("collectibles");
        for (Object json : jsonArray) {
            JSONObject collectible = (JSONObject) json;
            addCollectible(clist, collectible);
        }
        JSONArray jsonLevels = jsonObject.getJSONArray("levels");
        for (Object json : jsonLevels) {
            JSONArray level = (JSONArray) json;
            levels.add(parseLevels(level));
        }
    }
    
    /* 
     * EFFECTS: adds collectibles to clist from reading the autosave
     */
    private void addCollectible(ArrayList<Collectible> clist, JSONObject jsonObject) {
        int x = jsonObject.getInt("x value");
        int y = jsonObject.getInt("y value");
        Collectible c = new Collectible(x, y);
        clist.add(c);
    }

    /* REQUIRES: jsonArray
     * EFFECTS: goes through jsonArray and sets corresponding values to return as an Object[][]
     */
    private Object[][] parseLevels(JSONArray jsonArray) {
        Object[][] frame = new Object[8][8];
        List<Object> jsonList = jsonArray.toList();
        for (int i = 0; i < jsonList.size(); i++) {
            List<Object> arrList = (ArrayList<Object>) jsonList.get(i);
            for (int j = 0; j < arrList.size(); j++) {
                if (arrList.get(j) instanceof HashMap) {
                    attributeObject((HashMap<String, Object>) arrList.get(j), i, j, frame);
                } else {
                    frame[i][j] = null;
                }
            }
        }
        return frame;
    }

    /* REQUIRES: jsonArray
     * EFFECTS: goes through jsonArray and sets corresponding values to return as an Object[][]
     */
    private Object[][] parseLevelFrame(JSONArray jsonArray) {
        Object[][] frame = new Object[8][8];
        List<Object> jsonList = jsonArray.toList();
        List<Object> arrLists = (ArrayList<Object>) jsonList.get(0);
        for (int i = 0; i < arrLists.size(); i++) {
            List<Object> arrList = (ArrayList<Object>) arrLists.get(i);
            for (int j = 0; j < arrList.size(); j++) {
                if (arrList.get(j) instanceof HashMap) {
                    attributeObject((HashMap<String, Object>) arrList.get(j), i, j, frame);
                } else {
                    frame[i][j] = null;
                }
            }
        }
        return frame;
    }

    /* REQUIRES: HashMap, and indexes for frame
     * EFFECTS: sets objects with info obtained from the hashmaps
     */
    private void attributeObject(HashMap<String, Object> h, int i, int j, Object[][] frame) {
        if (h.toString().charAt(13) == 'i') {
            frame[i][j] = new Collectible(i, j);
        } else if (h.containsKey("speedX")) {
            frame[i][j] = new Cube(i, j);
        } else if (h.containsKey("isLava")) {
            boolean isLava = (boolean) h.get("isLava");
            frame[i][j] = new Platform(i, j, 50, 50, isLava);
        } 
    }

    /* 
     * EFFECTS: reads source file as string and returns it  
     */
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }
}
