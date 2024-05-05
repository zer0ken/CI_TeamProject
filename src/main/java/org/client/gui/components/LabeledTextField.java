package org.client.gui.components;

import javax.swing.*;
import java.awt.*;

import static org.client.gui.Constants.*;

public class LabeledTextField extends JPanel {
    JTextField textField;

    public LabeledTextField(String labelText) {
        setLayout(new BorderLayout());
        setPreferredSize(Y_LABELED_PANEL_SIZE);

        JLabel label = new JLabel(labelText);
        JPanel labelWrapper = new JPanel(new BorderLayout());
        labelWrapper.setBorder(DEFAULT_COMPONENT_PADDING);
        labelWrapper.add(label, BorderLayout.NORTH);

        textField = new JTextField();
        JPanel textFieldWrapper = new JPanel(new BorderLayout());
        textFieldWrapper.setPreferredSize(Y_LABELED_COMPONENT_SIZE);
        textFieldWrapper.setBorder(DEFAULT_COMPONENT_PADDING);
        textFieldWrapper.add(textField, BorderLayout.NORTH);

        add(labelWrapper, BorderLayout.NORTH);
        add(textFieldWrapper, BorderLayout.CENTER);
    }

    public JTextField getTextField() {
        return textField;
    }
}
