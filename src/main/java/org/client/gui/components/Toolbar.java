package org.client.gui.components;

import org.client.gui.models.ToolbarMouseAdapter;

import javax.swing.*;
import java.awt.*;

import static org.client.gui.Constants.*;

public class Toolbar extends ComponentJPanel {
    public Toolbar() {
        super(TOOLBAR_SIZE);
        setLayout(new BorderLayout());

        JToolBar toolBar = new JToolBar(TOOLBAR_TITLE);

        // Toolbar button setting
        for (int i = 0; i < TOOLBAR_BUTTONS.length; i++) {
            String buttonName = TOOLBAR_BUTTONS[i];
            JButton button = new JButton(buttonName);
            button.setFocusPainted(false);
            button.addMouseListener(new ToolbarMouseAdapter());
            button.setToolTipText(buttonName + "\n" + TOOLBAR_TOOL_TIPS[i]);
            toolBar.add(button);
        }

        add(toolBar, BorderLayout.CENTER);
    }
}
