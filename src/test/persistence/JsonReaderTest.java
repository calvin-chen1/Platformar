package persistence;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

import model.Cube;
import model.LevelFrame;

import java.io.IOException;

public class JsonReaderTest {
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testAutoRead() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralCase.json");
        LevelFrame lf = new LevelFrame();
        try {
            reader.autoRead();
            assertTrue(lf.getFrame()[1][2] instanceof Cube);
            assertEquals(1, LevelFrame.getClist().size());
        } catch (IOException e) {
            fail();
        }
    }
}
