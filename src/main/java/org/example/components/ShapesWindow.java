package org.example.components;


import org.example.ShapesViewModel;
import org.example.shapes.Shape;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.Map;

import static org.example.components._Constants.*;

public class ShapesWindow extends _ComponentJPanel implements ListSelectionListener {
    DefaultListModel<ShapeListItem> shapeListModel;
    JList<ShapeListItem> shapeList;

    public ShapesWindow(ShapesViewModel shapesViewModel) {
        super(SHAPES_WINDOW_SIZE, shapesViewModel);
        setBorder(BorderFactory.createTitledBorder(SHAPES_WINDOW_TITLE));
        setLayout(new BorderLayout());

        shapesViewModel.addListener(ShapesViewModel.Listener.CANVAS_SELECTION, this::selectByCanvas);

        shapeListModel = new DefaultListModel<>();

        shapeList = new JList<>(shapeListModel);
        shapeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        shapeList.addListSelectionListener(this);

        JScrollPane scrollPane = new JScrollPane(
                shapeList,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        for (int i = 0; i < 50; i++) {
            shapeListModel.add(0, new ShapeListItem(i*11111L, "사각형"));
        }

        scrollPane.setPreferredSize(new Dimension(200, 300));

        add(BorderLayout.CENTER, scrollPane);
    }

    public void setShapes(Map<Long, Shape> shapes) {
        shapeListModel.removeAllElements();
        shapes.forEach((id, shape) ->
                shapeListModel.add(0, new ShapeListItem(shape.getId(), shape.getRepresentation())));
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        ShapeListItem selectedShape;
        if (!shapeList.isSelectionEmpty()) {
            selectedShape = shapeListModel.get(e.getFirstIndex());
            shapesViewModel.selectByShapesWindow(selectedShape.getId());
        } else {
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
