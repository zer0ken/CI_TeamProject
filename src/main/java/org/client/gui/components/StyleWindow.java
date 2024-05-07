package org.client.gui.components;

import org.client.gui.models.StyleWindowModel;

import javax.swing.*;
import java.awt.*;

import static org.client.gui.Constants.*;

public class StyleWindow extends JPanel {
    public StyleWindow() {
        setLayout(new BorderLayout());

        add(new TitlePanel(STYLE_WINDOW_TITLE, STYLE_WINDOW_TOOLTIP), BorderLayout.NORTH);
        add(new StylePanel(new StyleWindowModel(), STYLE_TOOLTIPS), BorderLayout.CENTER);
    }
}
