package ui;

import java.util.ArrayList;
import java.util.Scanner;

import model.Cube;
import model.Platform;
import model.Collectible;

// Constructs the frame which the game is played on
public class LevelFrame {
    private Object[][] frame; // 2D array for the platfomer
    private Cube cube; // cube controlled by the user
    private Collectible collectible; // collectible that is the end goal
    private int rand; // random number
    private static ArrayList<Collectible> cList; // list of collected collectibles

    /*
     * REQUIRES: Indexes within the bounds of the 2D array
     * EFFECTS: cube is always set to (1,2); rand is set to a random index within
     * 0-7;
     * collectible is set to (rand, rand); cList is instantiated as an ArrayList;
     * frame is set as an 8x8 2D array; cube is given the index of (1,2); and
     * the platforms of the frame are randomly generated with a 50% chance of
     * occuring;
     * where there can only be a max of 3 in each row; this can result in the cube
     * being
     * trapped in some generations
     */
    public LevelFrame() {
        cube = new Cube(1, 2);
        rand = (int) (Math.random() * 8);
        collectible = new Collectible(rand, rand);
        cList = new ArrayList<>();
        frame = new Object[8][8];
        frame[rand][rand] = collectible;
        frame[1][2] = cube;
        int platformCount = 0;
        for (int i = 0; i < frame.length; i++) {
            for (int j = 0; j < frame[0].length; j++) {
                double f = Math.random();
                if (platformCount < 3) {
                    if (f <= 0.5 && frame[i][j] == null) {
                        frame[i][j] = new Platform(i, j, 50, 50, false);
                        platformCount++;
                    } else if (f >= 0.8 && frame[i][j] == null) {
                        frame[i][j] = new Platform(i, j, 50, 50, true);
                        platformCount++;
                    }
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
        cList = new ArrayList<>();
        rand = (int) (Math.random() * 8);
        collectible = new Collectible(rand, rand);
        this.frame = frame;
        frame[1][2] = cube;
        makePath(rand, rand);
    }

    public Object[][] getFrame() {
        return this.frame;
    }

    /* 
     * REQUIRES: the x and y position of the Collectible object, distX < frame.length && distY < frame[0].length
     * EFFECTS: creates a path to the Collectible
     * MODIFIES: frame
     */
    private void makePath(int distX, int distY) {
        double pathChoose = Math.random();
        if (distX < 1 || distY < 2) 
            pathShort();
        else if (pathChoose < 0.50) {
            for (int i = 3; i < distY; i++) {
                if (frame[1][i] != null) 
                    frame[1][i] = null; 
            }
            for (int j = 2; j < distX; j++) {
                if (frame[j][distY] != null) 
                    frame[j][distY] = null;
            }
        } else {
            for (int j = 2; j < distX; j++) {
                if (frame[j][2] != null) 
                    frame[j][2] = null;
            }
            for (int i = 3; i < distY; i++) {
                if (frame[distX][i] != null) 
                    frame[distX][i] = null; 
            }
        }
    }

    private void pathShort() {
        frame[0][1] = null;
        frame[0][2] = null;
    }

    /*
     * REQUIRES: frame
     * EFFECTS: prints out the frame that was generated/given, and
     * each symbol is represented as an icon shaped similarly
     * to a block
     */
    public void draw() {
        frame[rand][rand] = collectible;
        System.out.println();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Platform f = new Platform(i, j, 50, 50, true);
                Object current = this.frame[i][j];
                if (current == null) {
                    System.out.print("    ");
                } else if (current.equals(f)) {
                    System.out.print("[=] ");
                } else if (current.equals(this.cube)) {
                    System.out.print("{:} ");
                } else if (current.equals(this.collectible)) {
                    System.out.print("(~) ");
                    this.frame[i][j] = null;
                } else {
                    System.out.print("[-] ");
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
            cList.add(collectible);
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
        for (Collectible c : cList) {
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
        Scanner in = new Scanner(System.in);
        System.out.println("[-] is lava. {:} is you. (~) is your goal.");
        draw();
        System.out.println("Controls: Type w for up, d for right, s for down, a for left.");
        while (!checkCollectible()) {
            String input = in.next();
            move(input);
        }
        in.close();
        frame[cube.getX1()][cube.getY1()] = null;
        Main.saveLevel(this.frame);
        System.out.println("Congratulations on finishing! Your level and collectible has been saved.");
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
                break;
            case "d":
                moveRight();
                break;
            case "s":
                moveDown();
                break;
            case "a":
                moveLeft();
                break;
        }
    }

    /*
     * REQUIRES: cube.getX() - 1 >= 0 && frame[cube.getX()-1][cube.getY()] == null
     * EFFECTS: moves the cube up if there is no platform
     */
    public void moveUp() {
        if (cube.getX1() - 1 >= 0 && frame[cube.getX1() - 1][cube.getY1()] == null) {
            frame[cube.getX1() - 1][cube.getY1()] = cube;
            frame[cube.getX1()][cube.getY1()] = null;
            cube.moveLeft();
            draw();
        } else {
            draw(); 
            System.out.println("Invalid action, please try 'a', 's', or 'd'");
        }
    }

    /*
     * REQUIRES: cube.getY() + 1 >= 0 && frame[cube.getX()][cube.getY()+1] == null
     * EFFECTS: moves the cube right if there is no platform
     */
    public void moveRight() {
        if (cube.getY1() + 1 < frame[0].length && frame[cube.getX1()][cube.getY1() + 1] == null) {
            frame[cube.getX1()][cube.getY1() + 1] = cube;
            frame[cube.getX1()][cube.getY1()] = null;
            cube.jump();
            draw();
        } else {
            draw(); 
            System.out.println("Invalid action, please try 'a', 's', or 'w'");
        }
    }

    /*
     * REQUIRES: cube.getX() + 1 >= 0 && frame[cube.getX()+1][cube.getY()] == null
     * EFFECTS: moves the cube down if there is no platform
     */
    public void moveDown() {
        if (cube.getX1() + 1 < frame.length && frame[cube.getX1() + 1][cube.getY1()] == null) {
            frame[cube.getX1() + 1][cube.getY1()] = cube;
            frame[cube.getX1()][cube.getY1()] = null;
            cube.moveRight();
            draw();
        } else {
            draw(); 
            System.out.println("Invalid action, please try 'a', 'w', or 'd'");
        }
    }

    /*
     * REQUIRES: cube.getY() - 1 >= 0 && frame[cube.getX()][cube.getY()-1] == null
     * EFFECTS: moves the cube left if there is no platform
     */
    public void moveLeft() {
        if (cube.getY1() - 1 >= 0 && frame[cube.getX1()][cube.getY1() - 1] == null) {
            frame[cube.getX1()][cube.getY1() - 1] = cube;
            frame[cube.getX1()][cube.getY1()] = null;
            cube.fall();
            draw();
        } else {
            draw(); 
            System.out.println("Invalid action, please try 'w', 's', or 'd'");
        }
    }
}
