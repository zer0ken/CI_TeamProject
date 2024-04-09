package org.example.components.labeledslot;

import javax.swing.*;

public class NumberInput extends LabeledSlot {
    JSpinner numberSpinner;

    public NumberInput(String label, SpinnerNumberModel model) {
        super(label);
        numberSpinner = new JSpinner(model);
        addSlot(numberSpinner);
    }
}
