package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.JsonWriter;
import ui.ConsoleFrame;

// Constructs the frame which the game is played on
public class LevelFrame {
    private Object[][] frame; // 2D array for the platfomer
    private Cube cube; // cube controlled by the user
    private Collectible collectible; // collectible that is the end goal
    private int rand; // random number
    private static ArrayList<Collectible> clist = new ArrayList<>(); // list of collected collectibles
    private static ArrayList<Object[][]> levels = new ArrayList<>(); // list of levels finished or randomly added

    /*
     * REQUIRES: Indexes within the bounds of the 2D array
     * EFFECTS: cube is always set to (1,2); rand is set to a random index within
     * 0-7;
     * collectible is set to (rand, rand); cList is instantiated as an ArrayList;
     * frame is set as an 8x8 2D array; cube is given the index of (1,2); and
     * the platforms of the frame are randomly generated with a 40% chance of
     * occuring; with a 20% chance of it being lava
     * where there can only be a max of 3 in each row
     */
    public LevelFrame() {
        cube = new Cube(1, 2);
        rand = (int) (Math.random() * 8);
        collectible = new Collectible(rand, rand);
        frame = new Object[8][8];
        frame[rand][rand] = collectible;
        frame[1][2] = cube;
        int platformCount = 0;
        for (int i = 0; i < frame.length; i++) {
            for (int j = 0; j < frame[0].length; j++) {
                double f = Math.random();
                if (f <= 0.4 && frame[i][j] == null && platformCount < 3) {
                    if (Math.random() < 0.50) {
                        frame[i][j] = new Platform(i, j, 50, 50, false);
                    } else {
                        frame[i][j] = new Platform(i, j, 50, 50, true);
                    }
                    platformCount++;
                }
            }
            platformCount = 0;
        }
        makePath(rand, rand);
    }

    /*
     * REQUIRES: a frame generated in the same format, and valid indexes for the
     * frame
     * EFFECTS: same as the no-argument constructor but uses a given frame instead
     * of generating one
     */
    public LevelFrame(Object[][] frame) {
        cube = new Cube(1, 2);
        clist = new ArrayList<>();
        levels = new ArrayList<>();
        rand = (int) (Math.random() * 8);
        collectible = new Collectible(rand, rand);
        this.frame = frame;
        frame[1][2] = cube;
        makePath(rand, rand);
    }

    public LevelFrame(Object[][] frame, Collectible c, int prevRand) {
        cube = new Cube(1, 2);
        clist = new ArrayList<>();
        levels = new ArrayList<>();
        rand = prevRand;
        collectible = c;
        this.frame = frame;
        frame[1][2] = cube;
        frame[rand][rand] = collectible;
        makePath(rand, rand);
    }

    public Object[][] getFrame() {
        return this.frame;
    }

    public static ArrayList<Object[][]> getLevels() {
        return levels;
    }

    public static ArrayList<Collectible> getClist() {
        return clist;
    }

    /*
     * REQUIRES: the x and y position of the Collectible object, distX <
     * frame.length && distY < frame[0].length
     * EFFECTS: creates a path to the Collectible
     * MODIFIES: frame
     */
    private void makePath(int distX, int distY) {
        if (distX < 1 || distY < 2) {
            pathShort();
        } else {
            pathLong(distX, distY);
        }
    }

    /*
     * EFFECTS: creates a short path to the collectible
     */
    private void pathShort() {
        frame[0][1] = null;
        frame[0][2] = null;
    }

    private void pathLong(int distX, int distY) {
        for (int j = 2; j < distX; j++) {
            if (!(frame[j][2] == null)) {
                frame[j][2] = null;
            }
        }
        for (int i = 3; i < distY; i++) {
            if (!(frame[distX][i] == null)) {
                frame[distX][i] = null;
            }
        }
    }

