package org.client.gui.shapes;

import java.awt.*;
import java.io.Serial;
import java.io.Serializable;

public class Text extends Shape implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  public TextHandle northHandle, southHandle, eastHandle, westHandle;
  public TextHandle northEastHandle, northWestHandle, southEastHandle, southWestHandle;
  private int textWidth, textHeight;
  private int centerX, centerY;

  public Text() {
    northWestHandle = new TextHandle(x1, y1, true, "northWest");
    southEastHandle = new TextHandle(x2, y2, true, "southEast");
    northEastHandle = new TextHandle(x1, y2, true, "northEast");
    southWestHandle = new TextHandle(x2, y1, true, "southWest");
    northHandle = new TextHandle(x1, (y1 + y2) / 2, false, "north");
    southHandle = new TextHandle(x2, (y1 + y2) / 2, false, "south");
    eastHandle = new TextHandle((x1 + x2) / 2, y2, false, "east");
    westHandle = new TextHandle((x1 + x2) / 2, y1, false, "west");
  }

  public Text(Text other) {
    super(other);
    this.textWidth = other.textWidth;
    this.textHeight = other.textHeight;
    this.centerX = other.centerX;
    this.centerY = other.centerY;

    northWestHandle = new TextHandle(other.northWestHandle);
    southEastHandle = new TextHandle(other.southEastHandle);
    northEastHandle = new TextHandle(other.northEastHandle);
    southWestHandle = new TextHandle(other.southWestHandle);
    northHandle = new TextHandle(other.northHandle);
    southHandle = new TextHandle(other.southHandle);
    eastHandle = new TextHandle(other.eastHandle);
    westHandle = new TextHandle(other.westHandle);
  }

  @Override
  public Text copy() {
    return new Text(this);
  }

  @Override
  public void setLocation(int x1, int y1, int x2, int y2) {
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
    northWestHandle.setHandleLocation(x1, y1);
    southEastHandle.setHandleLocation(x2, y2);
    northEastHandle.setHandleLocation(x1, y2);
    southWestHandle.setHandleLocation(x2, y1);
    northHandle.setHandleLocation(x1, (y1 + y2) / 2);
    southHandle.setHandleLocation(x2, (y1 + y2) / 2);
    eastHandle.setHandleLocation((x1 + x2) / 2, y2);
    westHandle.setHandleLocation((x1 + x2) / 2, y1);
  }

  @Override
  public void draw(Graphics g) {
    Font font = new Font(null, Font.PLAIN, style.textSize());
    g.setColor(style.textColor());
    g.setFont(font);

    FontMetrics metrics = g.getFontMetrics(font);
    textWidth = metrics.stringWidth(style.textContent());
    textHeight = metrics.getHeight();

    centerX = Math.min(x1, x2) + (Math.max(x1, x2) - Math.min(x1, x2)) / 2;
    centerY = Math.min(y1, y2) + (Math.max(y1, y2) - Math.min(y1, y2)) / 2;

    g.drawString(style.textContent(), centerX - textWidth / 2, centerY + textHeight / 2);
  }

  @Override
  public void drawSelected(Graphics g) {
    draw(g);
    northWestHandle.drawHandle(g);
    southEastHandle.drawHandle(g);
    northEastHandle.drawHandle(g);
    southWestHandle.drawHandle(g);
    northHandle.drawHandle(g);
    southHandle.drawHandle(g);
    eastHandle.drawHandle(g);
    westHandle.drawHandle(g);
  }

  @Override
  public boolean contains(Point p) {                  // 포인트가 사각형 내에 있는 지 확인
    return p.x >= (Math.min(x1, x2) - 3) && p.x <= (Math.max(x1, x2) + 3)
        && p.y >= (Math.min(y1, y2) - 3) && p.y <= (Math.max(y1, y2) + 3);
  }

  @Override
  public void allHandleStopDrag() {
    
    /*
    northWestHandle.stopDragging();
    southEastHandle.stopDragging();
    northEastHandle.stopDragging();
    southWestHandle.stopDragging();
    northHandle.stopDragging();
    southHandle.stopDragging();
    eastHandle.stopDragging();
    westHandle.stopDragging();
    */
    
  }

  @Override
  public void fineAndStartDrag(Point p){
    
    /*
    if (northWestHandle.containsHandle(p)) {
      northWestHandle.startDragging(p);
    } else if (southEastHandle.containsHandle(p)) {
      southEastHandle.startDragging(p);
    } else if (northEastHandle.containsHandle(p)) {
      northEastHandle.startDragging(p);
    } else if (southWestHandle.containsHandle(p)) {
      southWestHandle.startDragging(p);
    } else if (northHandle.containsHandle(p)){
      northHandle.startDragging(p);
    } else if (southHandle.containsHandle(p)) {
      southHandle.startDragging(p);
    } else if (eastHandle.containsHandle(p)) {
      eastHandle.startDragging(p);
    } else if (westHandle.containsHandle(p)) {
      westHandle.startDragging(p);
    }
    */
    
  }

  @Override
  public void DragOrMove(Point p, int dx, int dy) {
    if (northWestHandle.isDragging()) {
      northWestHandle.drag(p);
    } else if (southEastHandle.isDragging()) {
      southEastHandle.drag(p);
    } else if (northEastHandle.isDragging()) {
      northEastHandle.drag(p);
    } else if (southWestHandle.isDragging()) {
      southWestHandle.drag(p);
    } else if (northHandle.isDragging()) {
      northHandle.drag(p);
    } else if (southHandle.isDragging()) {
      southHandle.drag(p);
    } else if (eastHandle.isDragging()) {
      eastHandle.drag(p);
    } else if (westHandle.isDragging()) {
      westHandle.drag(p);
    } else {
      move(dx, dy);
    }
  }

  public void setRectX1(int x) {
    setLocation(x, this.y1, this.x2, this.y2);
  }

  public void setRectY1(int y) {
    setLocation(this.x1, y, this.x2, this.y2);
  }

  public void setRectX2(int x) {
    setLocation(this.x1, this.y1, x, this.y2);
  }

  public void setRectY2(int y) {
    setLocation(this.x1, this.y1, this.x2, y);
  }

  public void setRectX1Y1(int x, int y) {
    setLocation(x, y, this.x2, this.y2);
  }

  public void setRectX2Y2(int x, int y) {
    setLocation(this.x1, this.y1, x, y);
  }

  public void setRectX1Y2(int x, int y) {
    setLocation(x, this.y1, this.x2, y);
  }

  public void setRectX2Y1(int x, int y) {
    setLocation(this.x1, y, x, this.y2);
  }


  public class TextHandle extends Handle {
    @Serial
    private static final long serialVersionUID = 1L;

    public TextHandle(int x, int y, boolean isDiagonalHandle, String direction) {
      this.x = x;
      this.y = y;
      this.isDiagonalHandle = isDiagonalHandle;
      this.direction = direction;
    }

    public TextHandle(TextHandle other) {
      this.x = other.getX();
      this.y = other.getY();
      this.isDiagonalHandle = other.isDiagonalHandle();
      this.direction = other.getDirection();
    }

    @Override
    public void drag(Point mousePoint) {
      x = mousePoint.x - offsetX;
      y = mousePoint.y - offsetY;
      if (!isDiagonalHandle) {
        switch (direction) {
          case "north" -> setRectX1(x);
          case "west" -> setRectY1(y);
          case "south" -> setRectX2(x);
          case "east" -> setRectY2(y);
        }
      } else {
        switch (direction) {
          case "northWest" -> setRectX1Y1(x, y);
          case "southEast" -> setRectX2Y2(x, y);
          case "northEast" -> setRectX1Y2(x, y);
          case "southWest" -> setRectX2Y1(x, y);
        }
      }
    }
  }
}
