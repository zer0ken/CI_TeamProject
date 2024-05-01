package org.client.gui.models;

import org.client.gui.shapes.Shape;

import javax.swing.*;

public class ShapeListModel extends DefaultListModel<Shape> {

    public ShapeListModel() {
        AppViewModel appViewModel = AppViewModel.getInstance();
        appViewModel.addListener(AppViewModel.Listener.CREATION, this::add);
        appViewModel.addListener(AppViewModel.Listener.MODIFICATION, this::edit);
        appViewModel.addListener(AppViewModel.Listener.REMOVAL, this::remove);
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
