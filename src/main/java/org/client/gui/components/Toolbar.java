package org.client.gui.components;

import org.client.gui.Theme;
import org.client.gui.Utils;
import org.client.gui.models.ToolbarButtonController;
import org.client.gui.models.ToolbarMouseAdapter;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static org.client.gui.Constants.*;

public class Toolbar extends JPanel {

    public Toolbar() {
        Map<String, JButton> buttonMap = new HashMap<>();
        new ToolbarButtonController(buttonMap);

        setLayout(new BorderLayout());
        setBorder(new MatteBorder(1, 0, 1, 0, Theme.getBorderColor()));

        ToolbarMouseAdapter toolbarMouseAdapter = new ToolbarMouseAdapter();

        JToolBar toolbar = new JToolBar(TOOLBAR_TITLE);

        for (int i = 0; i < TOOLBAR_ACTION_TOOLS.length; i++) {
            String buttonName = TOOLBAR_ACTION_TOOLS[i];
            JButton button = new JButton(Utils.scaleIcon(getClass().getResource(TOOLBAR_ACTION_ICONS[i]), TOOLBAR_ICON_SIZE));
            button.setName(buttonName);
            button.addMouseListener(toolbarMouseAdapter);
            button.setToolTipText(TOOLBAR_ACTION_TOOLTIPS[i]);
            button.setEnabled(false);
            buttonMap.put(buttonName, button);
            toolbar.add(button);
        }

        toolbar.addSeparator();

        ButtonGroup buttonGroup = new ButtonGroup();
        for (int i = 0; i < TOOLBAR_SHAPE_TOOLS.length; i++) {
            String buttonName = TOOLBAR_SHAPE_TOOLS[i];
            JToggleButton button = new JToggleButton(Utils.scaleIcon(getClass().getResource(TOOLBAR_SHAPE_ICONS[i]), TOOLBAR_ICON_SIZE));
            button.setName(buttonName);
            button.addMouseListener(toolbarMouseAdapter);
            button.setToolTipText(TOOLBAR_SHAPE_TOOLTIPS[i]);
            toolbar.add(button);
            buttonGroup.add(button);
            if (i == 0) {
                button.setSelected(true);
            }
        }

        Box wrapped = Box.createHorizontalBox();
        wrapped.add(Box.createGlue());
        wrapped.add(toolbar);
        wrapped.add(Box.createGlue());

        add(wrapped, BorderLayout.CENTER);
    }
}
