package org.client.gui.models;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.List;

public class PanelVisibilityController implements ChangeListener {
    private final String[] sourceButtons;
    private final List<JPanel> targetPanels;

    public PanelVisibilityController(String[] sourceButtons, JPanel... targetPanels) {
        this.sourceButtons = sourceButtons;
        this.targetPanels = List.of(targetPanels);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JToggleButton button = (JToggleButton) e.getSource();
        int index = List.of(sourceButtons).indexOf(button.getName() != null ? button.getName() : "");
        if (index == -1) return;
        targetPanels.get(index).setVisible(button.isSelected());
    }
}
