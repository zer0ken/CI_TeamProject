package org.client.gui.components;

import org.client.gui.models.AppViewModel;
import org.client.gui.shapes.Line;
import org.client.gui.shapes.Oval;
import org.client.gui.shapes.Rectangle;
import org.client.gui.shapes.Text;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static org.client.gui.Constants.*;

public class Toolbar extends ComponentJPanel {
    private final JLabel titleLabel;
    //private Canvas canvas;
    private final StyleWindow styleWindow;
    private long count = 0;                 // 도형 id용(임시)

    public Toolbar(StyleWindow styleWindow) {
        super(TOOLBAR_SIZE);
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
                org.client.gui.shapes.Shape clickedShape;
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
                appViewModel.createByUser(clickedShape);
//                canvas.addShape(count, selectedShape);
//                canvas.repaint();
            }
        }
    }
}
