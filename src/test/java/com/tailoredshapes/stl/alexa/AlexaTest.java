package com.tailoredshapes.stl.alexa;

import org.junit.Test;
import org.mockito.internal.matchers.Any;

import javax.swing.*;
import java.io.File;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AlexaTest {

    @Test
    public void testStart() throws Exception {

        FileCombiner combiner = mock(FileCombiner.class);
        MachineSpec spec = new MachineSpec(10, 10, 10);
        JFrame root = mock(JFrame.class);

        Alexa alexa = new Alexa(root, combiner, spec);

        alexa.start();

        verify(root).setContentPane(anyObject());
        verify(root).pack();
        verify(root).setVisible(true);
    }
}