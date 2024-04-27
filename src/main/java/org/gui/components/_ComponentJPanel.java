package org.gui.components;

import org.gui.ShapesViewModel;

import javax.swing.*;
import java.awt.*;

abstract class _ComponentJPanel extends JPanel {
    protected final ShapesViewModel shapesViewModel;

    _ComponentJPanel(Dimension size, ShapesViewModel shapesViewModel) {
        super();
        setPreferredSize(size);
        setBorder(BorderFactory.createLineBorder(Color.black));
        this.shapesViewModel = shapesViewModel;
    }
}
