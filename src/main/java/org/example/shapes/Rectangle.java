package org.example.shapes;

import java.awt.*;

public class Rectangle extends Shape {
  public RectHandle northHandle, southHandle, eastHandle, westHandle;
  public RectHandle northEastHandle, northWestHandle, southEastHandle, southWestHandle;

  public Rectangle() {
    northHandle = new RectHandle((x1 + x2) / 2, y1, false, "north");
    southHandle = new RectHandle((x1 + x2) / 2, y2, false, "south");
    eastHandle = new RectHandle(x2, (y1 + y2) / 2, false, "east");
    westHandle = new RectHandle(x1, (y1 + y2) / 2, false, "west");
    northEastHandle = new RectHandle(x2, y1, true, "northEast");
    northWestHandle = new RectHandle(x1, y1, true, "northWest");
    southEastHandle = new RectHandle(x2, y2, true, "southEast");
    southWestHandle = new RectHandle(x1, y2, true, "southWest");
  }

  @Override
  public void setLocation(int x1, int y1, int x2, int y2) {
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
    northHandle.setLocation((x1 + x2) / 2, y1);
    southHandle.setLocation((x1 + x2) / 2, y2);
    eastHandle.setLocation(x2, (y1 + y2) / 2);
    westHandle.setLocation(x1, (y1 + y2) / 2);
    northEastHandle.setLocation(x2, y1);
    northWestHandle.setLocation(x1, y1);
    southEastHandle.setLocation(x2, y2);
    southWestHandle.setLocation(x1, y2);
  }

  @Override
  public void draw(Graphics g) {
    if (x1 < x2 && y1 < y2) {
      g.drawRect(x1, y1, Math.abs(x2 - x1), Math.abs(y2 - y1));
    } else if (x2 < x1 && y1 < y2) {
      g.drawRect(x2, y1, Math.abs(x2 - x1), Math.abs(y2 - y1));
    } else if (x1 < x2 && y2 < y1) {
      g.drawRect(x1, y2, Math.abs(x2 - x1), Math.abs(y2 - y1));
    } else if (x2 < x1 && y2 < y1) {
      g.drawRect(x2, y2, Math.abs(x2 - x1), Math.abs(y2 - y1));
    }
  }

  @Override
  public void drawSelection(Graphics g) {
    if (x1 < x2 && y1 < y2) {
      g.drawRect(x1, y1, Math.abs(x2 - x1), Math.abs(y2 - y1));
    } else if (x2 < x1 && y1 < y2) {
      g.drawRect(x2, y1, Math.abs(x2 - x1), Math.abs(y2 - y1));
    } else if (x1 < x2 && y2 < y1) {
      g.drawRect(x1, y2, Math.abs(x2 - x1), Math.abs(y2 - y1));
    } else if (x2 < x1 && y2 < y1) {
      g.drawRect(x2, y2, Math.abs(x2 - x1), Math.abs(y2 - y1));
    }
    northHandle.draw(g);
    southHandle.draw(g);
    eastHandle.draw(g);
    westHandle.draw(g);
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
    northHandle.setLocation((x1 + x2) / 2, y1);
    southHandle.setLocation((x1 + x2) / 2, y2);
    eastHandle.setLocation(x2, (y1 + y2) / 2);
    westHandle.setLocation(x1, (y1 + y2) / 2);
    northEastHandle.setLocation(x2, y1);
    northWestHandle.setLocation(x1, y1);
    southEastHandle.setLocation(x2, y2);
    southWestHandle.setLocation(x1, y2);
  }

  public void setRectX1(int x1) {
    this.x1 = x1;
    northHandle.setLocation((x1 + x2) / 2, y1);
    southHandle.setLocation((x1 + x2) / 2, y2);
    westHandle.setLocation(x1, (y1 + y2) / 2);
    northWestHandle.setLocation(x1, y1);
    southWestHandle.setLocation(x1, y2);
  }

