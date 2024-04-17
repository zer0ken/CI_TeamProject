package org.example.shapes;

import java.awt.*;

public class Line implements Shape {
  private int x1, y1, x2, y2;
  public LineHandle startHandle, endHandle;

  public Line() {
    startHandle = new LineHandle(x1, y1, true);
    endHandle = new LineHandle(x2, y2, false);
  }

  public void setLocation(int x1, int y1, int x2, int y2) {
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
    startHandle.setLocation(x1, y1);
    endHandle.setLocation(x2, y2);
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


  public void draw(Graphics g) {
    g.setColor(Color.BLACK);
    g.drawLine(x1, y1, x2, y2);
  }

  public void drawSelection(Graphics g) {
    g.drawLine(x1, y1, x2, y2);
    startHandle.draw(g);
    endHandle.draw(g);
  }

  public boolean contains(Point p) {
    double m = (double) (y2 - y1) / (x2 - x1);
    double y = m * (p.x - x1) + y1;
    return Math.abs(p.y - y) < 3;
  }

  public void move(int dx, int dy) {
    x1 += dx;
    y1 += dy;
    x2 += dx;
    y2 += dy;
    startHandle.setLocation(x1, y1);
    endHandle.setLocation(x2, y2);
  }

  public class LineHandle {
    private int x, y;
    private boolean isStartHandle;
    private boolean isDragging;
    private int offsetX, offsetY;
    private static final int HANDLE_SIZE = 6;

    public LineHandle(int x, int y, boolean isStartHandle) {
      this.x = x;
      this.y = y;
      this.isStartHandle = isStartHandle;
    }

    public void draw(Graphics g) {
      g.setColor(Color.BLACK);
      g.fillRect(x - HANDLE_SIZE / 2, y - HANDLE_SIZE / 2, HANDLE_SIZE, HANDLE_SIZE);
    }

    public boolean contains(Point p) {
      return p.x >= x - HANDLE_SIZE / 2 && p.x <= x + HANDLE_SIZE / 2 &&
          p.y >= y - HANDLE_SIZE / 2 && p.y <= y + HANDLE_SIZE / 2;
    }

    public void setLocation(int x, int y) {
      this.x = x;
      this.y = y;
    }

    public void startDragging(Point mousePoint) {
      isDragging = true;
      offsetX = mousePoint.x - x;
      offsetY = mousePoint.y - y;
    }

    public void stopDragging() {
      isDragging = false;
    }

    public boolean isDragging() {
      return isDragging;
    }


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
