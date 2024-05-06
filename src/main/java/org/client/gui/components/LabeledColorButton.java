package org.client.gui.components;

import javax.swing.*;
import java.awt.*;

import static org.client.gui.Constants.*;

public class LabeledColorButton extends JPanel {
    private final JButton colorButton;

    public LabeledColorButton(String label) {
        setLayout(new BorderLayout());
        setBorder(DEFAULT_COMPONENT_PADDING);
        setPreferredSize(X_LABELED_PANEL_SIZE);

        colorButton = new JButton();
        colorButton.setPreferredSize(X_LABELED_COMPONENT_SIZE);

        add(new JLabel(label), BorderLayout.CENTER);
        add(colorButton, BorderLayout.EAST);
    }

    public JButton getColorButton() {
        return colorButton;
    }
}
