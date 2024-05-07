package org.client.gui.components;

import org.client.gui.Utils;
import org.client.gui.models.EditWindowController;

import javax.swing.*;
import java.awt.*;

import static org.client.gui.Constants.*;

public class EditWindow extends JPanel {
    public EditWindow() {
        setLayout(new BorderLayout());
        setPreferredSize(EDIT_WINDOW_SIZE);

        StylePanel stylePanel = new StylePanel(new EditWindowController());
        stylePanel.initVertically(EDIT_TOOLTIPS);

        GridBagConstraints constraints = stylePanel.getConstraints();
        constraints.gridx = 0;
        constraints.gridy = 7;
        constraints.gridwidth = 2;
        stylePanel.add(new JButton(APPLY_TEXT_BUTTON_CONTENT), constraints);

        add(new TitlePanel(EDIT_WINDOW_TITLE, EDIT_WINDOW_TOOLTIP), BorderLayout.NORTH);
        add(
                Utils.wrapWithVerticalScrollPane(Utils.wrapWithBorderLayout(stylePanel, BorderLayout.NORTH)),
                BorderLayout.CENTER
        );
    }
}
