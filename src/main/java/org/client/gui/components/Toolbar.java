package org.client.gui.components;

import org.client.gui.Theme;
import org.client.gui.Utils;
import org.client.gui.models.ToolbarMouseAdapter;
import org.client.gui.models.ToolbarButtonController;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.client.gui.Constants.*;

public class Toolbar extends JPanel {
    private Map<String, JButton> buttonMap;
    public Toolbar() {
        buttonMap = new HashMap<>();
        ToolbarButtonController buttonController = new ToolbarButtonController(buttonMap);

        setLayout(new BorderLayout());
        setBorder(new MatteBorder(1, 0, 1, 0, Theme.getBorderColor()));

        ToolbarMouseAdapter toolbarMouseAdapter = new ToolbarMouseAdapter();

        JToolBar toolbar = new JToolBar(TOOLBAR_TITLE);

        addButtons(toolbar, TOOLBAR_ACTION_TOOLS, TOOLBAR_ACTION_TOOLTIPS, TOOLBAR_ACTION_ICONS, toolbarMouseAdapter);
        toolbar.addSeparator();
        addButtons(toolbar, TOOLBAR_SHAPE_TOOLS, TOOLBAR_SHAPE_TOOLTIPS, TOOLBAR_SHAPE_ICONS, toolbarMouseAdapter);

        Box wrapped = Box.createHorizontalBox();
        wrapped.add(Box.createGlue());
        wrapped.add(toolbar);
        wrapped.add(Box.createGlue());

        add(wrapped, BorderLayout.CENTER);
    }

    private void addButtons(JToolBar toolbar, String[] buttonNames, String[] tooltips, String[] icons,
                              ToolbarMouseAdapter adapter) {
        for (int i = 0; i < buttonNames.length; i++) {
            String buttonName = buttonNames[i];
            JButton button = new JButton(Utils.scaleIcon(getClass().getResource(icons[i]), TOOLBAR_ICON_SIZE));
            button.setName(buttonName);
            button.addMouseListener(adapter);
            button.setToolTipText(tooltips[i]);
            if (Arrays.equals(buttonNames, TOOLBAR_ACTION_TOOLS)) {
                button.setEnabled(false);
                buttonMap.put(buttonName, button);
            }
            toolbar.add(button);
        }
    }
}
