package org.client.gui.components;


import org.client.gui.models.ShapeListModel;
import org.client.gui.models.ShapeListSelectionModel;
import org.client.gui.shapes.Shape;

import javax.swing.*;
import java.awt.*;

import static org.client.gui.Constants.SHAPES_WINDOW_SIZE;
import static org.client.gui.Constants.SHAPES_WINDOW_TITLE;

public class ShapesWindow extends TitledBorderJPanel {
    JList<Shape> shapeList;

    public ShapesWindow() {
        super(SHAPES_WINDOW_TITLE);
        setPreferredSize(SHAPES_WINDOW_SIZE);
//        setBorder(new MatteBorder(0, 1, 0, 0, PANEL_SEP
        shapeList = new JList<>();
        ShapeListModel shapeListModel = new ShapeListModel();
        shapeList.setModel(shapeListModel);
        shapeList.setSelectionModel(new ShapeListSelectionModel(shapeListModel));

        JScrollPane scrollPane = new JScrollPane(
                shapeList,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        scrollPane.setPreferredSize(new Dimension(200, 300));

        add(scrollPane, BorderLayout.CENTER);
    }
}
