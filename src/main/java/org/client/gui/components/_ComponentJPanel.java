package org.client.gui.components;

import org.client.gui.ShapesViewModel;

import javax.swing.*;
import java.awt.*;

abstract class _ComponentJPanel extends JPanel {
    protected final ShapesViewModel shapesViewModel;

    _ComponentJPanel(Dimension size, ShapesViewModel shapesViewModel) {
        super();
        setPreferredSize(size);
        this.shapesViewModel = shapesViewModel;
    }
}
