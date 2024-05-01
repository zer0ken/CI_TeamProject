package org.client.gui.components.labeledslot;

import javax.swing.*;

public class ColorInput extends LabeledSlot {
    JButton colorButton;

    public ColorInput(String label) {
        super(label);
        colorButton = new JButton();
        addSlot(colorButton);
    }

    public JButton getColorButton() {
        return colorButton;
    }
}
