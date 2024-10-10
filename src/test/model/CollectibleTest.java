package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CollectibleTest {
    private Collectible collectibleTest;
    private Cube cubeTest;

    @BeforeEach
    void runBefore() {
        collectibleTest = new Collectible(50, 100);
    }

    @Test
    void testConstructor() {
        assertEquals(50, collectibleTest.getX());
        assertEquals(100, collectibleTest.getY());
        assertEquals(1, collectibleTest.getId());
        assertFalse(collectibleTest.getIsCollected());
    }

    @Test 
    void collectTest() {
        assertFalse(collectibleTest.getIsCollected());
        cubeTest = new Cube(50, 100);
        collectibleTest.collect();
        assertTrue(collectibleTest.getIsCollected());
    }
}
