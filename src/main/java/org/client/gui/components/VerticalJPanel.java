package org.client.gui.components;

import javax.swing.*;
import java.awt.*;

public class VerticalJPanel extends JPanel {
    public VerticalJPanel() {
        super();
        LayoutManager layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(layout);
    }
}