    /*
     * REQUIRES: collectible and cube on the same indexes
     * MODIFIES: cList
     * EFFECTS: if the cube and the collectible are on the same indexes, then
     * return true and add the collectible to cList
     */
    public boolean checkCollectible() {
        this.collectible.collect(this.cube);
        if (collectible.getIsCollected()) {
            return true;
        }
        return false;
    }

    /*
     * EFFECTS: resets the position of cube and collectible
     */
    public void resetPosition() {
        cube.resetPosition();
        frame[1][2] = cube;
        frame[rand][rand] = collectible;
    }

    public void saveLevel() {
        JsonWriter writer = new JsonWriter("./data/autosave.json");
        levels.add(frame);
        EventLog.getInstance().logEvent(new Event("Level added to level list"));
        clist.add(collectible);
        EventLog.getInstance().logEvent(new Event("Collectible added to collectible list"));
        try {
            writer.open();
            writer.autoWrite();
            System.out.println("Your level and collectible has been saved.");
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file");
        }
    }

    /*
     * REQUIRES: autosave.json and levelframe.json
     * EFFECTS: deletes the files for autosave.json and levelframe.json
     */
    public static void resetSave(boolean print) {
        String fileName1 = "./data/autosave.json";
        String fileName2 = "./data/levelframe.json";
        levels.clear();
        EventLog.getInstance().logEvent(new Event("All levels were removed"));
        clist.clear();
        EventLog.getInstance().logEvent(new Event("All collected collectibles were removed"));
        determinePrint(print, fileName1, fileName2);
    }

    private static void determinePrint(boolean print, String fileName1, String fileName2) {
        try {
            Files.delete(Paths.get(fileName1));
            if (print) {
                System.out.println("Successfully deleted the levels save.");
            }
        } catch (IOException e) {
            if (print) {
                System.out.println("Save not found.");
            }
        }
        try {
            Files.delete(Paths.get(fileName2));
            if (print) {
                System.out.println("Successfully deleted the previous level save.");
            }
        } catch (IOException e) {
            if (print) {
                System.out.println("No previous level found.");
            }
        }
    }

    /*
     * REQUIRES: cube.getX() - 1 >= 0 && frame[cube.getX()-1][cube.getY()] == null
     * EFFECTS: moves the cube up if there is no platform
     */
    public void moveUp() {
        ConsoleFrame.clearScreen();
        if (cube.getX1() - 1 >= 0) {
            if (frame[cube.getX1() - 1][cube.getY1()] == null
                    || (frame[cube.getX1() - 1][cube.getY1()] instanceof Collectible)) {
                frame[cube.getX1() - 1][cube.getY1()] = cube;
                frame[cube.getX1()][cube.getY1()] = null;
                cube.moveLeft();
            } else if (frame[cube.getX1() - 1][cube.getY1()].toString().substring(0, 14).equals("model.Platform")) {
                determinePlatform((Platform) frame[cube.getX1() - 1][cube.getY1()], 1);
            }
        } else {
            System.out.println("Invalid action, please try 'a', 's', or 'd'");
        }
    }

    /*
     * REQUIRES: cube.getY() + 1 >= 0 && frame[cube.getX()][cube.getY()+1] == null
     * EFFECTS: moves the cube right if there is no platform
     */
    public void moveRight() {
        ConsoleFrame.clearScreen();
        if (cube.getY1() + 1 < frame[0].length) {
            if (frame[cube.getX1()][cube.getY1() + 1] == null
                    || (frame[cube.getX1()][cube.getY1() + 1] instanceof Collectible)) {
                frame[cube.getX1()][cube.getY1() + 1] = cube;
                frame[cube.getX1()][cube.getY1()] = null;
                cube.jump();
            } else if (frame[cube.getX1()][cube.getY1() + 1].toString().substring(0, 14).equals("model.Platform")) {
                determinePlatform((Platform) frame[cube.getX1()][cube.getY1() + 1], 2);
            }
        } else {
            System.out.println("Invalid action, please try 'w', 's', or 'd'");
        }
    }

