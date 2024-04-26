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
    //private Canvas canvas;
    private final StyleWindow styleWindow;
    private long count = 0;                 // 도형 id용(임시)

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
                Shape selectedShape;
                switch (source.getText()) {
                    case TOOLBAR_LINE :
                        selectedShape = new Line();
                        break;
                    case TOOLBAR_RECT:
                        selectedShape = new Rectangle();
                        break;
                    case TOOLBAR_OVAL:
                        selectedShape = new Oval();
                        break;
                    case TOOLBAR_TEXT:
                        selectedShape = new Text();
                        break;
                    default:
                        return;
                }
                selectedShape.setLocation(300, 300, 400, 400);
                selectedShape.setStyle(styleWindow.getStyle());
                count++;
                selectedShape.setId(count);
                shapesViewModel.createByUser(selectedShape);
//                canvas.addShape(count, selectedShape);
//                canvas.repaint();
            }
        }
    }
}
