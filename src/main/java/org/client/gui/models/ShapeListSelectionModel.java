package org.client.gui.models;

import org.client.gui.shape.Shape;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ShapeListSelectionModel extends DefaultListSelectionModel implements ListSelectionListener {
    private final ShapeListModel shapeListModel;
    private final AppModel appModel;

    public ShapeListSelectionModel(ShapeListModel shapeListModel) {
        this.shapeListModel = shapeListModel;
        this.appModel = AppModel.getInstance();

        appModel.addListener(AppModel.Listener.SELECTION, this::select);

        setSelectionMode(SINGLE_SELECTION);
        addListSelectionListener(this);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
            return;
        }
        int[] selectedIndices = getSelectedIndices();
        if (selectedIndices.length == 0) {
            return;
        }
        Shape newSelectedShape = shapeListModel.get(selectedIndices[0]);
        appModel.select(newSelectedShape);
    }

    public Void select(Shape newSelectedShape) {
        if (newSelectedShape == null) {
            clearSelection();
        } else {
            removeListSelectionListener(this);
            setSelectionInterval(0, shapeListModel.indexOf(newSelectedShape));
            addListSelectionListener(this);
        }
        return null;
    }
}
