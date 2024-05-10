package org.client.gui.shapes;

import java.awt.*;
import java.io.Serial;
import java.io.Serializable;

public class Rectangle extends Shape implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  public RectHandle northHandle, southHandle, eastHandle, westHandle;
  public RectHandle northEastHandle, northWestHandle, southEastHandle, southWestHandle;

  public Rectangle() {
    northWestHandle = new RectHandle(x1, y1, true, "northWest");
    southEastHandle = new RectHandle(x2, y2, true, "southEast");
    northEastHandle = new RectHandle(x1, y2, true, "northEast");
    southWestHandle = new RectHandle(x2, y1, true, "southWest");
    northHandle = new RectHandle(x1, (y1 + y2) / 2, false, "north");
    southHandle = new RectHandle(x2, (y1 + y2) / 2, false, "south");
    eastHandle = new RectHandle((x1 + x2) / 2, y2, false, "east");
    westHandle = new RectHandle((x1 + x2) / 2, y1, false, "west");
  }

  public Rectangle(Rectangle other) {
    super(other);
    northWestHandle = new RectHandle(other.northWestHandle);
    southEastHandle = new RectHandle(other.southEastHandle);
    northEastHandle = new RectHandle(other.northEastHandle);
    southWestHandle = new RectHandle(other.southWestHandle);
    northHandle = new RectHandle(other.northHandle);
    southHandle = new RectHandle(other.southHandle);
    eastHandle = new RectHandle(other.eastHandle);
    westHandle = new RectHandle(other.westHandle);
  }

  @Override
  public Rectangle copy() {
    return new Rectangle(this);
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
    Graphics2D g2d = (Graphics2D) g;
    g2d.setPaint(style.fillColor());
    g2d.fillRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
    g2d.setStroke(new BasicStroke(style.lineWidth()));
    g2d.setColor(style.lineColor());
    g2d.drawRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
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
    northWestHandle.stopDragging();
    southEastHandle.stopDragging();
    northEastHandle.stopDragging();
    southWestHandle.stopDragging();
    northHandle.stopDragging();
    southHandle.stopDragging();
    eastHandle.stopDragging();
    westHandle.stopDragging();
  }

  @Override
  public void fineAndStartDrag(Point p){
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


  public class RectHandle extends Handle implements Serializable {
    private static final long serialVersionUID = 1L;
    public RectHandle(int x, int y, boolean isDiagonalHandle, String direction) {
      this.x = x;
      this.y = y;
      this.isDiagonalHandle = isDiagonalHandle;
      this.direction = direction;
    }

    public RectHandle(RectHandle other) {
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
