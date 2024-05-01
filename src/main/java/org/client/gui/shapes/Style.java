package org.client.gui.shapes;

import java.awt.*;
import java.io.Serial;
import java.io.Serializable;

public class Style implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final int lineWidth;
    private final Color lineColor;
    private final Color fillColor;
    private final int textSize;
    private final Color textColor;
    private final String textContent;

    public Style(int lineWidth, Color lineColor, Color fillColor,
                 int textSize, Color textColor, String textContent) {
        this.lineWidth = lineWidth;
        this.lineColor = lineColor;
        this.fillColor = fillColor;
        this.textSize = textSize;
        this.textColor = textColor;
        this.textContent = textContent;
    }

    public int getLineWidth() {
        return lineWidth;
    }

    public Color getLineColor() {
        return lineColor;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public int getTextSize() {
        return textSize;
    }

    public Color getTextColor() {
        return textColor;
    }

    public String getTextContent() {
        return textContent;
    }
}
