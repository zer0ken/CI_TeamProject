package org.gui.components.labeledslot;

import javax.swing.*;
import java.awt.*;

import static org.gui.components._Constants.STYLE_DEFAULT_TEXT_CONTENT;

public class TextInput extends LabeledSlot {
    JTextField textField;

    public TextInput(String label) {
        super(label);
        textField = new JTextField(STYLE_DEFAULT_TEXT_CONTENT);
        addSlot(textField, BorderLayout.SOUTH);
    }

    public String getValue() {
        return textField.getText().trim();
    }

    public void setValue(String value) {
        textField.setText(value);
    }
}
