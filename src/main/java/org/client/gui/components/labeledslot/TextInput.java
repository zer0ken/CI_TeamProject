package org.client.gui.components.labeledslot;

import org.client.gui.Constants;

import javax.swing.*;
import java.awt.*;

public class TextInput extends LabeledSlot {
    JTextField textField;

    public TextInput(String label) {
        super(label);
        textField = new JTextField(Constants.STYLE_DEFAULT_TEXT_CONTENT);
        addSlot(textField, BorderLayout.SOUTH);
    }

    public String getValue() {
        return textField.getText().trim();
    }

    public void setValue(String value) {
        textField.setText(value);
    }
}
