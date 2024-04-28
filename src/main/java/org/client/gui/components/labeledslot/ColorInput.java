package org.client.gui.components.labeledslot;

import javax.swing.*;
import java.awt.*;

public class ColorInput extends LabeledSlot {
    Color color;
    JButton lineColorButton;

    public ColorInput(String label, String chooserTitle, Color color) {
        super(label);
        this.color = color;
        lineColorButton = new JButton();
        lineColorButton.setBackground(color);

        lineColorButton.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(null, chooserTitle, color);
            if (newColor != null) {
                setColor(newColor);
            }
        });

        addSlot(lineColorButton);
    }

    public void setColor(Color color) {
        this.color = color;
        lineColorButton.setBackground(color);
    }

    public Color getValue() {
        return color;
    }
}
