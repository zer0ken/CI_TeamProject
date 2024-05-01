package org.client.gui.components;

import org.client.gui.models.EditWindowModel;

import javax.swing.*;

import java.awt.*;

import static org.client.gui.Constants.*;

public class EditWindow extends DefaultStyleWindow {
    public EditWindow() {
        super(EDIT_WINDOW_SIZE, EDIT_WINDOW_TITLE);
        setModel(new EditWindowModel());
        addResized(new JButton(EDIT_APPLY));
    }

    private void addResized(JButton button) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setMaximumSize(STYLE_ITEM_SIZE);
        panel.setBorder(STYLE_ITEM_BORDER);
        button.setPreferredSize(new Dimension(STYLE_SLOT_WIDTH, STYLE_ITEM_HEIGHT));
        panel.add(BorderLayout.CENTER, button);
        add(panel);
    }
}