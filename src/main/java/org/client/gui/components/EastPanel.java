package org.client.gui.components;

import org.client.gui.Theme;
import org.client.gui.models.PanelVisibilityController;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;

import static org.client.gui.Constants.*;

public class EastPanel extends JPanel {
    public EastPanel() {
        setLayout(new GridBagLayout());

        ClientsWindow clientsWindow = new ClientsWindow();
        ShapesWindow shapesWindow = new ShapesWindow();
        JToolBar toolbar = buildToolbar(clientsWindow, shapesWindow);
        toolbar.add(new JToolBar.Separator(), 1);

        clientsWindow.setBorder(new MatteBorder(0, 1, 1, 0, Theme.getBorderColor()));
        shapesWindow.setBorder(new MatteBorder(0, 1, 0, 0, Theme.getBorderColor()));

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.weightx = 1.0;
        c.gridwidth = 1;

        c.gridy = 0;
        c.weighty = 0.25;
        add(clientsWindow, c);

        c.gridy = 1;
        c.weighty = 0.75;
        add(shapesWindow, c);

        c.gridx = 1;
        c.gridy = 0;
        c.gridheight = 2;
        c.weightx = 0.0;
        c.weighty = 1.0;
        add(toolbar, c);
    }

    private static JToolBar buildToolbar(ClientsWindow clientsWindow, ShapesWindow shapesWindow) {
        JToolBar toolbar = new VerticalToolbar(
                EAST_TOOLBAR_TOOLS,
                EAST_TOOLBAR_ICONS,
                EAST_TOOLBAR_TOOLTIPS,
                new PanelVisibilityController(
                        EAST_TOOLBAR_TOOLS,
                        clientsWindow,
                        shapesWindow
                )
        );
        toolbar.setBorder(new CompoundBorder(
                new MatteBorder(0, 1, 0, 0, Theme.getBorderColor()),
                new EmptyBorder(0, 3, 0, 3)
        ));
        return toolbar;
    }
}
