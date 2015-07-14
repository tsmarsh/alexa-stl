import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;

import clojure.java.api.Clojure;
import clojure.lang.IFn;

public class Alexa {

    public static void main(String... args){
        JFrame alexa = new JFrame("Alexa");
        GUI gui = new GUI(new ClojureFileCombiner());
        alexa.setContentPane(gui.startingPanel.build(alexa));
        alexa.pack();
        alexa.setVisible(true);
    }
}
