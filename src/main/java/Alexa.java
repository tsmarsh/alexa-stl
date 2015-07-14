import javax.swing.*;
import java.awt.*;
import java.io.File;

import clojure.java.api.Clojure;
import clojure.lang.IFn;

public class Alexa {

    public static JPanel workingPanel(JFrame root, File workingDirectory){
        JPanel jPanel = new JPanel();
        File[] stlFiles = workingDirectory.listFiles((dir, name) -> {
            return name.endsWith("stl");
        });
        JList jList = new JList<>(stlFiles);

        jPanel.add(jList);
        jPanel.add(getPickDirectoryButton(root, jPanel));
        jPanel.add(outputPanel(root, workingDirectory));
        return jPanel;
    }

    public static JPanel outputPanel(JFrame root, File workingDirectory){
        JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton save = new JButton("Save");
        save.addActionListener((actionEvent) -> {
            JFileChooser saver = new JFileChooser();
            saver.addActionListener((fileEvent) -> {
                File selectedFile = saver.getSelectedFile();
                IFn combineFiles = Clojure.var("stl-collector.core", "combine-files");
                combineFiles.invoke(Clojure.read("[70.0 70.0 70.0]"),
                        5.0,
                        selectedFile.getAbsolutePath(),
                        workingDirectory.getAbsoluteFile());
                JOptionPane.showMessageDialog(root, "Success");
                root.setContentPane(startingPanel(root));
                root.pack();
                root.repaint();
            });
            saver.showSaveDialog(root);
        });
        jPanel.add(save);
        return jPanel;
    }

    public static JPanel startingPanel(JFrame root){
        JPanel jPanel = new JPanel();
        JButton pickInputDir = getPickDirectoryButton(root, jPanel);

        jPanel.add(pickInputDir);
        return jPanel;
    }

    private static JButton getPickDirectoryButton(JFrame root, JPanel jPanel) {
        JButton pickInputDir = new JButton("Pick Directory");
        pickInputDir.addActionListener((actionEvent) -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.addActionListener((fileEvent) -> {
                root.setContentPane(workingPanel(root, chooser.getSelectedFile()));
                root.pack();
                root.repaint();
            });
            chooser.showOpenDialog(jPanel);
        });
        return pickInputDir;
    }

    public static void main(String... args){
        IFn require = Clojure.var("clojure.core", "require");
        require.invoke(Clojure.read("stl-collector.core"));
        JFrame alexa = new JFrame("Alexa");
        alexa.setContentPane(startingPanel(alexa));
        alexa.pack();
        alexa.setVisible(true);
    }
}
