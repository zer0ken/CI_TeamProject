package org.client.gui.components.labeledslot;

import javax.swing.*;

public class NumberInput extends LabeledSlot {
    private JSpinner numberSpinner;
    private SpinnerNumberModel model;
    private int value;

    public NumberInput(String label, int num, SpinnerNumberModel model) {
        super(label);
        this.value = num;
        this.model = model;
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

    public void setValue(int value) {
        this.value = value;
        model.setValue(value);
    }
}
