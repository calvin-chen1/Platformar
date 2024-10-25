package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

import ui.LevelFrame;

import java.io.IOException;

public class JsonWriterTest {
    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }
    
    @Test
    void testEmptyFile() {
        try {
            LevelFrame lf = new LevelFrame();
            JsonWriter writer = new JsonWriter("./data/testWriterNoSave.json");
            writer.open();
            writer.write(lf);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterNoSave.json");
            reader.read();
            assertEquals(0, LevelFrame.getLevels().size());
            assertEquals(0, LevelFrame.getClist().size());
        } catch (IOException e) {
            fail();
        }
    }
    
    @Test
    void testGeneralRun() {
        LevelFrame lf = new LevelFrame();
        JsonWriter writer = new JsonWriter("./data/testWriterGeneralCase.json");
        try { 
            writer.open();
            writer.autoWrite();
            writer.write(lf.getFrame());

            assertEquals(0, LevelFrame.getLevels().size());
            assertEquals(0, LevelFrame.getClist().size());
        } catch (IOException e) {
            fail();
        }
    }
}
