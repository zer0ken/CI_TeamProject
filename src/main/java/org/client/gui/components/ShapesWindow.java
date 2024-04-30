package org.client.gui.components;


import org.client.gui.models.AppViewModel;
import org.client.gui.shapes.Shape;

import javax.swing.*;
import java.awt.*;

import static org.client.gui.Constants.SHAPES_WINDOW_SIZE;
import static org.client.gui.Constants.SHAPES_WINDOW_TITLE;

public class ShapesWindow extends ComponentJPanel {
    JList<Shape> shapeList;

    public ShapesWindow(AppViewModel appViewModel) {
        super(SHAPES_WINDOW_SIZE, appViewModel);
        setBorder(BorderFactory.createTitledBorder(SHAPES_WINDOW_TITLE));
        setLayout(new BorderLayout());

        shapeList = new JList<>();
        shapeList.setModel(appViewModel.getShapeListModel());
        shapeList.setSelectionModel(appViewModel.getShapeListSelectionModel());

        JScrollPane scrollPane = new JScrollPane(
                shapeList,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        scrollPane.setPreferredSize(new Dimension(200, 300));

        add(BorderLayout.CENTER, scrollPane);
    }
}
