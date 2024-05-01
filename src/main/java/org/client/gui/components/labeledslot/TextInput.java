package org.client.gui.components.labeledslot;

import javax.swing.*;
import java.awt.*;

public class TextInput extends LabeledSlot {
    JTextField textField;

    public TextInput(String label) {
        super(label);
        textField = new JTextField();
        addSlot(textField, BorderLayout.SOUTH);
    }

    public JTextField getTextField() {
        return textField;
    }
}
