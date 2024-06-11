package org.client.gui.shape;

import org.client.gui.Utils;

import java.awt.*;
import java.io.Serial;
import java.io.Serializable;

import static org.client.gui.Constants.*;

public class Shape implements Serializable, Comparable<Shape>, Drawable, Interactable {
    @Serial
    private static final long serialVersionUID = 2L;

    private Type type;

    public int x1, y1, x2, y2;
    private Style style;

    private String author;
    private long timestamp;

    public Shape(Type type, Style style, int x1, int y1, int x2, int y2, String author, long timestamp) {
        this.type = type;
        this.style = new Style(style);
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.author = author;
        this.timestamp = timestamp;
    }

    public Shape(Type type, Style style, int x1, int y1, String author, long timestamp) {
        this(
                type, style, x1, y1,
                type == Type.TEXT ? x1 + 100 : x1 + 1,
                type == Type.TEXT ? y1 + 100 : y1 + 1,
                author, timestamp);
    }

    public Shape(Shape toCopy) {
        this(toCopy.type, toCopy.style, toCopy.x1, toCopy.y1, toCopy.x2, toCopy.y2, toCopy.author, toCopy.timestamp);
    }

    public Shape(Shape toCopy, Style style) {
        this(toCopy.type, style, toCopy.x1, toCopy.y1, toCopy.x2, toCopy.y2, toCopy.author, toCopy.timestamp);
    }

    public Shape(Shape toCopy, int dx, int dy) {
        this(toCopy.type, toCopy.style, toCopy.x1 + dx, toCopy.y1 + dy, toCopy.x2 + dx, toCopy.y2 + dy,
                toCopy.author, toCopy.timestamp);
    }

    public Shape(Shape toCopy, int x1, int y1, int x2, int y2) {
        this(toCopy.type, toCopy.style, x1, y1, x2, y2, toCopy.author, toCopy.timestamp);
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        switch (type) {
            case RECTANGLE -> {
                g2d.setPaint(style.fillColor());
                g2d.fillRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
                g2d.setColor(style.lineColor());
                g2d.setStroke(new BasicStroke(style.lineWidth()));
                g2d.drawRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
            }
            case OVAL -> {
                g2d.setPaint(style.fillColor());
                g2d.fillOval(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
                g2d.setColor(style.lineColor());
                g2d.setStroke(new BasicStroke(style.lineWidth()));
                g2d.drawOval(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
            }
            case LINE -> {
                g2d.setStroke(new BasicStroke(style.lineWidth()));
                g2d.setColor(style.lineColor());
                g2d.drawLine(x1, y1, x2, y2);
            }
            case TEXT -> {
                Font font = new Font(null, Font.PLAIN, style.textSize());
                g.setColor(style.textColor());
                g.setFont(font);

                FontMetrics metrics = g.getFontMetrics(font);
                int textWidth = metrics.stringWidth(style.textContent());
                int textHeight = metrics.getHeight();

                int centerX = Math.min(x1, x2) + (Math.max(x1, x2) - Math.min(x1, x2)) / 2;
                int centerY = Math.min(y1, y2) + (Math.max(y1, y2) - Math.min(y1, y2)) / 2;

                g.drawString(style.textContent(), centerX - textWidth / 2, centerY + textHeight / 2);
            }
        }
    }


    public Style getStyle() {               // 도형의 스타일 리턴
        return style;
    }

    public String getId() {
        return timestamp + "-" + author;
    }

    public int getX1() {
        return Math.min(x1, x2);
    }

    public int getX2() {
        return Math.max(x1, x2);
    }

    public int getY1() {
        return Math.min(y1, y2);
    }

    public int getY2() {
        return Math.max(y1, y2);
    }

    public Shape derive(int x1, int y1, int x2, int y2) {
        return new Shape(this, x1, y1, x2, y2);
    }

    public int getWidth() {
        return Math.abs(x2 - x1);
    }

    public int getHeight() {
        return Math.abs(y2 - y1);
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return "<html><b>" + switch (type) {
            case LINE -> LINE;
            case OVAL -> OVAL;
            case RECTANGLE -> RECT;
            case TEXT -> "\"" + getStyle().textContent() + "\"";
        } + "</b> ── <i>" + Utils.formatTime(timestamp) + ", " + author + " 작성</i></html>";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Shape)
            return ((Shape) obj).getId().equals(getId());
        return false;
    }

    @Override
    public int compareTo(Shape o) {
        int diff = (int) (timestamp - o.timestamp);
        if (diff != 0) {
            return diff;
        }
        return author.compareTo(o.author);
    }

    @Override
    public boolean contains(Point point) {
        return getX1() - 3 <= point.x && point.x <= getX2() + 3
                && getY1() - 3 <= point.y && point.y <= getY2() + 3;
    }

    public enum Type {
        RECTANGLE, OVAL, LINE, TEXT
    }
}
