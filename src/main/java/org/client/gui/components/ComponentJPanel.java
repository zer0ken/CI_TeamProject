package org.client.gui.components;

import org.client.gui.models.AppViewModel;

import javax.swing.*;
import java.awt.*;

abstract class ComponentJPanel extends JPanel {
    protected final AppViewModel appViewModel;

    ComponentJPanel(Dimension size) {
        super();
        setPreferredSize(size);
        this.appViewModel = AppViewModel.getInstance();
    }
}
