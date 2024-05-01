package org.client.gui.components;

import org.client.gui.models.StyleWindowModel;

import static org.client.gui.Constants.*;

public class StyleWindow extends DefaultStyleWindow {
    public StyleWindow() {
        super(STYLE_WINDOW_SIZE, STYLE_WINDOW_TITLE);
        setModel(new StyleWindowModel());
    }
}
