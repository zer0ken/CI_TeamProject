package org.example.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import static org.example.components._Constants.*;

import org.example.shapes.Style;
import org.example.shapes.Shape;
import org.example.shapes.Line;
import org.example.shapes.Rectangle;
import org.example.shapes.Oval;
import org.example.shapes.Text;

public class Toolbar extends _ComponentJPanel {
    private JLabel titleLabel;
    private Canvas canvas;
    private StyleWindow styleWindow;

    Toolbar(Canvas canvas, StyleWindow styleWindow) {
        this();
        this.canvas = canvas;
        this.styleWindow = styleWindow;
    }

    Toolbar() {
        super(TOOLBAR_SIZE);
        setLayout(new FlowLayout(FlowLayout.LEFT));

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
                canvas.addShape(currentTShape);
                canvas.repaint();
            }
        }
    }
}
