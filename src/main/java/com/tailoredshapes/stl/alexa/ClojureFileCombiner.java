package com.tailoredshapes.stl.alexa;

import java.io.File;

import static stlcollector.Core.combine;

public class ClojureFileCombiner implements FileCombiner{

    public void combineFiles(MachineSpec spec, double buffer, File workingDirectory, File targetFile) {
        combine(new double[]{spec.x, spec.y, spec.z}, buffer, targetFile, workingDirectory);
    }
}
