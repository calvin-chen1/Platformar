package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CollectibleTest {
    private Collectible collectibleTest;

    @BeforeEach
    void runBefore() {
        collectibleTest = new Collectible(50, 100);
    }

    @Test
    void testConstructor() {
        assertEquals(50, collectibleTest.getX1());
        assertEquals(100, collectibleTest.getY1());
        assertEquals(1, collectibleTest.getId());
        assertFalse(collectibleTest.getIsCollected());
    }

    @Test 
    void collectTest() {
        collectibleTest.collect(new Cube(10, 90));
        assertFalse(collectibleTest.getIsCollected());
        collectibleTest.collect(new Cube(50, 90));
        assertFalse(collectibleTest.getIsCollected());
        collectibleTest.collect(new Cube(50, 100));
        assertTrue(collectibleTest.getIsCollected());
    }
}
