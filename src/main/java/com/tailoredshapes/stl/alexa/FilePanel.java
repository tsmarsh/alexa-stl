package com.tailoredshapes.stl.alexa;

import javax.swing.*;
import java.io.File;

interface FilePanel {
    JPanel build(JFrame root, File workingDirectory);
}
