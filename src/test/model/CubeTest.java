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
        assertEquals(testCube.getX(), 500);
        assertEquals(testCube.getY(), 200);
    }

    @Test 
    void moveTest() {
        int x1 = testCube.getX();
        testCube.moveRight();
        int x2 = testCube.getX();
        assertTrue(x1 < x2);
        x1 = x2;
        testCube.moveLeft();
        x2 = testCube.getX();
        assertTrue(x1 > x2);
        assertFalse(x2 > x1);
    }

    @Test
    void jumpTest() {
        int y1 = testCube.getY();
        testCube.jump();
        int y2 = testCube.getY();
        assertTrue(y1 < y2);
        assertFalse(y2 < y1);
    }

    @Test
    void testCollision() {
        testPlatform = new Platform(testCube.getX()+(50/2), testCube.getY(), 50, 50, false);
        testCube.moveRight();
        assertEquals(0, testCube.getSpeedX());
        testPlatform = new Platform(testCube.getX()-(50/2), testCube.getY(), 50, 50, false);
        testCube.moveLeft();
        assertEquals(0, testCube.getSpeedX());
        testPlatform = new Platform(testCube.getX(), testCube.getY()+(50/2), 50, 50, false);
        testCube.jump();
        assertEquals(0, testCube.getSpeedY());
        testPlatform = new Platform(testCube.getX(), testCube.getY()-(50/2), 50, 50, false);
        assertEquals(0, testCube.getSpeedY());
    }
}
