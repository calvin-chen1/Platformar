package model;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
public class LevelTest {
    private LevelFrame testFrame;

    @BeforeEach
    void runBefore() {
        testFrame = new LevelFrame();
    }

    @Test
    void testConstructor() {
        assertTrue(testFrame.getFrame()[1][2] instanceof Cube);
        assertEquals(8, testFrame.getFrame().length);
        assertEquals(8, testFrame.getFrame()[0].length);
        testFrame = new LevelFrame(testFrame.getFrame(), new Collectible(3, 3), 3);
        assertTrue(testFrame.getFrame()[3][3] instanceof Collectible);
        testFrame = new LevelFrame(testFrame.getFrame());
        assertTrue(testFrame.getFrame()[3][3] instanceof Collectible);
        assertEquals(0, LevelFrame.getClist().size());
        assertEquals(0, LevelFrame.getLevels().size());
    }

    @Test
    void testMove() {
        testFrame = new LevelFrame(testFrame.getFrame(), new Collectible(3, 3), 3);
        for (int i = 0; i < testFrame.getFrame().length; i++) {
            for (int j = 0; j < testFrame.getFrame()[0].length; j++) {
                if (testFrame.getFrame()[i][j] instanceof Platform) {
                    testFrame.getFrame()[i][j] = null;
                }
            }
        }
        assertFalse(testFrame.checkCollectible());
        testFrame.moveDown();
        assertFalse(testFrame.getFrame()[1][2] instanceof Cube);
        assertTrue(testFrame.getFrame()[2][2] instanceof Cube);
        testFrame.moveUp();
        assertTrue(testFrame.getFrame()[1][2] instanceof Cube);
        testFrame.moveLeft();
        assertFalse(testFrame.getFrame()[1][2] instanceof Cube);
        assertTrue(testFrame.getFrame()[1][1] instanceof Cube);
        testFrame.moveRight();
        assertTrue(testFrame.getFrame()[1][2] instanceof Cube);
        testFrame.moveRight();
        testFrame.moveDown();
        testFrame.moveDown();
        assertTrue(testFrame.getFrame()[3][3] instanceof Cube);
        assertTrue(testFrame.checkCollectible());
        
        testFrame.resetPosition();
        assertTrue(testFrame.getFrame()[1][2] instanceof Cube);
        assertTrue(testFrame.getFrame()[3][3] instanceof Collectible);
        
        for (int i = 0; i < 8; i++) {
            testFrame.moveDown();
        }
        assertTrue(testFrame.getFrame()[7][2] instanceof Cube);
        for (int i = 0; i < 9; i++) {
            testFrame.moveUp();
        }
        assertTrue(testFrame.getFrame()[0][2] instanceof Cube);
        for (int i = 0; i < 2; i++) {
            testFrame.moveLeft();
        }
        assertTrue(testFrame.getFrame()[0][0] instanceof Cube);
        for (int i = 0; i < 9; i++) {
            testFrame.moveRight();
        }
        assertTrue(testFrame.getFrame()[0][7] instanceof Cube);
    }

    @Test
    void testSave() {
        testFrame.saveLevel();
        assertEquals(1, LevelFrame.getLevels().size());
        assertEquals(1, LevelFrame.getLevels().size());
        LevelFrame.resetSave();
        assertEquals(0, LevelFrame.getLevels().size());
        assertEquals(0, LevelFrame.getLevels().size());
    }
}
