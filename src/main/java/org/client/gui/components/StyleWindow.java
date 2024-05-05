package org.client.gui.components;

import org.client.gui.models.StyleWindowModel;

import javax.swing.*;
import javax.swing.border.MatteBorder;

import static org.client.gui.Constants.*;

public class StyleWindow extends DefaultStyleWindow {
    public StyleWindow() {
        super(STYLE_WINDOW_SIZE, STYLE_WINDOW_TITLE);
        setModel(new StyleWindowModel());
        setTooltips(STYLE_TOOL_TIPS);

        setBorder(new MatteBorder(0, 0, 1, 1, PANEL_SEPERATOR_COLOR));

        add(Box.createVerticalGlue());
    }
}
