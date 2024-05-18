package org.client.gui.shapes;

import java.awt.*;
import java.io.Serial;
import java.io.Serializable;

public record Style(
        int lineWidth,
        Color lineColor,
        Color fillColor,
        int textSize,
        Color textColor,
        String textContent
) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
}
