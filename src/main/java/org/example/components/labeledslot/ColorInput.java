package org.example.components.labeledslot;

import javax.swing.*;
import java.awt.*;

public class ColorInput extends LabeledSlot {
    Color color;

    public ColorInput(String label, String chooserTitle, Color color) {
        super(label);
        this.color = color;
        JButton lineColorButton = new JButton();
        lineColorButton.setBackground(color);

        lineColorButton.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(null, chooserTitle, color);
            if (newColor != null) {
                changeColor(newColor);
                lineColorButton.setBackground(newColor);
            }
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
