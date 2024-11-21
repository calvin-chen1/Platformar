package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.Collectible;
import model.Cube;
import model.Platform;

public class GridPanel extends JPanel {
    private static LevelFrame gFrame; // uses a LevelFrame to help with creating the actual frame
    private GameFrame gameFrame;
    private JLabel[][] labels;
    private Object[][] f;
    private LegendPanel lPanel;
    private Cube cube;
    private Collectible collectible;

    public GridPanel(GameFrame gameFrame, LegendPanel lPanel) {
        gFrame = new LevelFrame();
        f = gFrame.getFrame();
        labels = new JLabel[8][8];
        this.gameFrame = gameFrame;
        this.lPanel = lPanel;
        setLabels();
        initializeGrid();
    }

    private void setLabels() {
        for (int i = 0; i < f.length; i++) {
            for (int j = 0; j < f[i].length; j++) {
                labels[i][j] = labelObject(f[i][j]);
                if (f[i][j] instanceof Cube) {
                    this.cube = (Cube) f[i][j];
                } else if (f[i][j] instanceof Collectible) {
                    this.collectible = (Collectible) f[i][j];
                }
            }
        }
    }

    private void initializeGrid() {
        setPreferredSize(new Dimension(600, 600));
        // addKeyListener(new KeyHandler());
        setLayout(new GridLayout(f.length, f[0].length));
        for (int i = 0; i < labels.length; i++) {
            for (int j = 0; j < labels[0].length; j++) {
                add(labels[i][j]);
            }
        }
    }

    /*
     * REQUIRES: Object
     * EFFECTS: assigns each type of Object that can exist in a frame a background
     */
    private JLabel labelObject(Object o) {
        JLabel label = new JLabel();
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setPreferredSize(new Dimension(20, 20));
        label.setOpaque(true);
        label.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        if (o instanceof Platform) {
            determinePlatform(label, o);
        } else if (o instanceof Collectible) {
            label.setBackground(Color.YELLOW);
        } else if (o instanceof Cube) {
            label.setBackground(Color.BLUE);
        } else {
            label.setBackground(Color.WHITE);
        }
        return label;
    }

    /*
     * REQUIRES: JLabel, Object
     * EFFECTS: determines whether the platform is lava or not
     */
    private void determinePlatform(JLabel l, Object o) {
        Platform p = (Platform) o;
        if (!p.getIsLava()) {
            l.setBackground(Color.BLACK);
        } else {
            l.setBackground(Color.RED);
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: uses the keyCode recieved from GameFrame to 
     * move the cube through the grid layout
     */
    public void keyPressed(int keyCode) {
        lPanel.redirectConsoleOutput();
        JLabel c = labels[cube.getX1()][cube.getY1()];
        if (keyCode == KeyEvent.VK_W)
            gFrame.moveUp();
        else if (keyCode == KeyEvent.VK_D)
            gFrame.moveRight();
        else if (keyCode == KeyEvent.VK_S)
            gFrame.moveDown();
        else if (keyCode == KeyEvent.VK_A)
            gFrame.moveLeft();
        updateBoard(c);
        lPanel.updateMsg();
        System.out.flush();
        System.setOut(System.out);
    }


    /*
     * REQUIRES: keyPressed, initialLabel
     * EFFECTS: each time a key is pressed, the frame is updated and
     * checks if the collectible has been collected
     */
    private void updateBoard(Component initialLabel) {
        JLabel updatedLabel = labels[cube.getX1()][cube.getY1()];
        initialLabel.setBackground(Color.WHITE);
        updatedLabel.setBackground(Color.BLUE);
        collectible.collect(cube);
        if (collectible.getIsCollected()) {
            win();
        }
        repaint();
    }
    /*
     * EFFECTS: displays a win popup and prompts the user to replay, and sends
     * an event to notify the thread to continue
     */

    private void win() {
        JOptionPane.showMessageDialog(this, "You won!");
        int n = JOptionPane.showConfirmDialog(this,
                "Would you like to replay?", "", JOptionPane.YES_NO_OPTION);

        if (n == JOptionPane.YES_OPTION) {
            Main.restart = true;
        } else {
            JOptionPane.showMessageDialog(this, "Thanks for playing!");
            Main.restart = false;
        }
        gameFrame.dispatchEvent(new WindowEvent(gameFrame, WindowEvent.WINDOW_CLOSED));
    }
}