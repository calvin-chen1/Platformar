package ui;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONObject;

import model.Cube;
import model.Platform;
import model.Collectible;
import persistence.JsonWriter;

// Constructs the frame which the game is played on
public class LevelFrame {
    private Object[][] frame; // 2D array for the platfomer
    private Cube cube; // cube controlled by the user
    private Collectible collectible; // collectible that is the end goal
    private int rand; // random number
    private static ArrayList<Collectible> clist = new ArrayList<>(); // list of collected collectibles
    private static ArrayList<Object[][]> levels = new ArrayList<>(); // list of levels finished or randomly added

    private static Scanner in = Main.in;

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
     * EFFECTS: clears the console screen
     */
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
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
     * REQUIRES: frame
     * EFFECTS: prints out the frame that was generated/given, and
     * each symbol is represented as an icon shaped similarly
     * to a block
     */
    public void draw() {
        System.out.println();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Object current = this.frame[i][j];
                if (current == null) {
                    System.out.print("    ");
                } else if (current.toString().substring(0, 14).equals("model.Platform")) {
                    Platform temp = (Platform) current;
                    if (temp.getIsLava()) {
                        System.out.print("[=] ");
                    } else {
                        System.out.print("[-] ");
                    }
                } else if (current.equals(this.cube)) {
                    System.out.print("{:} ");
                } else {
                    System.out.print("(~) ");
                }
            }
            System.out.println();
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
     * REQUIRES: cList
     * EFFECTS: prints out all of the collectibles along with their respective IDs
     */
    public void viewCollectibles() {
        System.out.println("Welcome to your collectible collection! Take a look.");
        for (Collectible c : clist) {
            System.out.println("Collectible #" + c.getId());
        }
    }

    /*
     * REQUIRES: draw, move, frame
     * EFFECTS: the game is began through this method and controls the movement
     * of the cube, while checking if the collectible is on the same index
     * each movement and saving frame and the collectible after the game is
     * complete
     */
    public void start() {
        System.out.println("[=] is lava. {:} is you. (~) is your goal.");
        draw();
        System.out
                .println("Controls: Type w for up, d for right, s for down, a for left. Type /q if you wish to leave.");
        while (!checkCollectible()) {
            String input = in.next();
            move(input);
        }
        frame[1][2] = cube;
        frame[rand][rand] = collectible;
        savePrompt(in);
        replayPrompt(in);
    }

    public void saveLevel() {
        JsonWriter writer = new JsonWriter("./data/autosave.json");
        levels.add(frame);
        clist.add(collectible);
        try {
            writer.open();
            writer.autoWrite();
            System.out.println("Your level and collectible has been saved.");
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file");
        }
    }

    private void savePrompt(Scanner in) {
        String input = null;
        if (!containsLevelInSave()) {
            System.out.println("Congratulations on finishing! Would you like to save your data? (y/n)");
            do {
                input = in.next().toLowerCase();
                if (input.equals("y")) {
                    saveLevel();
                    break;
                }
            } while (!input.equals("n"));
        }
    }

    private boolean containsLevelInSave() {
        for (Object[][] f : levels) {
            if (Arrays.deepEquals(f, frame)) {
                return true;
            }
        }
        return false;
    }

    private void replayPrompt(Scanner in) {
        System.out.println("Do you want to replay? (y/n)");
        String input = in.next().toLowerCase();
        do {
            if (input.equals("y")) {
                Main.restart = true;
                break;
            } else if (input.equals("n")) {
                Main.restart = false;
            }

        } while (!input.equals("n"));
    }

    /*
     * REQUIRES: in >= 0
     * EFFECTS: used to decrease the amount of lines that the movement functions
     * take up
     */
    public void move(String in) {
        switch (in) {
            case "w":
                moveUp();
                draw();
                break;
            case "d":
                moveRight();
                draw();
                break;
            case "s":
                moveDown();
                draw();
                break;
            case "a":
                moveLeft();
                draw();
                break;
            case "/q":
                Main.savePreviousLevel(frame);
                System.exit(0);
                break;
        }
    }

    /*
     * REQUIRES: cube.getX() - 1 >= 0 && frame[cube.getX()-1][cube.getY()] == null
     * EFFECTS: moves the cube up if there is no platform
     */
    public void moveUp() {
        clearScreen();
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
        clearScreen();
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
        clearScreen();
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
        clearScreen();
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
     * and determines direction by representing directions in numbers 1 to 4 as north to west
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
}
