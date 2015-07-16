import junit.framework.Assert;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.attribute.FileAttribute;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.*;

public class ClojureFileCombinerTest {

    @Test
    public void shouldCombineFiles() throws Exception {
        ClojureFileCombiner clojureFileCombiner = new ClojureFileCombiner();
        File stl = new File(this.getClass().getResource("stls").getFile());
        File outputFile = Files.createTempDirectory("test").toFile();

        MachineSpec spec = new MachineSpec(30, 30, 30);

        clojureFileCombiner.combineFiles(spec, 5.0, stl, outputFile);

        int spaceAfter = outputFile.listFiles().length;

        assertTrue(String.format("After: %d", spaceAfter), 0 < spaceAfter);

        outputFile.delete();
    }
}