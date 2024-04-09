package org.example.components.labeledslot;

import javax.swing.*;
import java.awt.*;

import static org.example.components._Constants.STYLE_DEFAULT_TEXT_CONTENT;

public class TextInput extends LabeledSlot {
    JTextField textField;

    public TextInput(String label) {
        super(label);
        textField = new JTextField(STYLE_DEFAULT_TEXT_CONTENT);
        addSlot(textField, BorderLayout.SOUTH);
    }
}
