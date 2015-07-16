package com.tailoredshapes.stl.alexa;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;


class GUI {

    private FileCombiner combiner;
    private MachineSpec spec;

    public GUI(FileCombiner combiner, MachineSpec spec) {
        this.combiner = combiner;
        this.spec = spec;
    }

    public final StartingPanel startingPanel = (root) -> {
        JPanel jPanel = new JPanel();
        JButton pickInputDir = getPickDirectoryButton(root, jPanel);

        jPanel.add(pickInputDir);
        return jPanel;
    };

    private final MessagePanel messagePanel = (root, message) -> {
        JPanel jPanel = startingPanel.build(root);
        jPanel.add(new JLabel(message));
        return jPanel;
    };

    private final MachinePanel machinePanel = (root, spec) -> {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
        DecimalFormat decimalFormat = (DecimalFormat) numberFormat;
        decimalFormat.setGroupingUsed(false);

        JPanel jPanel = new JPanel();
        JLabel xLabel = new JLabel("width");
        JLabel yLabel = new JLabel("depth");
        JLabel zLabel = new JLabel("height");

        jPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JTextField xTextField = new JFormattedTextField(decimalFormat);
        xTextField.setText(String.valueOf(spec.x));
        JTextField yTextField = new JFormattedTextField(decimalFormat);
        yTextField.setText(String.valueOf(spec.y));
        JTextField zTextField = new JFormattedTextField(decimalFormat);
        zTextField.setText(String.valueOf(spec.z));

        xTextField.addActionListener((e) -> {
            JTextField field = (JTextField) e.getSource();
            spec.x = Double.parseDouble(field.getText());
        });

        yTextField.addActionListener((e) -> {
            JTextField field = (JTextField) e.getSource();
            spec.y = Double.parseDouble(field.getText());
        });

        zTextField.addActionListener((e) -> {
            JTextField field = (JTextField) e.getSource();
            spec.z = Double.parseDouble(field.getText());
        });

        jPanel.add(xLabel);
        jPanel.add(xTextField);
        jPanel.add(yLabel);
        jPanel.add(yTextField);
        jPanel.add(zLabel);
        jPanel.add(zTextField);

        return jPanel;
    };

    private final FilePanel outputPanel = (root, workingDirectory) ->{
        JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        jPanel.add(machinePanel.build(root, spec));

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

    private final FilePanel filePanel = (JFrame root, File workingDirectory) -> {
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
                double buffer = 5.0;
                combiner.combineFiles(spec, buffer, workingDirectory, selectedFile);
                root.setContentPane(messagePanel.build(root, "Success!"));
                root.pack();
                root.repaint();
            }
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
