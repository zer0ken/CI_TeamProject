package org.client.gui.components;

import org.client.gui.models.AppModel;

import javax.swing.*;
import java.awt.*;

abstract class ComponentJPanel extends JPanel {
    protected final AppModel appModel;

    ComponentJPanel(Dimension size) {
        super();
        setPreferredSize(size);
        this.appModel = AppModel.getInstance();
    }
}
