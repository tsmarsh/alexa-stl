package com.tailoredshapes.stl.alexa;

import javax.swing.*;

public class Alexa {

    public static JFrame alexa;

    public static void main(String... args){
        alexa = new JFrame("Alexa");
        GUI gui = new GUI(new ClojureFileCombiner());
        alexa.setContentPane(gui.startingPanel.build(alexa));
        alexa.pack();
        alexa.setVisible(true);
    }
}
