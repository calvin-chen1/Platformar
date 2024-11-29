package ui;

import java.util.Arrays;
import java.util.Scanner;

import model.Collectible;
import model.Cube;
import model.LevelFrame;
import model.Platform;

public class ConsoleFrame {
    private LevelFrame lf;
    private Object[][] frame;

    private static Scanner in = Main.in;

    public ConsoleFrame() {
        lf = new LevelFrame();
        frame = lf.getFrame();
    }

    public ConsoleFrame(LevelFrame lf) {
        this.lf = lf;
        frame = lf.getFrame();
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
        while (!lf.checkCollectible()) {
            String input = in.next();
            move(input);
        }
        lf.resetPosition();
        savePrompt(in);
        replayPrompt(in);
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
                } else if (current instanceof Cube) {
                    System.out.print("{:} ");
                } else {
                    System.out.print("(~) ");
                }
            }
            System.out.println();
        }
    }

    /*
     * EFFECTS: clears the console screen
     */
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /*
     * REQUIRES: cList
     * EFFECTS: prints out all of the collectibles along with their respective IDs
     */
    public void viewCollectibles() {
        System.out.println("Welcome to your collectible collection! Take a look.");
        for (Collectible c : LevelFrame.getClist()) {
            System.out.println("Collectible #" + c.getId());
        }
    }

    private void savePrompt(Scanner in) {
        String input = null;
        if (!containsLevelInSave()) {
            System.out.println("Congratulations on finishing! Would you like to save your data? (y/n)");
            do {
                input = in.next().toLowerCase();
                if (input.equals("y")) {
                    lf.saveLevel();
                    break;
                }
            } while (!input.equals("n"));
        }
    }

    private boolean containsLevelInSave() {
        for (Object[][] f : LevelFrame.getLevels()) {
            if (Arrays.deepEquals(f, lf.getFrame())) {
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
                lf.moveUp();
                draw();
                break;
            case "d":
                lf.moveRight();
                draw();
                break;
            case "s":
                lf.moveDown();
                draw();
                break;
            case "a":
                lf.moveLeft();
                draw();
                break;
            case "/q":
                Main.savePreviousLevel(lf.getFrame());
                System.exit(0);
                break;
        }
    }

}
