package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlatformTest {
    private Platform testPlatform;

    @BeforeEach
    void runBefore() {
        testPlatform = new Platform(250, 300, 100, 50, true);
    }

    @Test
    void testConstructor() {
        assertEquals(250, testPlatform.getX1());
        assertEquals(300, testPlatform.getY1());
        assertEquals(100, testPlatform.getWidth());
        assertEquals(50, testPlatform.getHeight());
        assertTrue(testPlatform.getIsLava());
    }

    @Test
    void borderTest() {
        assertEquals(testPlatform.getX1() + testPlatform.getWidth(), testPlatform.borderRight());
        assertEquals(testPlatform.getX1() - testPlatform.getWidth(), testPlatform.borderLeft());
        assertEquals(testPlatform.getY1() + testPlatform.getHeight(), testPlatform.borderTop());
        assertEquals(testPlatform.getY1() - testPlatform.getHeight(), testPlatform.borderBottom());
    }
}
