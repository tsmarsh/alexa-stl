import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.File;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class GUITest {

    GUI gui;
    FileCombiner combiner;

    @Before
    public void setUp() throws Exception {
        combiner = mock(FileCombiner.class);
        gui = new GUI(combiner);

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
        File fakeFile = new File("");

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
    }
}