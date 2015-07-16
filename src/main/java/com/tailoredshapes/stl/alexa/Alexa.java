package com.tailoredshapes.stl.alexa;

import javax.swing.*;

public class Alexa {

    public final JFrame root;
    private final FileCombiner combiner;

    public Alexa(JFrame root, FileCombiner combiner){
        this.root = root;
        this.combiner = combiner;
    }
    public void start(){
        root.setContentPane(new GUI(combiner).startingPanel.build(root));
        root.pack();
        root.setVisible(true);
    }
    public static void main(String... args){
        new Alexa(new JFrame("Alexa STL"),
                  new ClojureFileCombiner()).start();
    }
}
