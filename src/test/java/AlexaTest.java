import org.junit.Test;

import javax.swing.*;
import javax.swing.tree.ExpandVetoException;
import java.awt.event.ActionListener;
import java.io.File;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class AlexaTest {


    @Test
    public void shouldNotThrowExceptionIfNoFileSelected() throws Exception {
        JFrame root = new JFrame();
        JFileChooser chooser = mock(JFileChooser.class);
        ActionListener subject = Alexa.workdirSelected(root, chooser, null);
        subject.actionPerformed(null);
    }

    @Test
    public void shouldUpdateRootFrameIfAFileIsSelected() throws Exception {
        JFrame root = mock(JFrame.class);
        File fakeFile = new File("");

        JFileChooser chooser = mock(JFileChooser.class);
        when(chooser.getSelectedFile()).thenReturn(fakeFile);

        JPanel nextPanel = mock(JPanel.class);
        Alexa.WorkingPanel workFileChooser = mock(Alexa.WorkingPanel.class);
        when(workFileChooser.workingPanel(root, fakeFile)).thenReturn(nextPanel);


        ActionListener subject = Alexa.workdirSelected(root, chooser, workFileChooser);
        subject.actionPerformed(null);

        verify(root).setContentPane(nextPanel);
        verify(root).pack();
        verify(root).repaint();
    }
}