package org.client.gui.models;

import org.client.gui.shapes.Shape;

import javax.swing.*;

public class ShapeListModel extends DefaultListModel<Shape> {

    public ShapeListModel() {
        AppModel appModel = AppModel.getInstance();
        appModel.addListener(AppModel.Listener.CREATION, this::add);
        appModel.addListener(AppModel.Listener.MODIFICATION, this::edit);
        appModel.addListener(AppModel.Listener.REMOVAL, this::remove);
    }

    public Void add(Shape shape) {
        add(0, shape);
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
