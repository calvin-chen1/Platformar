package ui;

import java.awt.BorderLayout;
import javax.swing.JFrame;

import model.LevelFrame;
import persistence.JsonReader;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

// constructs a graphical version of the LevelFrame
public class GameFrame extends JFrame {
    private GridPanel gpanel;
    private LegendPanel lpanel;
    private StartPanel spanel;

    /*
     * REQUIRES: LevelFrame
     * EFFECTS: constructs gameFrame and borrows elements from LevelFrame
     */
    public GameFrame() {
        super("Platformar");
        loadSave();
        spanel = new StartPanel(this);
        lpanel = new LegendPanel();
        gpanel = new GridPanel(this, lpanel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        add(spanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    protected void updateGrid(LevelFrame frame) {
        gpanel = new GridPanel(this, lpanel, frame);
        setVisible(false);
        remove(spanel);
        initialize();
    }

    /*
     * EFFECTS: intializes the graphical window display for the frame
     */
    protected void initialize() {
        addKeyListener(new KeyHandler());
        setResizable(false);
        add(gpanel);
        add(lpanel, BorderLayout.NORTH);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /*
     * EFFECTS: creates a key handler to respond to key events
     */
    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            gpanel.keyPressed(e.getKeyCode());
        }
    }

    /*
     * REQUIRES: autosave.json
     * EFFECTS: reads from autosave and loads it
     */
    private void loadSave() {
        // setVisible(true);
        // setLocationRelativeTo(null);
        // int n = JOptionPane.showConfirmDialog(this, "Would you like to load your
        // saved data?", "Load",
        // JOptionPane.YES_NO_OPTION);
        // pack();

        // if (n == JOptionPane.YES_OPTION) {
        JsonReader jsonReader = new JsonReader("./data/autosave.json");
        try {
            if (!Main.restart) {
                jsonReader.autoRead();
            }
        } catch (IOException e) {
            e.getMessage();
        }
        // }
        setVisible(false);
    }
}
