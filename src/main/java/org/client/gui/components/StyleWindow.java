package org.client.gui.components;

import org.client.gui.Theme;
import org.client.gui.models.StyleWindowModel;

import javax.swing.*;
import javax.swing.border.MatteBorder;

import static org.client.gui.Constants.*;

public class StyleWindow extends DefaultStyleWindow {
    public StyleWindow() {
        super(STYLE_WINDOW_TITLE);
        setModel(new StyleWindowModel());
        setTooltips(STYLE_TOOL_TIPS);
        setBorder(new MatteBorder(0, 0, 1, 0, Theme.getBorderColor()));

        add(Box.createVerticalGlue());
    }
}
