package ui;

import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import model.Collectible;
import model.Cube;
import model.Platform;

// constructs a graphical version of the LevelFrame
public class GameFrame extends JFrame {
    private static LevelFrame gameFrame; // uses a LevelFrame to help with creating the actual frame
    private Object[][] f;
    private JLabel[][] labels;
    private JPanel gPanel;
    private LegendPanel lPanel;
    private Cube cube;
    private Collectible collectible;

    /*
     * REQUIRES: LevelFrame
     * EFFECTS: constructs gameFrame and borrows elements from LevelFrame
     */
    public GameFrame() {
        super("Platformar");
        gPanel = new JPanel();
        lPanel = new LegendPanel();
        gameFrame = new LevelFrame();
        f = gameFrame.getFrame();
        labels = new JLabel[8][8];
        setLabels();
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

    /*
     * REQUIRES: frame must exist
     * EFFECTS: intializes the graphical window display for the frame
     */
    public void initialize() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gPanel.setPreferredSize(new Dimension(600, 600));
        addKeyListener(new KeyHandler());
        setResizable(false);
        gPanel.setLayout(new GridLayout(f.length, f[0].length));
        for (int i = 0; i < labels.length; i++) {
            for (int j = 0; j < labels[0].length; j++) {
                gPanel.add(labels[i][j]);
            }
        }
        add(gPanel);
        add(lPanel, BorderLayout.NORTH);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
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
     * EFFECTS: creates a key handler to respond to key events
     */
    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            lPanel.redirectConsoleOutput();
            int keyCode = e.getKeyCode();
            JLabel c = labels[cube.getX1()][cube.getY1()];
            if (keyCode == KeyEvent.VK_W)
                gameFrame.moveUp();
            else if (keyCode == KeyEvent.VK_D)
                gameFrame.moveRight();
            else if (keyCode == KeyEvent.VK_S)
                gameFrame.moveDown();
            else if (keyCode == KeyEvent.VK_A)
                gameFrame.moveLeft();
            updateBoard(c);
            lPanel.updateMsg();
            System.out.flush();
            System.setOut(System.out);
        }
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
        initialLabel.revalidate();
        initialLabel.repaint();
        updatedLabel.revalidate();
        updatedLabel.repaint();
    }

    /*
     * EFFECTS: displays a win popup and prompts the user to replay, and sends 
     * an event to notify the thread to continue
     */
    private void win() {
        JOptionPane.showMessageDialog(gPanel, "You won!");
        int n = JOptionPane.showConfirmDialog(gPanel,
                "Would you like to replay?", "", JOptionPane.YES_NO_OPTION);

        if (n == JOptionPane.YES_OPTION) {
            Main.restart = true;
        } else {
            JOptionPane.showMessageDialog(gPanel, "Thanks for playing!");
            Main.restart = false;
        }
        dispatchEvent(new WindowEvent(getWindows()[0], WindowEvent.WINDOW_CLOSED));
    }
}
