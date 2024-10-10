package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertEquals(250, testPlatform.getX());
        assertEquals(300, testPlatform.getY());
        assertEquals(100, testPlatform.getWidth());
        assertEquals(50, testPlatform.getHeight());
        assertEquals(true, testPlatform.getIsLava());
    }

    @Test
    void borderTest() {
        assertEquals(testPlatform.getX() + testPlatform.getWidth(), testPlatform.borderRight());
        assertEquals(testPlatform.getX() - testPlatform.getWidth(), testPlatform.borderLeft());
        assertEquals(testPlatform.getY() + testPlatform.getHeight(), testPlatform.borderTop());
        assertEquals(testPlatform.getY() - testPlatform.getHeight(), testPlatform.borderBottom());
    }
}
