import clojure.java.api.Clojure;
import clojure.lang.IFn;

public class ClojureFileCombiner implements FileCombiner{
    public ClojureFileCombiner() {
        IFn require = Clojure.var("clojure.core", "require");
        require.invoke(Clojure.read("stl-collector.core"));
    }

    public void combineFiles(MachineSpec spec, double buffer, String workingDirectory, String targetFile) {
        IFn combineFiles = Clojure.var("stl-collector.core", "combine-files");
        combineFiles.invoke(Clojure.read(String.format("[%f %f %f]", spec.x, spec.y, spec.z)),
                5.0,
                workingDirectory,
                targetFile);
    }
}
