package org.client.gui.models;

import org.client.gui.shape.Shape;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static org.client.gui.Constants.*;

public class ToolbarMouseAdapter extends MouseAdapter {
    private final AppModel appModel = AppModel.getInstance();

    public void mouseClicked(MouseEvent e) {
        JToggleButton source = (JToggleButton) e.getSource();

        if (source.getName().equals(TOOLBAR_UNDO)) {
            appModel.undo();
        } else if (source.getName().equals(TOOLBAR_REDO)) {
            appModel.redo();
        } else {
            appModel.setType(switch (source.getName()) {
                case TOOLBAR_LINE -> Shape.Type.LINE;
                case TOOLBAR_RECT -> Shape.Type.RECTANGLE;
                case TOOLBAR_OVAL -> Shape.Type.OVAL;
                case TOOLBAR_TEXT -> Shape.Type.TEXT;
                default -> throw new IllegalStateException("Unexpected value: " + source.getName());
            });
        }
    }
}

