package org.client.gui.components;

import org.client.gui.models.EditWindowModel;

import javax.swing.*;

import java.awt.*;

import static org.client.gui.Constants.*;

public class EditWindow extends DefaultStyleWindow {
    public EditWindow() {
        super(EDIT_WINDOW_SIZE, EDIT_WINDOW_TITLE);
        setModel(new EditWindowModel());

        add(new Resized(new JButton(EDIT_APPLY)));
    }
}

class Resized extends JPanel {
    Resized(JButton button) {
        setLayout(new BorderLayout());
        setMaximumSize(STYLE_ITEM_SIZE);
        setBorder(STYLE_ITEM_BORDER);

        button.setPreferredSize(new Dimension(STYLE_SLOT_WIDTH, STYLE_ITEM_HEIGHT));
        add(BorderLayout.CENTER, button);
    }
}