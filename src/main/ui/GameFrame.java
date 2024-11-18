package ui;

import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import model.Collectible;
import model.Cube;
import model.Platform;

// constructs a graphical version of the LevelFrame
public class GameFrame {
    private static LevelFrame gameFrame; // uses a LevelFrame to help with creating the actual frame
    private Object[][] f;
    private JLabel[][] labels;
    private JFrame gFrame;
    private Cube cube;
    private Collectible collectible;

    /*
     * REQUIRES: LevelFrame
     * EFFECTS: constructs gameFrame and borrows elements from LevelFrame
     */
    public GameFrame() {
        gameFrame = new LevelFrame();
        f = gameFrame.getFrame();
        gFrame = new JFrame("Platfomar");
        labels = new JLabel[8][8];
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

    public JFrame getFrame() {
        return this.gFrame;
    }

    /*
     * REQUIRES: frame must exist
     * EFFECTS: intializes the graphical window display for the frame
     */
    public void initialize() {
        gFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        gFrame.setPreferredSize(new Dimension(800, 800));
        gFrame.setLayout(new GridLayout(f.length, f[0].length));
        gFrame.addKeyListener(new KeyHandler());
        gFrame.setResizable(false);
        for (int i = 0; i < labels.length; i++) {
            for (int j = 0; j < labels[0].length; j++) {
                gFrame.add(labels[i][j]);
            }
        }
        gFrame.pack();
        gFrame.setLocationRelativeTo(null);
        gFrame.setVisible(true);
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
     * A key handler to respond to key events
     */
    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
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
        }
    }

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

    private void win() {
        JOptionPane.showMessageDialog(gFrame, "You won!");
        int n = JOptionPane.showConfirmDialog(gFrame,
                "Would you like to replay?", "", JOptionPane.YES_NO_OPTION);

        if (n == JOptionPane.YES_OPTION) {
            Main.restart = true;
        } else {
            JOptionPane.showMessageDialog(gFrame, "Thanks for playing!");
            Main.restart = false;
        }
        gFrame.setVisible(false);
    }
}
