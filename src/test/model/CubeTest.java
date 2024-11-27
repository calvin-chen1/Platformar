package model;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CubeTest {
    private Cube testCube;

    @BeforeEach
    void runBefore() {
        testCube = new Cube(1,2);
    }

    @Test
    void constructorTest() {
        assertEquals(testCube.getX1(), 1);
        assertEquals(testCube.getY1(), 2);
        assertEquals(testCube.getSpeedX(), 1.0);
        assertEquals(testCube.getSpeedY(), 1.0);
    }

    @Test 
    void moveTest() {
        int x1 = testCube.getX1();
        testCube.moveRight();
        int x2 = testCube.getX1();
        assertTrue(x1 < x2);
        x1 = x2;
        testCube.moveLeft();
        x2 = testCube.getX1();
        assertTrue(x1 > x2);
        assertFalse(x2 > x1);
    }

    @Test
    void jumpTest() {
        testCube.jump();
        assertEquals(3, testCube.getY1());
        assertEquals(1.0, testCube.getSpeedY());
        testCube.fall();
        assertEquals(2, testCube.getY1());
        assertEquals(-1.0, testCube.getSpeedY());
    }
}
