package org.client.gui.components;


import org.client.gui.Theme;
import org.client.gui.Utils;
import org.client.gui.models.ShapeListModel;
import org.client.gui.models.ShapeListSelectionModel;
import org.client.gui.shapes.Shape;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

import static org.client.gui.Constants.SHAPES_WINDOW_TITLE;
import static org.client.gui.Constants.SHAPES_WINDOW_TOOLTIP;

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

        JScrollPane scrollPane = Utils.wrapWithScrollPane(shapeList);

        add(titlePanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
}
