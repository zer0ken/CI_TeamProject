package org.example.shapes;

import java.awt.*;

public class Text implements Shape {
  private int x1, y1, x2, y2;
  private TextHandle northHandle, southHandle, eastHandle, westHandle;
  private TextHandle northEastHandle, northWestHandle, southEastHandle, southWestHandle;
  private int textWidth, textHeight;


  public Text() {
    northHandle = new TextHandle((x1 + x2) / 2, y1, false, "north");
    southHandle = new TextHandle((x1 + x2) / 2, y2, false, "south");
    eastHandle = new TextHandle(x2, (y1 + y2) / 2, false, "east");
    westHandle = new TextHandle(x1, (y1 + y2) / 2, false, "west");
    northEastHandle = new TextHandle(x2, y1, true, "northEast");
    northWestHandle = new TextHandle(x1, y1, true, "northWest");
    southEastHandle = new TextHandle(x2, y2, true, "southEast");
    southWestHandle = new TextHandle(x1, y2, true, "southWest");
  }

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

  public void setTextX1(int x1) {
    this.x1 = x1;
    northHandle.setLocation((x1 + x2) / 2, y1);
    southHandle.setLocation((x1 + x2) / 2, y2);
    westHandle.setLocation(x1, (y1 + y2) / 2);
    northWestHandle.setLocation(x1, y1);
    southWestHandle.setLocation(x1, y2);
  }

  public void setTextY1(int y1) {
    this.y1 = y1;
    northHandle.setLocation((x1 + x2) / 2, y1);
    eastHandle.setLocation(x2, (y1 + y2) / 2);
    westHandle.setLocation(x1, (y1 + y2) / 2);
    northEastHandle.setLocation(x2, y1);
    northWestHandle.setLocation(x1, y1);
  }

  public void setTextX2(int x2) {
    this.x2 = x2;
    northHandle.setLocation((x1 + x2) / 2, y1);
    southHandle.setLocation((x1 + x2) / 2, y2);
    eastHandle.setLocation(x2, (y1 + y2) / 2);
    northEastHandle.setLocation(x2, y1);
    southEastHandle.setLocation(x2, y2);
  }

  public void setTextY2(int y2) {
    this.y2 = y2;
    southHandle.setLocation((x1 + x2) / 2, y2);
    eastHandle.setLocation(x2, (y1 + y2) / 2);
    westHandle.setLocation(x1, (y1 + y2) / 2);
    southEastHandle.setLocation(x2, y2);
    southWestHandle.setLocation(x1, y2);
  }

  public void setTextX1Y1(int x, int y) {
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

  public void setTextX2Y2(int x, int y) {
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

  public void setTextX1Y2(int x1, int y2) {
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

  public void setTextX2Y1(int x2, int y1) {
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

  public void draw(Graphics g) {
    if (x1 < x2 && y1 < y2) {
      g.drawString("Text", x1, y2);
    } else if (x2 < x1 && y1 < y2) {
      g.drawString("Text", x2, y2);
    } else if (x1 < x2 && y2 < y1) {
      g.drawString("Text", x1, y1);
    } else if (x2 < x1 && y2 < y1) {
      g.drawString("Text", x2, y1);
    }
    FontMetrics metrics = g.getFontMetrics();
    textWidth = metrics.stringWidth("Text");
    textHeight = metrics.getHeight();
  }

  public void drawSelection(Graphics g) {
    g.setColor(Color.BLUE);
    if (x1 < x2 && y1 < y2) {
      g.drawString("Text", x1, y2);
    } else if (x2 < x1 && y1 < y2) {
      g.drawString("Text", x2, y2);
    } else if (x1 < x2 && y2 < y1) {
      g.drawString("Text", x1, y1);
    } else if (x2 < x1 && y2 < y1) {
      g.drawString("Text", x2, y1);
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

  public boolean contains(Point p) {
    return p.x >= x1 && p.x <= x2 && p.y >= y1 && p.y <= y2;
  }

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

  //TextHandle 클래스 정의
  private class TextHandle {
    private int x, y;
    private boolean isDiagonalHandle;
    private String direction;
    private boolean isDragging;
    private int offsetX, offsetY;
    private static final int HANDLE_SIZE = 6;

    public TextHandle(int x, int y, boolean isDiagonalHandle, String direction) {
      this.x = x;
      this.y = y;
      this.isDiagonalHandle = isDiagonalHandle;
      this.direction = direction;
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
      if (!isDiagonalHandle) {
        if (direction.equals("west")) {
          setTextX1(x);
        } else if (direction.equals("east")) {
          setTextX2(x);
        } else if (direction.equals("north")) {
          setTextY1(y);
        } else if (direction.equals("south")) {
          setTextY2(y);
        }
      } else {
        if (direction.equals("northWest")) {
          setTextX1Y1(x, y);
        } else if (direction.equals("southEast")) {
          setTextX2Y2(x, y);
        } else if (direction.equals("northEast")) {
          setTextX2Y1(x, y);
        } else if (direction.equals("southWest")) {
          setTextX1Y2(x, y);
        }
      }
    }
  }
}
