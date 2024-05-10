package org.client.gui.models;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RemoveActionListener implements ActionListener {
    private final AppModel appModel = AppModel.getInstance();

    @Override
    public void actionPerformed(ActionEvent e) {
        if (appModel.getSelectedShape() != null) {
            appModel.removeByUser(appModel.getSelectedShape().getId());
        }
    }
}
