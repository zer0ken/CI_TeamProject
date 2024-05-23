package org.client.gui.shapes;

import java.awt.*;
import java.io.Serial;
import java.io.Serializable;

public class Oval extends Shape implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;
  public OvalHandle northHandle, southHandle, eastHandle, westHandle;
  public OvalHandle northEastHandle, northWestHandle, southEastHandle, southWestHandle;

  public Oval() {
    northWestHandle = new OvalHandle(x1, y1, true, "northWest");
    southEastHandle = new OvalHandle(x2, y2, true, "southEast");
    northEastHandle = new OvalHandle(x2, y1, true, "northEast");
    southWestHandle = new OvalHandle(x1, y2, true, "southWest");
    northHandle = new OvalHandle((x1 + x2) / 2, y1, false, "north");
    southHandle = new OvalHandle((x1 + x2) / 2, y2, false, "south");
    eastHandle = new OvalHandle(x2, (y1 + y2) / 2, false, "east");
    westHandle = new OvalHandle(x1, (y1 + y2) / 2, false, "west");
  }

  public Oval(Oval other) {
    super(other);
    northWestHandle = new OvalHandle(other.northWestHandle);
    southEastHandle = new OvalHandle(other.southEastHandle);
    northEastHandle = new OvalHandle(other.northEastHandle);
    southWestHandle = new OvalHandle(other.southWestHandle);
    northHandle = new OvalHandle(other.northHandle);
    southHandle = new OvalHandle(other.southHandle);
    eastHandle = new OvalHandle(other.eastHandle);
    westHandle = new OvalHandle(other.westHandle);
  }

  @Override
  public Oval copy() {
    return new Oval(this);
  }


  @Override
  public void setLocation(int x1, int y1, int x2, int y2) {
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
    northWestHandle.setHandleLocation(x1, y1);
    southEastHandle.setHandleLocation(x2, y2);
    northEastHandle.setHandleLocation(x2, y1);
    southWestHandle.setHandleLocation(x1, y2);
    northHandle.setHandleLocation((x1 + x2) / 2, y1);
    southHandle.setHandleLocation((x1 + x2) / 2, y2);
    eastHandle.setHandleLocation(x2, (y1 + y2) / 2);
    westHandle.setHandleLocation(x1, (y1 + y2) / 2);
  }

  @Override
  public void draw(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;
    g2d.setPaint(style.fillColor());
    g2d.fillOval(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
    g2d.setStroke(new BasicStroke(style.lineWidth()));
    g2d.setColor(style.lineColor());
    g2d.drawOval(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
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
  public boolean contains(Point p) {        // 포인트가 사각형 내에 있는 지 확인
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
  public void fineAndStartDrag(Point p) {
    if (northWestHandle.containsHandle(p)) {
      northWestHandle.startDragging(p);
    } else if (southEastHandle.containsHandle(p)) {
      southEastHandle.startDragging(p);
    } else if (northEastHandle.containsHandle(p)) {
      northEastHandle.startDragging(p);
    } else if (southWestHandle.containsHandle(p)) {
      southWestHandle.startDragging(p);
    } else if (northHandle.containsHandle(p)) {
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

  public void setOvalX1(int x) {
    setLocation(x, this.y1, this.x2, this.y2);
  }

  public void setOvalY1(int y) {
    setLocation(this.x1, y, this.x2, this.y2);
  }

  public void setOvalX2(int x) {
    setLocation(this.x1, this.y1, x, this.y2);
  }

  public void setOvalY2(int y) {
    setLocation(this.x1, this.y1, this.x2, y);
  }

  public void setOvalX1Y1(int x) {
    setLocation(x, this.y1 - (this.x1 - x), this.x2, this.y2);
  }

  public void setOvalX2Y2(int x) {
    setLocation(this.x1, this.y1, x, this.y2 + (x - this.x2));
  }

  public void setOvalX2Y1(int x) {
    setLocation(this.x1, this.y1 - (x - this.x2), x, this.y2);
  }

  public void setOvalX1Y2(int x) {
    setLocation(x, this.y1, this.x2, this.y2 + (this.x1 - x));
  }


  public class OvalHandle extends Handle implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public OvalHandle(int x, int y, boolean isDiagonalHandle, String direction) {
      this.x = x;
      this.y = y;
      this.isDiagonalHandle = isDiagonalHandle;
      this.direction = direction;
    }

    public OvalHandle(OvalHandle other) {
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
          case "north" -> setOvalY1(y);
          case "south" -> setOvalY2(y);
          case "west" -> setOvalX1(x);
          case "east" -> setOvalX2(x);
        }
      } else {
        switch (direction) {
          case "northWest" -> setOvalX1Y1(x);
          case "southEast" -> setOvalX2Y2(x);
          case "northEast" -> setOvalX2Y1(x);
          case "southWest" -> setOvalX1Y2(x);
        }
      }
    }
  }
}
