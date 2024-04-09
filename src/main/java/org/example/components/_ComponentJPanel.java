package org.example.components;

import javax.swing.*;
import java.awt.*;

abstract class _ComponentJPanel extends JPanel {
    _ComponentJPanel(Dimension size) {
        super();
        setPreferredSize(size);
        setBorder(BorderFactory.createLineBorder(Color.black));
    }
}
