package org.example.components;

import javax.swing.*;
import java.awt.*;

import static org.example.components._Constants.STYLE_LABELS;
import static org.example.components._Constants.STYLE_WINDOW_BOUNDS;

public class StyleWindow extends _ComponentJPanel {
    StyleWindow() {
        super(STYLE_WINDOW_BOUNDS);

        setLayout(new GridLayout(5, 2));

        for (int i = 0; i < STYLE_LABELS.length; i++) {
            JLabel label = new JLabel(STYLE_LABELS[i]);
            add(label);
        }
    }
}
