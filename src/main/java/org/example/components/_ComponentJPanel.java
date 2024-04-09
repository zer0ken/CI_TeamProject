package org.example.components;

import javax.swing.*;
import java.awt.*;

abstract class _ComponentJPanel extends JPanel {
    _ComponentJPanel(Rectangle bounds) {
        super();
        setBounds(bounds);
        setBorder(BorderFactory.createLineBorder(Color.black));

    }
}
