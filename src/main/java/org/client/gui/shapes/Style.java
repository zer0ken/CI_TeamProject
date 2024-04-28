package org.client.gui.shapes;

import java.awt.*;
import java.io.Serializable;

public class Style implements Serializable {
    private static final long serialVersionUID = 1L;
    private int lineWidth;
    private Color lineColor;
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

    public Style(Style other) {
        this.lineWidth = other.getLineWidth();
        this.lineColor = other.getLineColor();
        this.fillColor = other.getFillColor();
        this.textSize = other.getTextSize();
        this.textColor = other.getTextColor();
        this.textContent = other.getTextContent();
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
