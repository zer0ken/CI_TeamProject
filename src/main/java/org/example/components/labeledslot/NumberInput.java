package org.example.components.labeledslot;

import javax.swing.*;

public class NumberInput extends LabeledSlot {
    private JSpinner numberSpinner;

    private int value;

    public NumberInput(String label, int num, SpinnerNumberModel model) {
        super(label);
        this.value = num;
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
