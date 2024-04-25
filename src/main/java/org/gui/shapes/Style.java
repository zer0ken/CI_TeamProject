package org.gui.shapes;

import java.awt.*;

public class Style {
    private int lineWidth;
    private Color lineColor;    // TODO: Change type as need.
    private Color fillColor;
    private int textSize;
    private Color textColor;
    private String textContent;

    public Style(int lineWidth, Color lineColor, Color fillColor, int textSize, Color textColor, String textContent) {
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

    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
    }

    public Color getLineColor() {
        return lineColor;
    }

    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public Color getTextColor() {
        return textColor;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }
}
