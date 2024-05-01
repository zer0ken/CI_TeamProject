package org.client.gui.components.labeledslot;

import javax.swing.*;

public class NumberInput extends LabeledSlot {
    private final JSpinner numberSpinner;

    public NumberInput(String label) {
        super(label);
        numberSpinner = new JSpinner();
        addSlot(numberSpinner);
    }

    public JSpinner getNumberSpinner() {
        return numberSpinner;
    }
}
