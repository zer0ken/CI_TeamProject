package org.example.components;

import org.example.ShapesViewModel;
import org.example.shapes.Rectangle;
import org.example.shapes.Shape;
import org.example.shapes.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static org.example.components._Constants.*;

public class Toolbar extends _ComponentJPanel {
    private final JLabel titleLabel;
    private final StyleWindow styleWindow;

    Toolbar(ShapesViewModel shapesViewModel, StyleWindow styleWindow) {
        super(TOOLBAR_SIZE, shapesViewModel);
        setLayout(new FlowLayout(FlowLayout.LEFT));

        this.styleWindow = styleWindow;

        // Toolbar title
        titleLabel = new JLabel(TOOLBAR_TITLE);
        add(titleLabel); // Add the title label before adding buttons

        // Toolbar button setting
        for (int i = 0; i < TOOLBAR_BUTTONS.length; i++){
            JButton button = new JButton(TOOLBAR_BUTTONS[i]);
            button.setFocusPainted(false);
            button.addMouseListener(new DoubleClickListener());
            add(button);
        }

    }

    private class DoubleClickListener extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                JButton source = (JButton) e.getSource();
                Shape currentTShape;
                Style style;
                switch (source.getText()) {
                    case TOOLBAR_LINE :
                        currentTShape = new Line();
                        break;
                    case TOOLBAR_RECT:
                        currentTShape = new Rectangle();
                        break;
                    case TOOLBAR_OVAL:
                        currentTShape = new Oval();
                        break;
                    case TOOLBAR_TEXT:
                        currentTShape = new Text();
                        break;
                    default:
                        return;
                }
                currentTShape.setLocation(300, 300, 400, 400);
                style = styleWindow.getStyle();
                currentTShape.setStyle(style);
                shapesViewModel.createByUser(currentTShape);
//                canvas.addShape(currentTShape);
//                canvas.repaint();
            }
        }
    }
}
