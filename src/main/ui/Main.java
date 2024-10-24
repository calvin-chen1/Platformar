package ui;

import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    private static ArrayList<Object[][]> levels = new ArrayList<>();
    
    /* REQUIRES: 2D Object array (frame)
     * MODIFIES: this
     * EFFECTS: adds a 2D frame to levels
     */
    public static void saveLevel(Object[][] f) {
        levels.add(f);
    }

    /* REQUIRES: levels 
     * EFFECTS: prints out the levels in the levels ArrayList and allows you to select
     *          one by its corresponding number in the list
     */
    public static void levelsMenu() {
        Scanner in = new Scanner(System.in);
        System.out.println("Welcome to the levels menu. Here are the current levels:");
        for (int i = 0; i < levels.size(); i++) {
            System.out.print("[" + (i + 1) + "] ");
        }
        System.out.println();
        System.out.println("Select which one you would like to try.");
        int input = in.nextInt();
        while (input < 1 || input > levels.size()) {
            System.out.println("Invalid input. Please choose a number that is listed.");
            input = in.nextInt();
        }
        LevelFrame game = new LevelFrame(levels.get(input - 1));
        game.start();
        in.close();
    }

    /* REQUIRES: LevelFrame
     * EFFECTS: prints out the menu for all of the options in platformer
     */
    public static void menu() {
        Scanner in = new Scanner(System.in);
        System.out.println("-------------------------------------------------------");
        System.out.println("Welcome to Platformar! Choose your options (Enter 1-4):");
        System.out.println("    [1] Start a level");
        System.out.println("    [2] View levels");
        System.out.println("    [3] View collectibles");
        System.out.println("    [4] Add a random level");
        System.out.println("-------------------------------------------------------");
        
        menuScanner(in);
    }
    
    private static void menuScanner(Scanner in) {
        LevelFrame game = new LevelFrame();
        int i = in.nextInt();
        do {
            switch (i) {
                case 1:
                    game.start();
                    break;
                case 2:
                    levelsMenu();
                    break;
                case 3:
                    game.viewCollectibles();
                    break;
                case 4:
                    levels.add(new LevelFrame().getFrame());
                    System.out.println("Added a randomized level to the levels list.");
                    LevelFrame.clearScreen();
                    menu();
                    break;
                default:
                    System.out.println("Invalid input. Please try again (1-4).");
                    i = in.nextInt();
            }
        } while (i < 1 || i > 4);
    }

    public static void main(String[] args) throws Exception {
        menu();
    }
}
