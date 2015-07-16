import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;


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

    public MessagePanel messagePanel = (root, message) -> {
        JPanel jPanel = startingPanel.build(root);
        jPanel.add(new JLabel(message));
        return jPanel;
    };


    public FilePanel outputPanel = (root, workingDirectory) ->{
        JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton save = new JButton("Save");
        ActionListener saveFileSelected = (actionEvent) -> {
            JFileChooser saver = new JFileChooser();
            saver.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            saver.addActionListener(onSaveFileSelected(root, workingDirectory, saver, messagePanel, combiner));
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


    ActionListener onSaveFileSelected(JFrame root,
                                              File workingDirectory,
                                              JFileChooser saver,
                                              MessagePanel messagePanel,
                                              FileCombiner combiner) {
        return (fileEvent) -> {
            File selectedFile = saver.getSelectedFile();
            if(selectedFile != null){
                MachineSpec spec = new MachineSpec(70.0, 70.0, 70.0);
                double buffer = 5.0;
                combiner.combineFiles(spec, buffer, workingDirectory, selectedFile);
                root.setContentPane(messagePanel.build(root, "Success!"));
                root.pack();
                root.repaint();
            }
        };
    }

    JButton getPickDirectoryButton(JFrame root, JPanel jPanel) {
        JButton pickInputDir = new JButton("Pick Directory");
        pickInputDir.addActionListener(onPickDirectory(root, jPanel));
        return pickInputDir;
    }

    ActionListener onPickDirectory(JFrame root, JPanel jPanel) {
        return (actionEvent) -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            chooser.addActionListener(workdirSelected(root, chooser, filePanel));
            chooser.showOpenDialog(jPanel);
        };
    }

    ActionListener workdirSelected(JFrame root, JFileChooser chooser, FilePanel filePanel) {
        return (fileEvent) -> {
            if (chooser.getSelectedFile() != null) {
                root.setContentPane(filePanel.build(root, chooser.getSelectedFile()));
                root.pack();
                root.repaint();
            }
        };
    }
}
