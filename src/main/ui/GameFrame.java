package ui;

import java.awt.*;
import javax.swing.*;

import model.Collectible;
import model.Cube;
import model.Platform;

// constructs a graphical version of the LevelFrame
public class GameFrame {
    private static LevelFrame gameFrame; // uses a LevelFrame to help with creating the actual frame

    /*
     * REQUIRES: LevelFrame
     * EFFECTS: constructs gameFrame
     */
    public GameFrame() {
        gameFrame = new LevelFrame();
    }

    /*
     * REQUIRES: frame must exist
     * EFFECTS: intializes the graphical window display for the frame
     */
    public void initialize() {
        JFrame gFrame = new JFrame("Platfomar");
        Object[][] f = gameFrame.getFrame();
        gFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gFrame.setPreferredSize(new Dimension(800, 800));
        gFrame.setLayout(new GridLayout(f.length, f[0].length));
        gFrame.setLocationRelativeTo(null);

        for (int i = 0; i < f.length; i++) {
            for (int j = 0; j < f[0].length; j++) {
                JLabel label = labelObject(f[i][j]);
                gFrame.add(label);
            }
        }
        gFrame.pack();
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
}
