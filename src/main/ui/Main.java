package ui;



import javax.swing.JFrame;

import model.Collectible;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    protected static Scanner in;

    private static LevelFrame game;
    private static GameFrame dGame;
    protected static boolean restart;

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
            System.out.println("Unable to save");
        }
    }

    /*
     * REQUIRES: autosave.json and levelframe.json
     * EFFECTS: deletes the files for autosave.json and levelframe.json
     */
    private static void resetSave() {
        String fileName1 = "./data/autosave.json";
        String fileName2 = "./data/levelframe.json";
        ArrayList<Object[][]> f = LevelFrame.getLevels();
        ArrayList<Collectible> l = LevelFrame.getClist();
        f.clear();
        l.clear();
        try {
            Files.delete(Paths.get(fileName1));
            System.out.println("Successfully deleted the levels save.");
        } catch (IOException e) {
            System.out.println("Save not found.");
        }
        try {
            Files.delete(Paths.get(fileName2));
            System.out.println("Successfully deleted the previous level save.");
        } catch (IOException e) {
            System.out.println("No previous level found.");
        }
        menu();
    }

    /*
     * MODIFIES: this
     * EFFECTS: loads LevelFrame from file
     */
    private static void loadPreviousLevel() {
        String source = "./data/levelframe.json";
        JsonReader jsonReader = new JsonReader(source);
        try {
            game = jsonReader.read();
            System.out.println("Previous level loaded.");
        } catch (IOException e) {
            System.out.println("Level does not exist.");
        }
        menu();
    }

    /*
     * REQUIRES: levels
     * EFFECTS: prints out the levels in the levels ArrayList and allows you to
     * select
     * one by its corresponding number in the list
     */
    public static void levelsMenu() {
        ArrayList<Object[][]> levels = LevelFrame.getLevels();
        ArrayList<Collectible> collectibles = LevelFrame.getClist();
        System.out.println("Welcome to the levels menu. Here are the current levels:");
        int input = 0;
        do {
            for (int i = 0; i < levels.size(); i++) {
                System.out.print("[" + (i + 1) + "] ");
            }
            System.out.println();
            try {
                System.out.println("Select which one you would like to try.");
                input = in.nextInt();
            } catch (InputMismatchException e) {
                in.nextLine();
                System.out.println("Invalid input. Please choose a number that is listed.");
            }
        } while (input < 1 || input > levels.size());
        Collectible c = collectibles.get(input - 1);
        LevelFrame game = new LevelFrame(levels.get(input - 1), c, c.getX1());
        game.start();
    }

    /*
     * REQUIRES: LevelFrame
     * EFFECTS: prints out the menu for all of the options in platformer
     */
    public static void menu() {
        JsonReader jsonReader = new JsonReader("./data/autosave.json");
        System.out.println("-------------------------------------------------------");
        System.out.println("Welcome to Platformar! Choose your options (Enter 1-6):");
        System.out.println("\t[1] Start a level");
        System.out.println("\t[2] View levels");
        System.out.println("\t[3] View collectibles");
        System.out.println("\t[4] Load previous level");
        System.out.println("\t[5] Reset your save");
        System.out.println("\t[6] Quit");
        System.out.println("-------------------------------------------------------");
        try {
            jsonReader.autoRead();
        } catch (IOException e) {
            System.out.print("");
        }
        menuScanner();
    }

    /*
     * REQUIRES: input from user
     * EFFECTS: takes in input for the menu; invalid inputs if beyond 5 or less than
     * 1
     */
    private static void menuScanner() {
        int i = 0;
        do {
            try {
                i = in.nextInt();
                menuInput(i);
            } catch (InputMismatchException e) {
                in.nextLine();
            }
        } while (i < 1 || i > 5);
    }

    /*
     * REQUIRES: input from user
     * EFFECTS: takes in input for the menu; invalid inputs if beyond 5 or less than
     * 1
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
                LevelFrame.clearScreen();
                loadPreviousLevel();
                break;
            case 5:
                LevelFrame.clearScreen();
                resetSave();
                break;
            case 6:
                System.out.println("Thanks for playing!");
                System.exit(0);
                break;
        }
    }

    public static void main(String[] args) throws Exception {
        in = new Scanner(System.in);
        System.out.println("Graphical or console display? (1 or 2)");
        int input = 0;
        input = in.nextInt();
        if (input == 1) {
            do {
                dGame = new GameFrame();
                dGame.initialize();
                waitForFrame();
            } while (restart);
        } else if (input == 2) {
            consoleRun();
        }
        in.close();
    }

    private static void consoleRun() {
        do {
            game = new LevelFrame();
            menu();
        } while (restart);
    }

    private static void waitForFrame() {
        JFrame frame = dGame.getFrame();
        Thread t = new Thread() {
            public void run() {
                synchronized (dGame) {
                    while (frame.isVisible()) {
                        try {
                            dGame.wait();
                        } catch (InterruptedException e) {
                            System.err.println("Unexcepted interruption");
                        }
                    }
                }
            }
        };
        t.start();
        addWindowListener(t, frame);
    }

    private static void addWindowListener(Thread t, JFrame frame) {
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent arg0) {
                synchronized (dGame) {
                    frame.setVisible(false);
                    frame.dispose();
                    dGame.notify();
                }
            }
        });
        try {
            t.join();
        } catch (InterruptedException e) {
            System.err.println("Unexcepted interruption");
        }
    }
}
