import clojure.java.api.Clojure;
import clojure.lang.IFn;

import java.io.File;

public interface  FileCombiner {
    void combineFiles(MachineSpec spec, double buffer, String workingDirectory, String targetFile);
}
