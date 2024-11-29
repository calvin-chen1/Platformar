package ui;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Collectible;
import model.LevelFrame;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;

// for the menu interface loaded at the start
public class StartPanel extends JPanel implements ActionListener {
    private GameFrame gameFrame; // call methods on GameFrame
    private int input; // stores user input
    private JTextField levelNum; // for user input for the level number
    private JButton start; // calls initialize from GameFrame
    private JButton lvlView; // calls methods to create levels view
    private JButton ctblesView; // calls methods to create collectibles view
    private JButton reset; // remove save files
    private JButton back; // go back to the initial start menu
    private JButton submit; // submit user input

    /*
     * REQUIRES: current gameFrame being run
     * EFFECTS: creates a menu interface for all of the options seen in LevelFrame
     */
    public StartPanel(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
        start = new JButton("Start game");
        lvlView = new JButton("View levels");
        ctblesView = new JButton("View collectibles");
        reset = new JButton("Reset save");
        back = new JButton("Go back");
        submit = new JButton("Submit");
        setButtons();

        setPreferredSize(new Dimension(300, 200));
        setLayout(new FlowLayout(1, 75, 5));

        addButtons();
    }

    /*
     * EFFECTS: adds actionListeners to all buttons and sets dimensions
     */
    private void setButtons() {
        start.addActionListener(this);
        lvlView.addActionListener(this);
        ctblesView.addActionListener(this);
        reset.addActionListener(this);
        back.addActionListener(this);
        submit.addActionListener(this);

        start.setPreferredSize(new Dimension(200, 30));
        lvlView.setPreferredSize(new Dimension(200, 30));
        ctblesView.setPreferredSize(new Dimension(200, 30));
        reset.setPreferredSize(new Dimension(200, 30));
        back.setPreferredSize(new Dimension(100, 30));
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds button to this
     */
    private void addButtons() {
        setBorder(BorderFactory.createTitledBorder("Welcome to Platfomar! Select any option below."));
        add(start);
        add(lvlView);
        add(ctblesView);
        add(reset);
        setVisible(true);
    }

    /*
     * REQUIRES: ActionEvent to occur
     * EFFECTS: determine which button has been clicked and call corresponding methods
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == start) {
            gameFrame.setVisible(false);
            gameFrame.remove(this);
            gameFrame.initialize();
        } else if (e.getSource() == lvlView) {
            levelView();
        } else if (e.getSource() == ctblesView) {
            collectiblesView();
        } else if (e.getSource() == reset) {
            LevelFrame.resetSave();
            JOptionPane.showMessageDialog(this, "Reset all saves.");
        } else if (e.getSource() == submit) {
            sendInput();
        } else {
            setVisible(false);
            removeAll();
            addButtons();
        }
    }

    /*
     * EFFECTS: shows a level view with a back button
     */
    private void levelView() {
        setVisible(false);
        removeAll();
        add(back, BorderLayout.WEST);
        Container container = new Container();
        container.setLayout(new GridLayout(1, 4));
        ArrayList<Object[][]> levels = LevelFrame.getLevels();
        if (levels.isEmpty()) {
            setBorder(BorderFactory.createTitledBorder("The levels list is currently empty."));
            setVisible(true);
        } else {
            setBorder(BorderFactory.createTitledBorder("These are your saved levels."));
            for (int i = 0; i < levels.size(); i++) {
                JLabel level = new JLabel("Level " + (i + 1));
                level.setPreferredSize(new Dimension(50, 15));
                container.add(level);
            }
            add(container);
            getInput();
        }
    }

    /*
     * EFFECTS: creates a JTextField for user input
     */
    private void getInput() {
        levelNum = new JTextField(16);
        add(levelNum, BorderLayout.SOUTH);
        add(submit);
        setVisible(true);
    }

    /*
     * EFFECTS: determines whether the user input can be used
     */
    private void sendInput() {
        ArrayList<Object[][]> levels = LevelFrame.getLevels();
        ArrayList<Collectible> collectibles = LevelFrame.getClist();
        boolean valid = false;
        try {
            input = Integer.parseInt(levelNum.getText());
            if (input >= 1 || input <= levels.size()) {
                valid = true;
            }
        } catch (NumberFormatException e) {
            valid = false;
        } finally {
            if (!valid) {
                JOptionPane.showMessageDialog(this, "Invalid input, try a number from the list");
            }
        }
        if (valid) {
            Collectible c = collectibles.get(input - 1);
            gameFrame.updateGrid(
                    new LevelFrame(levels.get(input - 1), new Collectible(c.getX1(), c.getY1()), c.getX1()));
        }
    }

    /*
     * EFFECTS: displays collectibles with their id's in the same format as levelsView
     */
    private void collectiblesView() {
        setVisible(false);
        removeAll();
        add(back, BorderLayout.WEST);
        Container container = new Container();
        container.setLayout(new GridLayout(1, 4));
        ArrayList<Collectible> collectibles = LevelFrame.getClist();
        for (int i = 0; i < collectibles.size(); i++) {
            JLabel collectible = new JLabel("Collectible #" + (collectibles.get(i).getId()));
            collectible.setPreferredSize(new Dimension(80, 15));
            container.add(collectible);
        }
        add(container);
        setVisible(true);
    }
}
