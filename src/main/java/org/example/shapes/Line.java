package org.example.shapes;

import java.awt.*;

public class Line extends Shape {
  public LineHandle startHandle, endHandle;

  public Line() {
    startHandle = new LineHandle(x1, y1, true);
    endHandle = new LineHandle(x2, y2, false);
  }

  @Override
  public void setLocation(int x1, int y1, int x2, int y2) {
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
    startHandle.setLocation(x1, y1);
    endHandle.setLocation(x2, y2);
  }

  @Override
  public void setStyle(Style style) {
    this.lineWidth = style.getLineWidth();
    this.lineColor = style.getLineColor();
  }

  @Override
  public void draw(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;
    g2d.setStroke(new BasicStroke(lineWidth));
    g2d.setColor(lineColor);
    g2d.drawLine(x1, y1, x2, y2);
  }

  @Override
  public void drawSelection(Graphics g) {
    startHandle.drawHandle(g);
    endHandle.drawHandle(g);
    Graphics2D g2d = (Graphics2D) g;
    g2d.setStroke(new BasicStroke(lineWidth));
    g2d.setColor(lineColor);
    g2d.drawLine(x1, y1, x2, y2);
  }

  @Override
  public boolean contains(Point p) {
    double m = (double) (y2 - y1) / (x2 - x1);
    double y = m * (p.x - x1) + y1;
    return Math.abs(p.y - y) < 3;
  }

  @Override
  public void move(int dx, int dy) {
    x1 += dx;
    y1 += dy;
    x2 += dx;
    y2 += dy;
    startHandle.setLocation(x1, y1);
    endHandle.setLocation(x2, y2);
  }

  @Override
  public void allHandleStopDrag() {
    startHandle.stopDragging();
    endHandle.stopDragging();
  }

  @Override
  public void fineAndStartDrag(Point p){
    if (startHandle.contains(p)){
      startHandle.startDragging(p);
    } else if (endHandle.contains(p)) {
      endHandle.startDragging(p);
    }
  }

  @Override
  public void handleDrag(Point p, int dx, int dy) {
    if (startHandle.isDragging()) {
      startHandle.drag(p);
    } else if (endHandle.isDragging()) {
      endHandle.drag(p);
    } else {
      move(dx, dy);
    }
  }

  public void setLineX1Y1(int x, int y) {
    this.x1 = x;
    this.y1 = y;
    startHandle.setLocation(x, y);
  }

  public void setLineX2Y2(int x, int y) {
    this.x2 = x;
    this.y2 = y;
    endHandle.setLocation(x, y);
  }

  public class LineHandle extends Handle {
    private boolean isStartHandle;

    public LineHandle(int x, int y, boolean isStartHandle) {
      this.x = x;
      this.y = y;
      this.isStartHandle = isStartHandle;
    }

    @Override
    public void drag(Point mousePoint) {
      x = mousePoint.x - offsetX;
      y = mousePoint.y - offsetY;
      if (isStartHandle) {
        setLineX1Y1(x, y);
      } else {
        setLineX2Y2(x, y);
      }
    }
  }
}
