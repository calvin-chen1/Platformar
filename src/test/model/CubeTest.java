package model;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CubeTest {
    private Cube testCube;
    private Platform testPlatform;

    @BeforeEach
    void runBefore() {
        testCube = new Cube(500, 200);
    }

    @Test
    void constructorTest() {
        assertEquals(testCube.getX1(), 500);
        assertEquals(testCube.getY1(), 200);
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
        int y1 = testCube.getY1();
        testCube.jump();
        int y2 = testCube.getY1();
        assertTrue(y1 < y2);
        assertFalse(y2 < y1);
        while (testCube.getSpeedY() >= 0) {
            testCube.jump();
        }
        assertEquals(testCube.getSpeedY(), -1.0);
    }

    @Test
    void testCollision() {
        testPlatform = new Platform(testCube.getX1() + 50, testCube.getY1(), 50, 50, false);
        testCube.moveRight();
        assertTrue(testCube.detectCollision(testPlatform));
        assertEquals(testCube.getSpeedX(), 0);
        testPlatform = new Platform(testCube.getX1() - 50, testCube.getY1(), 50, 50, false);
        testCube.moveLeft();
        assertTrue(testCube.detectCollision(testPlatform));
        assertEquals(testCube.getSpeedX(), 0);

        testPlatform = new Platform(testCube.getX1(), testCube.getY1() + 100, 50, 50, false);
        testCube.jump();
        assertTrue(testCube.detectCollision(testPlatform));
        assertEquals(0, testCube.getSpeedY());
        testPlatform = new Platform(testCube.getX1(), testCube.getY1() - 100, 50, 50, false);
        assertEquals(0, testCube.getSpeedY());

        testPlatform = new Platform(testCube.getX1(), 0, 50, 50, false);
        assertFalse(testCube.detectCollision(testPlatform));
    }
}
