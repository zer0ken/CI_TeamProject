package org.gui.components;

import org.gui.ShapesViewModel;
import org.gui.shapes.Rectangle;
import org.gui.shapes.Shape;
import org.gui.shapes.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static org.gui.components._Constants.*;

public class Toolbar extends _ComponentJPanel {
    private final JLabel titleLabel;
    //private Canvas canvas;
    private final StyleWindow styleWindow;
    private long count = 0;                 // 도형 id용(임시)

    public Toolbar(ShapesViewModel shapesViewModel, StyleWindow styleWindow) {
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
                Shape clickedShape;
                switch (source.getText()) {
                    case TOOLBAR_LINE :
                        clickedShape = new Line();
                        break;
                    case TOOLBAR_RECT:
                        clickedShape = new Rectangle();
                        break;
                    case TOOLBAR_OVAL:
                        clickedShape = new Oval();
                        break;
                    case TOOLBAR_TEXT:
                        clickedShape = new Text();
                        break;
                    default:
                        return;
                }
                clickedShape.setLocation(300, 300, 400, 400);
                clickedShape.setStyle(styleWindow.getStyle());
                count++;
                clickedShape.setId(count);
                shapesViewModel.createByUser(clickedShape);
//                canvas.addShape(count, selectedShape);
//                canvas.repaint();
            }
        }
    }
}