  public void setRectY1(int y1) {
    this.y1 = y1;
    northHandle.setLocation((x1 + x2) / 2, y1);
    eastHandle.setLocation(x2, (y1 + y2) / 2);
    westHandle.setLocation(x1, (y1 + y2) / 2);
    northEastHandle.setLocation(x2, y1);
    northWestHandle.setLocation(x1, y1);
  }

  public void setRectX2(int x2) {
    this.x2 = x2;
    northHandle.setLocation((x1 + x2) / 2, y1);
    southHandle.setLocation((x1 + x2) / 2, y2);
    eastHandle.setLocation(x2, (y1 + y2) / 2);
    northEastHandle.setLocation(x2, y1);
    southEastHandle.setLocation(x2, y2);
  }

  public void setRectY2(int y2) {
    this.y2 = y2;
    southHandle.setLocation((x1 + x2) / 2, y2);
    eastHandle.setLocation(x2, (y1 + y2) / 2);
    westHandle.setLocation(x1, (y1 + y2) / 2);
    southEastHandle.setLocation(x2, y2);
    southWestHandle.setLocation(x1, y2);
  }

  public void setRectX1Y1(int x, int y) {
    this.x1 = x;
    this.y1 = y;
    northHandle.setLocation((x1 + x2) / 2, y1);
    southHandle.setLocation((x1 + x2) / 2, y2);
    eastHandle.setLocation(x2, (y1 + y2) / 2);
    westHandle.setLocation(x1, (y1 + y2) / 2);
    northEastHandle.setLocation(x2, y1);
    northWestHandle.setLocation(x1, y1);
    southWestHandle.setLocation(x1, y2);
  }

  public void setRectX2Y2(int x, int y) {
    this.x2 = x;
    this.y2 = y;
    northHandle.setLocation((x1 + x2) / 2, y1);
    southHandle.setLocation((x1 + x2) / 2, y2);
    eastHandle.setLocation(x2, (y1 + y2) / 2);
    westHandle.setLocation(x1, (y1 + y2) / 2);
    northEastHandle.setLocation(x2, y1);
    southEastHandle.setLocation(x2, y2);
    southWestHandle.setLocation(x1, y2);
  }

  public void setRectX1Y2(int x1, int y2) {
    this.x1 = x1;
    this.y2 = y2;
    northHandle.setLocation((x1 + x2) / 2, y1);
    southHandle.setLocation((x1 + x2) / 2, y2);
    eastHandle.setLocation(x2, (y1 + y2) / 2);
    westHandle.setLocation(x1, (y1 + y2) / 2);
    northWestHandle.setLocation(x1, y1);
    southEastHandle.setLocation(x2, y2);
    southWestHandle.setLocation(x1, y2);
  }

  public void setRectX2Y1(int x2, int y1) {
    this.x2 = x2;
    this.y1 = y1;
    northHandle.setLocation((x1 + x2) / 2, y1);
    southHandle.setLocation((x1 + x2) / 2, y2);
    eastHandle.setLocation(x2, (y1 + y2) / 2);
    westHandle.setLocation(x1, (y1 + y2) / 2);
    northEastHandle.setLocation(x2, y1);
    northWestHandle.setLocation(x1, y1);
    southEastHandle.setLocation(x2, y2);
  }


  public class RectHandle extends Handle {

    // 생성자
    public RectHandle(int x, int y, boolean isDiagonalHandle, String direction) {
      this.x = x;
      this.y = y;
      this.isDiagonalHandle = isDiagonalHandle;
      this.direction = direction;
    }

    @Override
    public void drag(Point mousePoint) {
      x = mousePoint.x - offsetX;
      y = mousePoint.y - offsetY;
      if (!isDiagonalHandle) {
        switch (direction) {
          case "west" -> setRectX1(x);
          case "east" -> setRectX2(x);
          case "north" -> setRectY1(y);
          case "south" -> setRectY2(y);
        }
      } else {
        switch (direction) {
          case "northWest" -> setRectX1Y1(x, y);
          case "southEast" -> setRectX2Y2(x, y);
          case "northEast" -> setRectX2Y1(x, y);
          case "southWest" -> setRectX1Y2(x, y);
        }
      }
    }
  }
}
