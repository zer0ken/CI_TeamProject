package org.client.gui.models;

import org.client.gui.shape.Shape;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static org.client.gui.Constants.*;

public class ToolbarMouseAdapter extends MouseAdapter {
    private final AppModel appModel = AppModel.getInstance();

    public void mouseClicked(MouseEvent e) {
        JButton source = (JButton) e.getSource();

        if (source.getName().equals(TOOLBAR_UNDO)) {
            appModel.undo();
        } else if (source.getName().equals(TOOLBAR_REDO)) {
            appModel.redo();
        } else if (e.getClickCount() == 2) {
            Shape.Type type = switch (source.getName()) {
                case TOOLBAR_LINE -> Shape.Type.LINE;
                case TOOLBAR_RECT -> Shape.Type.RECTANGLE;
                case TOOLBAR_OVAL -> Shape.Type.OVAL;
                case TOOLBAR_TEXT -> Shape.Type.TEXT;
                default -> throw new IllegalStateException("Unexpected value: " + source.getName());
            };

            Shape newShape = new Shape(
                    type, appModel.getStyle(),
                    300, 300, 400, 400,
                    appModel.getMyself(), System.currentTimeMillis()
            );
            appModel.createByUser(newShape);
        }
    }
}

