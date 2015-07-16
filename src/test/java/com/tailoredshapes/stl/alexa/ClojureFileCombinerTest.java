package com.tailoredshapes.stl.alexa;

import com.tailoredshapes.stl.alexa.ClojureFileCombiner;
import com.tailoredshapes.stl.alexa.MachineSpec;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;

import static junit.framework.Assert.assertEquals;

public class ClojureFileCombinerTest {

    @Test
    public void shouldCombineFiles() throws Exception {
        ClojureFileCombiner clojureFileCombiner = new ClojureFileCombiner();
        File stl = new File(this.getClass().getResource("stls").getFile());
        File outputFile = Files.createTempDirectory("test").toFile();

        MachineSpec spec = new MachineSpec(30, 30, 30);

        clojureFileCombiner.combineFiles(spec, 5.0, stl, outputFile);

        int numberOfFiles = outputFile.listFiles().length;

        assertEquals(1, numberOfFiles);

        outputFile.delete();
    }
}