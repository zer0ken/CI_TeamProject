package org.client.gui.models;

import org.client.gui.shapes.*;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static org.client.gui.Constants.*;

public class ToolbarMouseAdapter extends MouseAdapter {
    private final AppModel appModel = AppModel.getInstance();

    public void mouseClicked(MouseEvent e) {
        JButton source = (JButton) e.getSource();
        if (e.getClickCount() == 2) {
            Shape newShape;
            switch (source.getName()) {
                case TOOLBAR_LINE -> newShape = new Line();
                case TOOLBAR_RECT -> newShape = new Rectangle();
                case TOOLBAR_OVAL -> newShape = new Oval();
                case TOOLBAR_TEXT -> newShape = new Text();
                default -> {
                    return;
                }
            }
            newShape.setLocation(300, 300, 400, 400);
            newShape.setStyle(appModel.getStyle());
            newShape.setTimestamp(System.currentTimeMillis());
            newShape.setAuthor(appModel.getMyself());
            appModel.storeUndoStack(UserAction.Type.CREATE, newShape, null);
            appModel.undoAvailable(true);
            appModel.createByUser(newShape);
            return;
        }

        if (source.getName().equals(TOOLBAR_DELETE)) {
            if (appModel.getSelectedShape() != null) {
                appModel.storeUndoStack(UserAction.Type.DELETE, appModel.getSelectedShape(), null);
                appModel.undoAvailable(true);
                appModel.removeByUser(appModel.getSelectedShape().getId());
                appModel.select(null);
            }
        } else if (source.getName().equals(TOOLBAR_UNDO)) {
            appModel.unDo();
        } else if (source.getName().equals(TOOLBAR_REDO)) {
            appModel.reDo();
        }

    }
}

