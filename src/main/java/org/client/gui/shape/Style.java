package org.client.gui.shape;

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
    private static final long serialVersionUID = 2L;

    public Style(Style toCopy) {
        this(
                toCopy.lineWidth,
                toCopy.lineColor,
                toCopy.fillColor,
                toCopy.textSize,
                toCopy.textColor,
                toCopy.textContent
        );
    }
}
