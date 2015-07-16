package com.tailoredshapes.stl.alexa;

import javax.swing.*;

public class Alexa {

    public final JFrame root;
    private final FileCombiner combiner;
    private final MachineSpec spec;

    public Alexa(JFrame root, FileCombiner combiner, MachineSpec machineSpec){
        this.root = root;
        this.combiner = combiner;
        this.spec = machineSpec;
    }
    public void start(){
        root.setContentPane(new GUI(combiner, spec).startingPanel.build(root));
        root.pack();
        root.setVisible(true);
    }
    public static void main(String... args){
        new Alexa(new JFrame("Alexa STL"),
                  new ClojureFileCombiner(),
                  new MachineSpec(70.0, 70.0, 70.0)).start();
    }
}
