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
        appModel.addListener(AppModel.Listener.SELECTION, shape -> {
            handler.setTarget(shape);
            return null;
        });
        appModel.addListener(AppModel.Listener.SERVER_MODIFICATION, shape -> {
            if (shape.equals(handler.getTarget())){
                handler.setTarget(shape);
            }
            return null;
        });
        appModel.addListener(AppModel.Listener.SERVER_REMOVAL, shape -> {
            if (shape != null && shape.equals(handler.getTarget())){
                handler.setTarget(null);
                handler.finishDrag();
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
        Shape clicked = null;
        for (Shape shape : appModel.getShapes().values()) {
            if (shape.contains(e.getPoint())) {
                clicked = shape;
            }
        }
        if (clicked == null) {
            if (appModel.getSelectedShape() != null) {
                appModel.select(null);
            } else {
                Shape shape = new Shape(
                        appModel.getType(),
                        appModel.getStyle(),
                        e.getX(),
                        e.getY(),
                        appModel.getMyself(),
                        System.currentTimeMillis()
                );
                appModel.createByUser(shape);
                appModel.select(shape);
                handler.setTarget(shape);
                handler.startCreation(e.getPoint());
                appModel.setModifying(true);
            }
        } else if (handler.getTarget() != null && handler.getTarget().equals(clicked)) {
            handler.startDrag(e.getPoint());
            appModel.setModifying(true);
        } else {
            appModel.select(clicked);
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
            boolean resized = !handler.isMoving();
            Shape rearranged = handler.finishDrag();
            if(resized && rearranged.getType() != Shape.Type.LINE) {
                appModel.resizeByUser(rearranged);
            }
            appModel.setModifying(false);
        }
    }
}
