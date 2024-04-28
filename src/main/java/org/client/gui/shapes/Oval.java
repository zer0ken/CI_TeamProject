package org.client.gui.shapes;

import java.awt.*;
import java.io.Serializable;

public class Oval extends Shape implements Serializable {
  private static final long serialVersionUID = 1L;
  public OvalHandle northEastHandle, northWestHandle, southEastHandle, southWestHandle;

  public Oval() {
    northWestHandle = new OvalHandle(x1, y1, "northWest");
    southEastHandle = new OvalHandle(x2, y2, "southEast");
    northEastHandle = new OvalHandle(x1, y2, "northEast");
    southWestHandle = new OvalHandle(x2, y1, "southWest");
  }

  public Oval(Oval other) {
    northWestHandle = new OvalHandle(other.northWestHandle);
    southEastHandle = new OvalHandle(other.southEastHandle);
    northEastHandle = new OvalHandle(other.northEastHandle);
    southWestHandle = new OvalHandle(other.southWestHandle);
  }

  @Override
  public Oval copy() {
    Oval copied = new Oval(this);
    copied.setLocation(this.getX1(), this.getY1(), this.getX2(), this.getY2());
    copied.setStyle(this.getStyle());
    copied.setId(this.getId());
    return copied;
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
  }

  @Override
  public void draw(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;
    g2d.setPaint(style.getFillColor());
    g2d.fillOval(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
    g2d.setStroke(new BasicStroke(style.getLineWidth()));
    g2d.setColor(style.getLineColor());
    g2d.drawOval(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
  }

  @Override
  public void drawSelected(Graphics g) {
    draw(g);
    northWestHandle.drawHandle(g);
    southEastHandle.drawHandle(g);
    northEastHandle.drawHandle(g);
    southWestHandle.drawHandle(g);
  }

  @Override
  public boolean contains(Point p) {        // 포인트가 사각형 내에 있는 지 확인
    return p.x >= x1 && p.x <= x2 && p.y >= y1 && p.y <= y2;
  }

  @Override
  public void allHandleStopDrag() {
    northWestHandle.stopDragging();
    southEastHandle.stopDragging();
    northEastHandle.stopDragging();
    southWestHandle.stopDragging();
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
    } else {
      move(dx, dy);
    }
  }


  public void setOvalX1Y1(int x) {
    setLocation(x, this.y1 - (this.x1 - x), this.x2, this.y2);
  }

  public void setOvalX2Y2(int x) {
    setLocation(this.x1, this.y1, x, this.y2 + (x - this.x2));
  }

  public void setOvalX1Y2(int x) {
    setLocation(x, this.y1, this.x2, this.y2 - (x - this.x1));
  }

  public void setOvalX2Y1(int x) {
    setLocation(this.x1, this.y1 + (this.x2 - x), x, this.y2);
  }


  public class OvalHandle extends Handle implements Serializable {
    private static final long serialVersionUID = 1L;
    public OvalHandle(int x, int y, String direction) {
      this.x = x;
      this.y = y;
      this.direction = direction;
    }

    public OvalHandle(OvalHandle other) {
      this.x = other.getX();
      this.y = other.getY();
      this.direction = other.getDirection();
    }

    @Override
    public void drag(Point mousePoint) {
      x = mousePoint.x - offsetX;
      y = mousePoint.y - offsetY;
      switch (direction) {
        case "northWest" -> setOvalX1Y1(x);
        case "southEast" -> setOvalX2Y2(x);
        case "northEast" -> setOvalX1Y2(x);
        case "southWest" -> setOvalX2Y1(x);
      }
    }
  }
}
