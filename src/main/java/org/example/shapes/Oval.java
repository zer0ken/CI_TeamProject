package org.example.shapes;

import java.awt.*;

public class Oval extends Shape {
  public OvalHandle northEastHandle, northWestHandle, southEastHandle, southWestHandle;

  public Oval() {
    northEastHandle = new OvalHandle(x2, y1, "northEast");
    northWestHandle = new OvalHandle(x1, y1, "northWest");
    southEastHandle = new OvalHandle(x2, y2, "southEast");
    southWestHandle = new OvalHandle(x1, y2, "southWest");
  }

  public Oval(Oval other) {
    northEastHandle = new OvalHandle(other.northEastHandle);
    northWestHandle = new OvalHandle(other.northWestHandle);
    southEastHandle = new OvalHandle(other.southEastHandle);
    southWestHandle = new OvalHandle(other.southWestHandle);
  }

  @Override
  public void setLocation(int x1, int y1, int x2, int y2) {
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
    northEastHandle.setLocation(x2, y1);
    northWestHandle.setLocation(x1, y1);
    southEastHandle.setLocation(x2, y2);
    southWestHandle.setLocation(x1, y2);
  }

  @Override
  public void setStyle(Style style) {
    this.lineWidth = style.getLineWidth();
    this.lineColor = style.getLineColor();
    this.fillColor = style.getFillColor();
  }

  @Override
  public void draw(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;
    g2d.setStroke(new BasicStroke(lineWidth));
    g2d.setPaint(fillColor);
    g2d.fillOval(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
    g2d.setColor(lineColor);
    g2d.drawOval(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
  }

  @Override
  public void drawSelection(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;
    g2d.setStroke(new BasicStroke(lineWidth));
    g2d.setPaint(fillColor);
    g2d.fillOval(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
    g2d.setColor(lineColor);
    g2d.drawOval(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));

    northEastHandle.drawHandle(g);
    northWestHandle.drawHandle(g);
    southEastHandle.drawHandle(g);
    southWestHandle.drawHandle(g);
  }

  @Override
  public boolean contains(Point p) {
    return p.x >= x1 && p.x <= x2 && p.y >= y1 && p.y <= y2;
  }

  @Override
  public void move(int dx, int dy) {
    x1 += dx;
    y1 += dy;
    x2 += dx;
    y2 += dy;
    northEastHandle.setLocation(x2, y1);
    northWestHandle.setLocation(x1, y1);
    southEastHandle.setLocation(x2, y2);
    southWestHandle.setLocation(x1, y2);
  }

  @Override
  public void allHandleStopDrag() {
    northEastHandle.stopDragging();
    northWestHandle.stopDragging();
    southEastHandle.stopDragging();
    southWestHandle.stopDragging();
  }

  @Override
  public void fineAndStartDrag(Point p){
    if (northEastHandle.contains(p)) {
      northEastHandle.startDragging(p);
    } else if (northWestHandle.contains(p)) {
      northWestHandle.startDragging(p);
    } else if (southEastHandle.contains(p)) {
      southEastHandle.startDragging(p);
    } else if (southWestHandle.contains(p)) {
      southWestHandle.startDragging(p);
    }
  }

  @Override
  public void handleDrag(Point p, int dx, int dy) {
    if (northEastHandle.isDragging()) {
      northEastHandle.drag(p);
    } else if (northWestHandle.isDragging()) {
      northWestHandle.drag(p);
    } else if (southEastHandle.isDragging()) {
      southEastHandle.drag(p);
    } else if (southWestHandle.isDragging()) {
      southWestHandle.drag(p);
    } else {
      move(dx, dy);
    }
  }

  @Override
  public Shape copy() {
    return new Oval(this);
  }

  public void setOvalX1Y1(int x) {
    this.y1 = y1 - (x1 - x);
    this.x1 = x;
    northEastHandle.setLocation(x2, y1);
    northWestHandle.setLocation(x1, y1);
    southWestHandle.setLocation(x1, y2);
  }

  public void setOvalX2Y2(int x) {
    this.y2 = y2 + (x - x2);
    this.x2 = x;
    northEastHandle.setLocation(x2, y1);
    southEastHandle.setLocation(x2, y2);
    southWestHandle.setLocation(x1, y2);
  }

  public void setOvalX2Y1(int x) {
    this.y1 = y1 + (x2 - x);
    this.x2 = x;
    northWestHandle.setLocation(x1, y1);
    northEastHandle.setLocation(x2, y1);
    southEastHandle.setLocation(x2, y2);
  }

  public void setOvalX1Y2(int x) {
    this.y2 = y2 - (x - x1);
    this.x1 = x;
    northWestHandle.setLocation(x1, y1);
    southWestHandle.setLocation(x1, y2);
    southEastHandle.setLocation(x2, y2);
  }

  public class OvalHandle extends Handle {

    public OvalHandle(int x, int y, String direction) {
      this.x = x;
      this.y = y;
      this.direction = direction;
    }

    public OvalHandle(OvalHandle other) {
      this.x = other.x;
      this.y = other.y;
      this.direction = other.direction;
    }

    @Override
    public void drag(Point mousePoint) {
      x = mousePoint.x - offsetX;
      y = mousePoint.y - offsetY;
      switch (direction) {
        case "northWest" -> setOvalX1Y1(x);
        case "southEast" -> setOvalX2Y2(x);
        case "northEast" -> setOvalX2Y1(x);
        case "southWest" -> setOvalX1Y2(x);
      }
    }
  }
}
