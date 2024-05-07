package org.client.gui.components;

import org.client.gui.Theme;
import org.client.gui.models.PanelVisibilityController;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;

import static org.client.gui.Constants.*;

public class WestPanel extends JPanel {
    public WestPanel() {
        setLayout(new GridBagLayout());

        StyleWindow styleWindow = new StyleWindow();
        EditWindow editWindow = new EditWindow();
        JToolBar toolbar = buildToolbar(styleWindow, editWindow);

        styleWindow.setBorder(new MatteBorder(0, 0, 1, 1, Theme.getBorderColor()));
        editWindow.setBorder(new MatteBorder(0, 0, 0, 1, Theme.getBorderColor()));

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 1;
        c.weightx = 1.0;
        c.gridwidth = 1;

        c.gridy = 0;
        c.weighty = 0.4;
        add(styleWindow, c);

        c.gridy = 1;
        c.weighty = 0.6;
        add(editWindow, c);

        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 2;
        c.weightx = 0.0;
        c.weighty = 1.0;
        add(toolbar, c);
    }

    private static JToolBar buildToolbar(StyleWindow styleWindow, EditWindow editWindow) {
        JToolBar toolbar = new VerticalToolbar(
                WEST_TOOLBAR_TOOLS,
                WEST_TOOLBAR_ICONS,
                WEST_TOOLBAR_TOOLTIPS,
                new PanelVisibilityController(
                        WEST_TOOLBAR_TOOLS,
                        styleWindow,
                        editWindow
                )
        );
        toolbar.setBorder(new CompoundBorder(
                new MatteBorder(0, 0, 0, 1, Theme.getBorderColor()),
                new EmptyBorder(0, 3, 0, 3)
        ));
        return toolbar;
    }
}
