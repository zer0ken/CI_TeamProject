package org.example.components.labeledslot;

import javax.swing.*;

public class NumberInput extends LabeledSlot {
    private JSpinner numberSpinner;

    private int value = 0;

    public NumberInput(String label, SpinnerNumberModel model) {
        super(label);
        numberSpinner = new JSpinner(model);

        numberSpinner.addChangeListener(e -> {
            try {
                value = (Integer) numberSpinner.getValue();
            } catch (ClassCastException ignored) {
            }
        });

        addSlot(numberSpinner);
    }

    public int getValue() {
        return value;
    }
}
