package org.client.gui.models;

import org.client.gui.shapes.*;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static org.client.gui.Constants.*;

public class ToolbarMouseAdapter extends MouseAdapter {
    private final AppModel appModel = AppModel.getInstance();
    private long count = 0;                 // 도형 id용(임시)

    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            JButton source = (JButton) e.getSource();
            Shape clickedShape;
            switch (source.getName()) {
                case TOOLBAR_LINE -> clickedShape = new Line();
                case TOOLBAR_RECT -> clickedShape = new Rectangle();
                case TOOLBAR_OVAL -> clickedShape = new Oval();
                case TOOLBAR_TEXT -> clickedShape = new Text();
                default -> {
                    return;
                }
            }
            clickedShape.setLocation(300, 300, 400, 400);
            clickedShape.setStyle(appModel.getStyle());
            clickedShape.setId(count++);
            appModel.createByUser(clickedShape);
        }
    }
}

