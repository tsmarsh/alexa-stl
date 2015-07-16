import java.io.File;

interface  FileCombiner {
    void combineFiles(MachineSpec spec, double buffer, File workingDirectory, File targetFile);
}
