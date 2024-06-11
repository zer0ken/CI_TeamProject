package org.client.gui.models;

import org.client.gui.shape.Shape;
import org.client.gui.shape.ShapeHandler;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class CanvasModel extends MouseAdapter implements MouseMotionListener {
    private static CanvasModel instance;
    private final AppModel appModel = AppModel.getInstance();

    private final ShapeHandler handler = new ShapeHandler();

    private CanvasModel() {
        appModel.addListener(AppModel.Listener.SELECTION, selected -> {
            if (selected == null
                    || !handler.isDragging()
                    || !selected.equals(handler.getTarget())) {
                handler.setTarget(selected);
            }
            return null;
        });
    }

    public static CanvasModel getInstance() {
        if (instance == null) {
            instance = new CanvasModel();
        }
        return instance;
    }

    public ShapeHandler getHandler() {
        return handler;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (appModel.getSelectedShape() != null && handler.contains(e.getPoint())) {
            handler.startDrag(e.getPoint());
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (appModel.getSelectedShape() == null) {
            return;
        }
        if (!handler.isDragging()) {
            return;
        }
        if (handler.isMoving()) {
            appModel.moveByUser(handler.drag(e.getPoint()));
        } else {
            appModel.resizeByUser(handler.drag(e.getPoint()));
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (handler.isDragging()) {
            handler.finishDrag();
        } else {
            for (Shape shape : appModel.getShapes().values()) {
                if (shape.contains(e.getPoint())) {
                    appModel.select(shape);
                    return;
                }
            }
            appModel.select(null);
        }
    }
}
