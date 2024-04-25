package org.gui.components.labeledslot;

import javax.swing.*;
import java.awt.*;

public class ColorInput extends LabeledSlot {
    Color color = Color.BLACK;

    public ColorInput(String label, String chooserTitle) {
        super(label);
        JButton lineColorButton = new JButton();
        lineColorButton.setBackground(color);

        lineColorButton.addActionListener(e -> {
            changeColor(
                    JColorChooser.showDialog(null, chooserTitle, color)
            );
            lineColorButton.setBackground(color);
        });

        addSlot(lineColorButton);
    }

    protected void changeColor(Color color) {
        this.color = color;
    }

    public Color getValue() {
        return color;
    }
}
