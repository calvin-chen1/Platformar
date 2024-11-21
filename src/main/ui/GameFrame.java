package ui;

import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

// constructs a graphical version of the LevelFrame
public class GameFrame extends JFrame {
    private GridPanel gPanel;
    private LegendPanel lPanel;

    /*
     * REQUIRES: LevelFrame
     * EFFECTS: constructs gameFrame and borrows elements from LevelFrame
     */
    public GameFrame() {
        super("Platformar");
        lPanel = new LegendPanel();
        gPanel = new GridPanel(this, lPanel);
    }


    /*
     * REQUIRES: frame must exist
     * EFFECTS: intializes the graphical window display for the frame
     */
    public void initialize() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addKeyListener(new KeyHandler());
        setResizable(false);
        add(gPanel);
        add(lPanel, BorderLayout.NORTH);
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
            gPanel.keyPressed(e.getKeyCode());
        }
    }
}
