import java.io.File;

public interface  FileCombiner {
    void combineFiles(MachineSpec spec, double buffer, File workingDirectory, File targetFile);
}
