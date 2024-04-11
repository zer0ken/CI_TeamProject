package org.example.components;

import java.awt.*;
import static org.example.components._Constants.CANVAS_SIZE;

public class Canvas extends _ComponentJPanel {
    Canvas() {
        super(CANVAS_SIZE);
        setLayout(new BorderLayout());
        setBackground(Color.white);

    }
}