    /*
     * REQUIRES: cube.getX() + 1 >= 0 && frame[cube.getX()+1][cube.getY()] == null
     * EFFECTS: moves the cube down if there is no platform
     */
    public void moveDown() {
        ConsoleFrame.clearScreen();
        if (cube.getX1() + 1 < frame.length) {
            if (frame[cube.getX1() + 1][cube.getY1()] == null
                    || (frame[cube.getX1() + 1][cube.getY1()] instanceof Collectible)) {
                frame[cube.getX1() + 1][cube.getY1()] = cube;
                frame[cube.getX1()][cube.getY1()] = null;
                cube.moveRight();
            } else if (frame[cube.getX1() + 1][cube.getY1()].toString().substring(0, 14).equals("model.Platform")) {
                determinePlatform((Platform) frame[cube.getX1() + 1][cube.getY1()], 3);
            }
        } else {
            System.out.println("Invalid action, please try 'a', 'w', or 'd'");
        }
    }

    /*
     * REQUIRES: cube.getY() - 1 >= 0 && frame[cube.getX()][cube.getY()-1] == null
     * EFFECTS: moves the cube left if there is no platform
     */
    public void moveLeft() {
        ConsoleFrame.clearScreen();
        if (cube.getY1() - 1 >= 0) {
            if (frame[cube.getX1()][cube.getY1() - 1] == null
                    || (frame[cube.getX1()][cube.getY1() - 1] instanceof Collectible)) {
                frame[cube.getX1()][cube.getY1() - 1] = cube;
                frame[cube.getX1()][cube.getY1()] = null;
                cube.fall();
            } else if (frame[cube.getX1()][cube.getY1() - 1].toString().substring(0, 14).equals("model.Platform")) {
                determinePlatform((Platform) frame[cube.getX1()][cube.getY1() - 1], 4);
            }
        } else {
            System.out.println("Invalid action, please try 'a', 'w', or 's'");
        }
    }

    /*
     * REQUIRES: p, direction
     * EFFECTS: determines the type of platform and prints the corresponding message
     * and determines direction by representing directions in numbers 1 to 4 as
     * north to west
     */
    private void determinePlatform(Platform p, int direction) {
        if (p.getIsLava()) {
            frame[cube.getX1()][cube.getY1()] = null;
            cube.resetPosition();
            frame[1][2] = cube;
            System.out.println("You ran into lava! Your position is reset.");
        } else {
            printDirection(direction);
        }
    }

    /*
     * REQUIRES: direction
     * EFFECTS: prints the direction where the action is invalid
     */
    private void printDirection(int direction) {
        switch (direction) {
            case 1:
                System.out.println("Invalid action, please try 'a', 's', or 'd'");
                break;
            case 2:
                System.out.println("Invalid action, please try 'w', 's', or 'd'");
                break;
            case 3:
                System.out.println("Invalid action, please try 'a', 'w', or 'd'");
                break;
            case 4:
                System.out.println("Invalid action, please try 'a', 'w', or 's'");
                break;
        }
    }

    /*
     * REQUIRES: JSONArray
     * EFFECTS: converts object data to json formatting
     */
    public JSONArray toJson() {
        JSONArray json = new JSONArray();
        json.put(frame);

        return json;
    }

    public static JSONObject autoSave() {
        JSONObject json = new JSONObject();
        json.put("collectibles", clistToJson());
        json.put("levels", levelsToJson());
        return json;
    }

    private static JSONArray clistToJson() {
        JSONArray json = new JSONArray();
        for (Collectible c : clist) {
            json.put(c.toJson());
        }
        return json;
    }

    private static JSONArray levelsToJson() {
        JSONArray json = new JSONArray();
        for (Object[][] f : levels) {
            json.put(f);
        }
        return json;
    }

    public static void printLog() {
        for (Event e : EventLog.getInstance()) {
            System.out.println(e);
        }
    }
}
