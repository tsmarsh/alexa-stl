import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by tmarsh on 7/14/15.
 */
public class GUI {

    private FileCombiner combiner;

    public GUI(FileCombiner combiner) {
        this.combiner = combiner;
    }

    public StartingPanel startingPanel = (root) -> {
        JPanel jPanel = new JPanel();
        JButton pickInputDir = getPickDirectoryButton(root, jPanel);

        jPanel.add(pickInputDir);
        return jPanel;
    };

    public FilePanel outputPanel = (root, workingDirectory) ->{
        JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton save = new JButton("Save");
        ActionListener saveFileSelected = (actionEvent) -> {
            JFileChooser saver = new JFileChooser();
            combiner = new ClojureFileCombiner();
            saver.addActionListener(onSaveFileSelected(root, workingDirectory, saver, startingPanel, combiner));
            saver.showSaveDialog(root);
        };
        save.addActionListener(saveFileSelected);
        jPanel.add(save);
        return jPanel;
    };

    public FilePanel filePanel = (JFrame root, File workingDirectory) -> {
        JPanel jPanel = new JPanel();
        File[] stlFiles = workingDirectory.listFiles((dir, name) -> {
            return name.endsWith("stl");
        });
        JList jList = new JList<>(stlFiles);

        jPanel.add(jList);
        jPanel.add(getPickDirectoryButton(root, jPanel));
        jPanel.add(outputPanel.build(root, workingDirectory));
        return jPanel;
    };


    private ActionListener onSaveFileSelected(JFrame root, File workingDirectory, JFileChooser saver, StartingPanel startingPanel, FileCombiner combiner) {
        return (fileEvent) -> {
            File selectedFile = saver.getSelectedFile();
            MachineSpec spec = new MachineSpec(70.0, 70.0, 70.0);
            double buffer = 5.0;
            combiner.combineFiles(spec, buffer, selectedFile.getAbsolutePath(), workingDirectory.getAbsolutePath());
            JOptionPane.showMessageDialog(root, "Success");
            root.setContentPane(startingPanel.build(root));
            root.pack();
            root.repaint();
        };
    }

    private JButton getPickDirectoryButton(JFrame root, JPanel jPanel) {
        JButton pickInputDir = new JButton("Pick Directory");
        pickInputDir.addActionListener(onPickDirectory(root, jPanel));
        return pickInputDir;
    }

    private ActionListener onPickDirectory(JFrame root, JPanel jPanel) {
        return (actionEvent) -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            chooser.addActionListener(workdirSelected(root, chooser, filePanel));
            chooser.showOpenDialog(jPanel);
        };
    }

    public ActionListener workdirSelected(JFrame root, JFileChooser chooser, FilePanel filePanel) {
        return (fileEvent) -> {
            if (chooser.getSelectedFile() != null) {
                root.setContentPane(filePanel.build(root, chooser.getSelectedFile()));
                root.pack();
                root.repaint();
            }
        };
    }
}
