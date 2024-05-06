package org.client.gui.components;

import javax.swing.*;
import java.awt.*;

import static org.client.gui.Constants.*;

public class LabeledNumberSpinner extends JPanel {
    private final JSpinner numberSpinner;

    public LabeledNumberSpinner(String label) {
        setLayout(new BorderLayout());
        setBorder(DEFAULT_COMPONENT_PADDING);
        setPreferredSize(X_LABELED_PANEL_SIZE);

        numberSpinner = new JSpinner();
        numberSpinner.setPreferredSize(X_LABELED_COMPONENT_SIZE);

        add(new JLabel(label), BorderLayout.CENTER);
        add(numberSpinner, BorderLayout.EAST);
    }

    public JSpinner getNumberSpinner() {
        return numberSpinner;
    }
}
