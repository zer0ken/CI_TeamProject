package org.example.components;

import javax.swing.*;

public class LabeledTextField extends JPanel {
    LabeledTextField(String label) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        add(new JLabel(label));
        add(new JTextField());
    }
}
