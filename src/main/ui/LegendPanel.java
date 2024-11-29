package ui;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

// constructs the panel displayed on the top of GameFrame
public class LegendPanel extends JPanel {
    private static final String GUIDE_TXT = "Blue is you. Red is lava. Yellow is the goal."; // guide text
    private JLabel msgLbl; // represents the message displayed
    private ByteArrayOutputStream baos; // the redirected connection from console output
    private PrintStream stdOut; // the old output saved

    /*
     * EFFECTS: creates a JPanel that represents the legend and the corresponding messages 
     * delivered from LevelFrame
     */
    public LegendPanel() {
        setBackground(new Color(211, 211, 211));
        msgLbl = new JLabel(GUIDE_TXT);
        msgLbl.setPreferredSize(new Dimension(250, 30));
        add(msgLbl);
        add(Box.createHorizontalStrut(5));
        stdOut = System.out;
    }

    /*
     * EFFECTS: redirects the stream from the console output to a new 
     * ByteArrayOutputStream, and can take anything printed towards the
     * console and convert it to a string
     */
    public void redirectConsoleOutput() {
        baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        System.setOut(ps);
    }

    /* 
     * EFFECTS: updates msgLbl with the converted information from baos
     */
    public void updateMsg() {
        msgLbl.setText(baos.toString().substring(7));
        repaint();
    }

    public void revertOutput() {
        System.setOut(stdOut);
    }
}
