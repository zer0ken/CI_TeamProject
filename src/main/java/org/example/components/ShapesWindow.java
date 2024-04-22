package org.example.components;


import org.example.shapes.Shape;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.Map;

import static org.example.components._Constants.*;

public class ShapesWindow extends _ComponentJPanel implements ListSelectionListener {
    Canvas canvas;
    DefaultListModel<ShapeListItem> shapeListModel;
    JList<ShapeListItem> shapeList;
    ShapeListItem selectedShape = null;

    ShapesWindow(Canvas canvas) {
        this();
        this.canvas = canvas;

        canvas.addOnSelectedListener(this::onCanvasSelected);
    }

    private ShapesWindow() {
        super(SHAPES_WINDOW_SIZE);
        setBorder(BorderFactory.createTitledBorder(SHAPES_WINDOW_TITLE));
        setLayout(new BorderLayout());

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
        shapes.forEach((id, shape) -> shapeListModel.add(0, new ShapeListItem(shape.getId(), shape.getType())));
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (shapeList.isSelectionEmpty()) {
            selectedShape = null;
            canvas.select(-1);
            return;
        }
        selectedShape = shapeListModel.get(e.getFirstIndex());
        canvas.select(selectedShape.getId());
    }

    public Void onCanvasSelected(Shape newSelectedShape) {
        if (newSelectedShape == null) {
            selectedShape = null;
            shapeList.clearSelection();
            return null;
        }
        if (selectedShape != null && selectedShape.getId() == newSelectedShape.getId()) {
            return null;
        }
        for (int i = 0; i < shapeListModel.getSize(); i++) {
            ShapeListItem item = shapeListModel.get(i);
            if (item.getId() == newSelectedShape.getId()) {
                selectedShape = item;
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
