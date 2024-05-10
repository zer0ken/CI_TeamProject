package org.client.gui.components;

import org.client.gui.Utils;
import org.client.gui.models.StyleWindowController;

import javax.swing.*;
import java.awt.*;

import static org.client.gui.Constants.*;

public class StyleWindow extends JPanel {
    public StyleWindow() {
        setLayout(new BorderLayout());
        setPreferredSize(STYLE_WINDOW_SIZE);

        StylePanel stylePanel = new StylePanel();
        stylePanel.composite(STYLE_TOOLTIPS);

        (new StyleWindowController()).bind(
                stylePanel.getLineWidth(),
                stylePanel.getLineColor(),
                stylePanel.getFillColor(),
                stylePanel.getTextSize(),
                stylePanel.getTextColor(),
                stylePanel.getTextContent()
        );

        add(new TitlePanel(STYLE_WINDOW_TITLE, STYLE_WINDOW_TOOLTIP), BorderLayout.NORTH);
        add(
                Utils.wrapWithVerticalScrollPane(Utils.wrapWithBorderLayout(stylePanel, BorderLayout.NORTH)),
                BorderLayout.CENTER
        );
    }
}
