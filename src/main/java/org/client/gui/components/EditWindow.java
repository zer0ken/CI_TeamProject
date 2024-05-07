package org.client.gui.components;

import org.client.gui.Theme;
import org.client.gui.models.EditWindowModel;
import org.client.gui.utils.PanelUtils;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

import static org.client.gui.Constants.*;

public class EditWindow extends JPanel {
    public EditWindow() {
        setLayout(new BorderLayout());
        setBorder(new MatteBorder(1, 0, 0, 0, Theme.getBorderColor()));

        StylePanel stylePanel = new StylePanel(new EditWindowModel(), EDIT_TOOLTIPS);

        GridBagConstraints constraints = stylePanel.getConstraints();
        constraints.gridx = 0;
        constraints.gridy = 7;
        constraints.gridwidth = 2;
        stylePanel.add(new JButton(APPLY_TEXT_BUTTON_CONTENT), constraints);

        add(new TitlePanel(EDIT_WINDOW_TITLE, EDIT_WINDOW_TOOLTIP), BorderLayout.NORTH);
        add(PanelUtils.stick(stylePanel, BorderLayout.NORTH), BorderLayout.CENTER);
    }
}
