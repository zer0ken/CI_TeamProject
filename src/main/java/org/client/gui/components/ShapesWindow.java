package org.client.gui.components;


import org.client.gui.ShapesViewModel;
import org.client.gui.shapes.Shape;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.Map;

import static org.client.gui.components._Constants.*;

public class ShapesWindow extends _ComponentJPanel implements ListSelectionListener {
    DefaultListModel<ShapeListItem> shapeListModel;
    JList<ShapeListItem> shapeList;

    public ShapesWindow(ShapesViewModel shapesViewModel) {
        super(SHAPES_WINDOW_SIZE, shapesViewModel);
        setBorder(BorderFactory.createTitledBorder(SHAPES_WINDOW_TITLE));
        setLayout(new BorderLayout());

        shapesViewModel.addListener(ShapesViewModel.Listener.CANVAS_SELECTION, this::selectByCanvas);
        shapesViewModel.addListener(ShapesViewModel.Listener.CREATION, this::update);
        shapesViewModel.addListener(ShapesViewModel.Listener.MODIFICATION, this::update);
        shapesViewModel.addListener(ShapesViewModel.Listener.REMOVAL, this::update);

        shapeListModel = new DefaultListModel<>();

        shapeList = new JList<>(shapeListModel);
        shapeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        shapeList.addListSelectionListener(this);

        JScrollPane scrollPane = new JScrollPane(
                shapeList,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        scrollPane.setPreferredSize(new Dimension(200, 300));

        add(BorderLayout.CENTER, scrollPane);
    }

    public Void update(Shape unused) {
        setShapes(shapesViewModel.getShapes());
        return null;
    }

    public void setShapes(Map<Long, Shape> shapes) {
        shapeListModel.removeAllElements();
        shapes.forEach((id, shape) ->
                shapeListModel.add(0, new ShapeListItem(shape.getId(), shape.getRepresentation())));
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
            return;
        }
        ShapeListItem selectedShape;
        if (!shapeList.isSelectionEmpty()) {
            selectedShape = shapeList.getSelectedValue();
            if (shapesViewModel.getSelectedShape() == null
                    || shapesViewModel.getSelectedShape().getId() != selectedShape.getId()) {
                shapesViewModel.selectByShapesWindow(selectedShape.getId());
            }
        } else if (shapesViewModel.getSelectedShape() != null) {
            shapesViewModel.selectByShapesWindow(-1);
        }
    }

    public Void selectByCanvas(Shape selectedShape) {
        if (selectedShape == null) {
            shapeList.clearSelection();
            return null;
        }
        for (int i = 0; i < shapeListModel.getSize(); i++) {
            ShapeListItem item = shapeListModel.get(i);
            if (item.getId() == selectedShape.getId()) {
                shapeList.setSelectedIndex(i);
                return null;
            }
        }
        return null;
    }
}

class ShapeListItem {
    private final long id;
    private final String shapeRepresentation;

    ShapeListItem(Long id, String shapeRepresentation) {
        this.id = id;
        this.shapeRepresentation = shapeRepresentation;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return shapeRepresentation + "(" + id + ")";
    }
}
