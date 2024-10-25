package ui;

import java.util.Scanner;

import persistence.JsonReader;
import persistence.JsonWriter;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    private static LevelFrame game = new LevelFrame();

    /*
     * REQUIRES: 2D Object array (frame)
     * MODIFIES: this
     * EFFECTS: adds a 2D frame to levels
     */
    public static void saveLevel(Object[][] f) {
        String destination = "./data/levelframe.json";
        JsonWriter jsonWriter = new JsonWriter(destination);
        try {
            jsonWriter.open();
            jsonWriter.write(f);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + destination);
        }
    }

    /* MODIFIES: this
     * EFFECTS: loads LevelFrame from file
     */
    private static void loadLevel() {
        String source = "./data/levelframe.json";
        JsonReader jsonReader = new JsonReader(source);
        try {
            game = jsonReader.read();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + source);
        }
    }

    /*
     * REQUIRES: levels
     * EFFECTS: prints out the levels in the levels ArrayList and allows you to
     * select
     * one by its corresponding number in the list
     */
    public static void levelsMenu() {
        ArrayList<Object[][]> levels = LevelFrame.getLevels();
        Scanner in = new Scanner(System.in);
        System.out.println("Welcome to the levels menu. Here are the current levels:");
        for (int i = 0; i < levels.size(); i++) {
            System.out.print("[" + (i + 1) + "] ");
        }
        int input = 0;
        System.out.println();
        do {
            try {
                System.out.println("Select which one you would like to try.");
                input = in.nextInt();
            } catch (InputMismatchException e) {
                in.nextLine();
            }
            System.out.println("Invalid input. Please choose a number that is listed.");
        } while (input < 1 || input > levels.size());
        LevelFrame game = new LevelFrame(levels.get(input - 1));
        game.start();
        in.close();
    }

    /*
     * REQUIRES: LevelFrame
     * EFFECTS: prints out the menu for all of the options in platformer
     */
    public static void menu() {
        JsonReader jsonReader = new JsonReader("./data/autosave.json");
        Scanner in = new Scanner(System.in);
        System.out.println("-------------------------------------------------------");
        System.out.println("Welcome to Platformar! Choose your options (Enter 1-5):");
        System.out.println("\t[1] Start a level");
        System.out.println("\t[2] View levels");
        System.out.println("\t[3] View collectibles");
        System.out.println("\t[4] Load previous level");
        System.out.println("\t[5] Reset your save");
        System.out.println("-------------------------------------------------------");
        try {
            jsonReader.autoRead();
        } catch (IOException e) {
            System.err.println("Unable to load autosave.");
        }
        menuScanner(in);
    }

     /*
     * REQUIRES: input from user
     * EFFECTS: takes in input for the menu; invalid inputs if beyond 5 or less than 1
     */
    private static void menuScanner(Scanner in) {
        int i = 0;
        do {
            try {
                i = in.nextInt();
                menuInput(i);
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please try again (1-5).");
                in.nextLine();
            }
        } while (i < 1 || i > 5);
    }

    /*
     * REQUIRES: autosave.json and levelframe.json
     * EFFECTS: deletes the files for autosave.json and levelframe.json
     */
    private static void resetSave() {
        String fileName1 = "./data/autosave.json";
        String fileName2 = "./data/levelframe.json";
        try {
            Files.delete(Paths.get(fileName1));
            Files.delete(Paths.get(fileName2));
            System.out.println("Sucessfully deleted saves.");
        } catch (IOException e) {
            System.out.println("Save files not found.");
        }
    }

    /*
     * REQUIRES: input from user
     * EFFECTS: takes in input for the menu; invalid inputs if beyond 5 or less than 1
     */
    private static void menuInput(int i) {
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
                loadLevel();
                System.out.println("Loaded level.");
                menu();
                break;
            case 5:
                resetSave();
                break;
            default:
                System.out.println("Invalid input. Please try again (1-5).");
        }
    }

    public static void main(String[] args) throws Exception {
        menu();
    }
}
