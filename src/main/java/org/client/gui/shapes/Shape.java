package org.client.gui.shapes;

import org.client.gui.Utils;

import java.awt.*;
import java.io.Serial;
import java.io.Serializable;

import static org.client.gui.Constants.*;

public abstract class Shape implements Serializable, Comparable<Shape> {
    @Serial
    private static final long serialVersionUID = 1L;

    protected int x1, y1, x2, y2;
    protected Style style;

    protected String author;
    protected long timestamp;

    public Shape() {
    }

    public Shape(Shape other) {
        this.author = other.author;
        this.timestamp = other.timestamp;
        this.style = other.style;
        this.x1 = other.x1;
        this.y1 = other.y1;
        this.x2 = other.x2;
        this.y2 = other.y2;
    }

    public Style getStyle() {               // 도형의 스타일 리턴
        return style;
    }

    public void setStyle(Style style) {     // 도형의 스타일 설정
        this.style = style;
    }

    public String getId() {                   // 도형의 id 리턴
        return author + "-" + timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void move(int dx, int dy) {      // 도형 전체 이동
        setLocation(x1 + dx, y1 + dy, x2 + dx, y2 + dy);
    }

    public abstract Shape copy();

    public Shape copy(Style newStyle) {                      // 스타일 복사
        Shape copied = copy();
        copied.setStyle(newStyle);
        return copied;
    }

    public abstract void setLocation(int x1, int y1, int x2, int y2);

    public abstract void draw(Graphics g);

    public abstract void drawSelected(Graphics g);

    public abstract boolean contains(Point p);

    public abstract void allHandleStopDrag();

    public abstract void fineAndStartDrag(Point p);

    public abstract void DragOrMove(Point p, int dx, int dy);

    @Override
    public String toString() {
        return switch (getClass().getSimpleName()) {
            case "Line" -> LINE;
            case "Oval" -> OVAL;
            case "Rectangle" -> RECT;
            case "Text" -> "\"" + getStyle().textContent() + "\"";
            default -> null;
        } + "(" + Utils.formatTime(timestamp)+ ", " + author + " 작성)";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Shape)
            return ((Shape)obj).getId().equals(getId());
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
}
