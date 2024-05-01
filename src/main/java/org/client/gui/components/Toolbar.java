package org.client.gui.components;

import org.client.gui.models.ToolbarMouseAdapter;

import javax.swing.*;
import java.awt.*;

import static org.client.gui.Constants.*;

public class Toolbar extends ComponentJPanel {
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
            button.addMouseListener(new ToolbarMouseAdapter());
            add(button);
        }
    }
}
