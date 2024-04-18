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
  public void draw(Graphics g) {
    if (x1 < x2 && y1 < y2) {
      g.drawOval(x1, y1, Math.abs(x2 - x1), Math.abs(y2 - y1));
    } else if (x2 < x1 && y1 < y2) {
      g.drawOval(x2, y1, Math.abs(x2 - x1), Math.abs(y2 - y1));
    } else if (x1 < x2 && y2 < y1) {
      g.drawOval(x1, y2, Math.abs(x2 - x1), Math.abs(y2 - y1));
    } else if (x2 < x1 && y2 < y1) {
      g.drawOval(x2, y2, Math.abs(x2 - x1), Math.abs(y2 - y1));
    }
  }

  @Override
  public void drawSelection(Graphics g) {
    if (x1 < x2 && y1 < y2) {
      g.drawOval(x1, y1, Math.abs(x2 - x1), Math.abs(y2 - y1));
    } else if (x2 < x1 && y1 < y2) {
      g.drawOval(x2, y1, Math.abs(x2 - x1), Math.abs(y2 - y1));
    } else if (x1 < x2 && y2 < y1) {
      g.drawOval(x1, y2, Math.abs(x2 - x1), Math.abs(y2 - y1));
    } else if (x2 < x1 && y2 < y1) {
      g.drawOval(x2, y2, Math.abs(x2 - x1), Math.abs(y2 - y1));
    }
    northEastHandle.draw(g);
    northWestHandle.draw(g);
    southEastHandle.draw(g);
    southWestHandle.draw(g);
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
