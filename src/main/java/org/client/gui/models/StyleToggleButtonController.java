package org.client.gui.models;

import javax.swing.*;

public class StyleToggleButtonController {
    public StyleToggleButtonController(JToggleButton styleButton, JToggleButton editButton) {
        AppModel.getInstance().addListener(AppModel.Listener.SELECTION, shape -> {
            if (styleButton.isSelected() == editButton.isSelected()) {
                return null;
            }
            if (shape == null && !styleButton.isSelected()) {
                styleButton.setSelected(true);
                editButton.setSelected(false);
            }
            if (shape != null && !editButton.isSelected()) {
                styleButton.setSelected(false);
                editButton.setSelected(true);
            }
            return null;
        });
    }
}
