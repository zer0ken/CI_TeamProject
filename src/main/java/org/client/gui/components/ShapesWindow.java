package org.client.gui.components;


import org.client.gui.Utils;
import org.client.gui.models.ShapeListModel;
import org.client.gui.models.ShapeListSelectionModel;
import org.client.gui.shapes.Shape;

import javax.swing.*;
import java.awt.*;

import static org.client.gui.Constants.*;

public class ShapesWindow extends JPanel {
    JList<Shape> shapeList;

    public ShapesWindow() {
        setLayout(new BorderLayout());
        setPreferredSize(SHAPES_WINDOW_SIZE);

        shapeList = new JList<>();
        ShapeListModel shapeListModel = new ShapeListModel();
        shapeList.setModel(shapeListModel);
        shapeList.setSelectionModel(new ShapeListSelectionModel(shapeListModel));

        JScrollPane scrollPane = Utils.wrapWithScrollPane(shapeList);

        add(new TitlePanel(SHAPES_WINDOW_TITLE, SHAPES_WINDOW_TOOLTIP), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
}
