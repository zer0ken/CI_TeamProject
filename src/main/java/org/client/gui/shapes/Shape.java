package org.client.gui.shapes;

import java.awt.*;
import java.io.Serializable;

import static org.client.gui.components._Constants.*;

public abstract class Shape implements Serializable {
  private static final long serialVersionUID = 1L;
  protected int x1, y1, x2, y2;
  protected Style style;

  protected long id;


  public int getX1() {
    return x1;
  }

  public int getY1() {
    return y1;
  }

  public int getX2() {
    return x2;
  }

  public int getY2() {
    return y2;
  }

  public Style getStyle() {               // 도형의 스타일 리턴
    return style;
  }

  public void setStyle(Style style) {     // 도형의 스타일 설정
    this.style = style;
  }

  public long getId() {                   // 도형의 id 리턴
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public void move(int dx, int dy) {      // 도형 전체 이동
    setLocation(x1 + dx, y1 + dy, x2 + dx, y2 + dy);
  }

  public abstract Shape copy();

  public Shape copy(int x1, int y1, int x2, int y2) {   // 위치 복사
    Shape copied = this.copy();
    copied.setLocation(x1, y1, x2, y2);
    return copied;
  }

  public Shape copy(Style style) {                      // 스타일 복사
    Shape copied = this.copy();
    copied.setStyle(style);
    return copied;
  }

  public Shape copy(Long id) {                          // id 복사
    Shape copied = this.copy();
    copied.setId(id);
    return copied;
  }

  public abstract void setLocation(int x1, int y1, int x2, int y2);

  public abstract void draw(Graphics g);

  public abstract void drawSelected(Graphics g);

  public abstract boolean contains(Point p);

  public abstract void allHandleStopDrag();

  public abstract void fineAndStartDrag(Point p);

  public abstract void DragOrMove(Point p, int dx, int dy);


  public String getRepresentation() {
    return switch (getClass().getSimpleName()) {
      case "Line" -> TOOLBAR_LINE;
      case "Oval" -> TOOLBAR_OVAL;
      case "Rectangle" -> TOOLBAR_RECT;
      case "Text" -> TOOLBAR_TEXT + "\"" + getStyle().getTextContent() + "\"" ;
      default -> null;
    };
  }
}
