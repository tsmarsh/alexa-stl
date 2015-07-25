package com.tailoredshapes.stl.alexa;

import javax.swing.*;

interface MachinePanel {
    JPanel build(JFrame root, MachineSpec model);
}
