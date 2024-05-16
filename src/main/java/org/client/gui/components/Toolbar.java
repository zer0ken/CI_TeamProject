package org.client.gui.components;

import org.client.gui.Theme;
import org.client.gui.Utils;
import org.client.gui.models.AppModel;
import org.client.gui.models.AppModel.Listener;
import org.client.gui.models.ToolbarMouseAdapter;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.client.gui.Constants.*;

public class Toolbar extends JPanel {
    private final AppModel appModel = AppModel.getInstance();
    private Map<String, JButton> buttonMap;
    public Toolbar() {
        buttonMap = new HashMap<>();
        appModel.addStackListener(Listener.UNDO_AVAILABLE, this::setUndoButtonEnabled);
        appModel.addStackListener(Listener.UNDO_UNAVAILABLE, this::setUndoButtonEnabled);
        appModel.addStackListener(Listener.REDO_AVAILABLE, this::setRedoButtonEnabled);
        appModel.addStackListener(Listener.REDO_UNAVAILABLE, this::setRedoButtonEnabled);

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

    public Void setUndoButtonEnabled(boolean enabled) {
        JButton button = buttonMap.get(TOOLBAR_UNDO);
        if (button != null) {
            button.setEnabled(enabled);
        }
        return null;
    }

    public Void setRedoButtonEnabled(boolean enabled) {
        JButton button = buttonMap.get(TOOLBAR_REDO);
        if (button != null) {
            button.setEnabled(enabled);
        }
        return null;
    }
}
