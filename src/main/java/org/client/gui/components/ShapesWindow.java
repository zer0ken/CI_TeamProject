package org.client.gui.components;


import org.client.gui.Theme;
import org.client.gui.models.ShapeListModel;
import org.client.gui.models.ShapeListSelectionModel;
import org.client.gui.shapes.Shape;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

import static org.client.gui.Constants.*;

public class ShapesWindow extends JPanel {
    JList<Shape> shapeList;

    public ShapesWindow() {
        setLayout(new BorderLayout());

        TitlePanel titlePanel = new TitlePanel(SHAPES_WINDOW_TITLE, SHAPES_WINDOW_TOOLTIP);
        titlePanel.setBorder(new MatteBorder(0, 1, 0, 0, Theme.getBorderColor()));

        shapeList = new JList<>();
        ShapeListModel shapeListModel = new ShapeListModel();
        shapeList.setModel(shapeListModel);
        shapeList.setSelectionModel(new ShapeListSelectionModel(shapeListModel));

        JScrollPane scrollPane = new JScrollPane(
                shapeList,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        add(titlePanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
}
