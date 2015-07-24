package com.tailoredshapes.stl.alexa;

import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.attribute.FileAttribute;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class GUITest {

    private GUI gui;
    private FileCombiner combiner;

    @Before
    public void setUp() throws Exception {
        combiner = mock(FileCombiner.class);
        gui = new GUI(combiner, new MachineSpec(70.0, 70.0, 70.0));

    }

    @Test
    public void shouldNotThrowExceptionIfNoFileSelected() throws Exception {
        JFrame root = mock(JFrame.class);
        JFileChooser chooser = mock(JFileChooser.class);
        ActionListener subject = gui.workdirSelected(root, chooser, null);
        subject.actionPerformed(null);
    }

    @Test
    public void shouldUpdateRootFrameIfAFileIsSelected() throws Exception {
        JFrame root = mock(JFrame.class);
        File fakeFile = Files.createTempDirectory("test").toFile();

        JFileChooser chooser = mock(JFileChooser.class);
        when(chooser.getSelectedFile()).thenReturn(fakeFile);

        JPanel nextPanel = mock(JPanel.class);
        FilePanel workFileChooser = mock(FilePanel.class);
        when(workFileChooser.build(root, fakeFile)).thenReturn(nextPanel);


        ActionListener subject = gui.workdirSelected(root, chooser, workFileChooser);
        subject.actionPerformed(null);

        verify(root).setContentPane(nextPanel);
        verify(root).pack();
        verify(root).repaint();

        fakeFile.delete();
    }

    @Test
    public void shouldCallTheCombiner() throws Exception{
        File destination = File.createTempFile("test", "stl");
        File workingDir = File.createTempFile("shouldBe", "aDir");
        JButton save = mock(JButton.class);


        JFrame root = mock(JFrame.class);
        JFileChooser chooser = mock(JFileChooser.class);
        when(chooser.getSelectedFile()).thenReturn(destination);

        MessagePanel startingPanel = mock(MessagePanel.class);

        JPanel nextPanel = mock(JPanel.class);
        when(startingPanel.build(eq(root), anyString())).thenReturn(nextPanel);


        ActionListener actionListener = gui.onSaveFileSelected(root, workingDir, chooser, startingPanel, combiner, save);

        actionListener.actionPerformed(null);


        verify(combiner).combineFiles(any(MachineSpec.class),
                eq(5.0),
                eq(workingDir),
                eq(destination));

        verify(root).setContentPane(nextPanel);
        verify(root).pack();
        verify(root).repaint();
    }

    @Test
    public void shouldNotShitTheBedWhenNoSaveFileIsSelected() throws Exception{
        File workingDir = File.createTempFile("shouldBe", "aDir");
        JButton save = mock(JButton.class);

        JFrame root = mock(JFrame.class);
        JFileChooser chooser = mock(JFileChooser.class);

        MessagePanel startingPanel = mock(MessagePanel.class);

        ActionListener actionListener = gui.onSaveFileSelected(root, workingDir, chooser, startingPanel, combiner, save);

        actionListener.actionPerformed(null);
    }
}