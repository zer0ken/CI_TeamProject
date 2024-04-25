package org.example.shapes;

import java.awt.*;

import static org.example.components._Constants.*;

public abstract class Shape {
  protected int x1, y1, x2, y2;
  protected int lineWidth;
  protected Color lineColor;
  protected Color fillColor;
  protected int textSize;
  protected Color textColor;
  protected String textContent;
  protected long id;

  public abstract void setLocation(int x1, int y1, int x2, int y2);

  public abstract void setStyle(Style style);

  public abstract void draw(Graphics g);

  public abstract void drawSelection(Graphics g);

  public abstract boolean contains(Point p);

  public abstract void move(int dx, int dy);

  public abstract void allHandleStopDrag();

  public abstract void fineAndStartDrag(Point p);

  public abstract void handleDrag(Point p, int dx, int dy);

  public abstract Shape copy();

  public long getId() {
    return id;
  }

  public Style getStyle() {
    return null;
  }

  public Shape copy(Style style) {
    Shape copied = this.copy();
    copied.setStyle(style);
    return copied;
  }

  public String getRepresentation() {
    return switch (getClass().getName()) {
      case "Line" -> TOOLBAR_LINE;
      case "Oval" -> TOOLBAR_OVAL;
      case "Rectangle" -> TOOLBAR_RECT;
      case "Text" -> TOOLBAR_TEXT + "\"" + getStyle().getTextContent() + "\"" ;
      default -> null;
    };
  }
}
