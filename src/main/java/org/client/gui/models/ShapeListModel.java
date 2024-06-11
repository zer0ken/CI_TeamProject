package org.client.gui.models;

import org.client.gui.shape.Shape;

import javax.swing.*;

public class ShapeListModel extends DefaultListModel<Shape> {
    private final AppModel appModel;

    public ShapeListModel() {
        appModel = AppModel.getInstance();
        appModel.addListener(AppModel.Listener.CREATION, this::add);
        appModel.addListener(AppModel.Listener.MODIFICATION, this::edit);
        appModel.addListener(AppModel.Listener.REMOVAL, this::remove);
    }

    private Void clear(Void unused) {
        clear();
        return null;
    }

    public Void add(Shape shape) {
        if (isEmpty() || get(0).compareTo(shape) < 0) {
            add(0, shape);
            return null;
        }
        clear();
        appModel.getShapes().forEach((k, v) -> add(0, v));
        return null;
    }

    public Void edit(Shape shape) {
        set(indexOf(shape), shape);
        return null;
    }

    public Void remove(Shape shape) {
        removeElement(shape);
        return null;
    }
}
