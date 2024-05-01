package org.client.gui.models;

import javax.swing.*;
import java.awt.*;
import java.util.function.Function;

public class StyleColorButtonModel extends DefaultButtonModel {
    private final Function<Color, Void> updateUi;
    private Color color;

    StyleColorButtonModel(Function<Color, Void> updateUi, Function<Color, Void> notify,
                          Color defaultColor, String chooserTitle) {
        this.updateUi = updateUi;
        setColor(defaultColor);
        notify.apply(defaultColor);
        addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(null, chooserTitle, color);
            if (newColor == null) {
                return;
            }
            color = newColor;
            setColor(newColor);
            notify.apply(newColor);
        });
    }

    public void setColor(Color color) {
        this.color = color;
        updateUi.apply(color);
    }

    public Color getColor() {
        return color;
    }
}
