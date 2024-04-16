package org.example.components;

import javax.swing.*;
import java.awt.*;

public class VerticalJPanel extends JPanel {
    VerticalJPanel() {
        super();
        LayoutManager layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(layout);
    }
}
