package org.client.gui.components;

import org.client.gui.models.EditWindowModel;

import javax.swing.*;
import java.awt.*;

import static org.client.gui.Constants.*;

public class EditWindow extends DefaultStyleWindow {
    public EditWindow() {
        super(EDIT_WINDOW_TITLE);
        setModel(new EditWindowModel());
        setTooltips(EDIT_TOOL_TIPS);

        JButton applyTextButton = new JButton(APPLY_TEXT_BUTTON_CONTENT);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBorder(DEFAULT_COMPONENT_PADDING);
        wrapper.setToolTipText(EDIT_TOOL_TIPS[6]);
        wrapper.add(applyTextButton, BorderLayout.NORTH);

        add(wrapper);
    }
}