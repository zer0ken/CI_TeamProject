package org.client.gui.components;

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
    private long count = 0;                 // 도형 id용(임시)

    public Toolbar() {
        super(TOOLBAR_SIZE);
        setLayout(new FlowLayout(FlowLayout.LEFT));

        // Toolbar title
        JLabel titleLabel = new JLabel(TOOLBAR_TITLE);
        add(titleLabel); // Add the title label before adding buttons

        // Toolbar button setting
        for (String toolbarButton : TOOLBAR_BUTTONS) {
            JButton button = new JButton(toolbarButton);
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
                    case TOOLBAR_LINE -> clickedShape = new Line();
                    case TOOLBAR_RECT -> clickedShape = new Rectangle();
                    case TOOLBAR_OVAL -> clickedShape = new Oval();
                    case TOOLBAR_TEXT -> clickedShape = new Text();
                    default -> {
                        return;
                    }
                }
                clickedShape.setLocation(300, 300, 400, 400);
                clickedShape.setStyle(appViewModel.getStyle());
                count++;
                clickedShape.setId(count);
                appViewModel.createByUser(clickedShape);
            }
        }
    }
}
