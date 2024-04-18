package org.example.shapes;

import java.awt.*;

public class Text extends Shape {
  public TextHandle northHandle, southHandle, eastHandle, westHandle;
  public TextHandle northEastHandle, northWestHandle, southEastHandle, southWestHandle;
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

  @Override
  public void drawSelection(Graphics g) {
    if (x1 < x2 && y1 < y2) {
      g.drawString("Text", x1, y2);
    } else if (x2 < x1 && y1 < y2) {
      g.drawString("Text", x2, y2);
    } else if (x1 < x2 && y2 < y1) {
      g.drawString("Text", x1, y1);
    } else if (x2 < x1 && y2 < y1) {
      g.drawString("Text", x2, y1);
    }
    northHandle.drawHandle(g);
    southHandle.drawHandle(g);
    eastHandle.drawHandle(g);
    westHandle.drawHandle(g);
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
  public void allHandleStopDrag() {
    northHandle.stopDragging();
    southHandle.stopDragging();
    eastHandle.stopDragging();
    westHandle.stopDragging();
    northEastHandle.stopDragging();
    northWestHandle.stopDragging();
    southEastHandle.stopDragging();
    southWestHandle.stopDragging();
  }

  @Override
  public void fineAndStartDrag(Point p){
    if (northHandle.contains(p)){
      northHandle.startDragging(p);
    } else if (southHandle.contains(p)) {
      southHandle.startDragging(p);
    } else if (eastHandle.contains(p)) {
      eastHandle.startDragging(p);
    } else if (westHandle.contains(p)) {
      westHandle.startDragging(p);
    } else if (northEastHandle.contains(p)) {
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
    if (northHandle.isDragging()) {
      northHandle.drag(p);
    } else if (southHandle.isDragging()) {
      southHandle.drag(p);
    } else if (eastHandle.isDragging()) {
      eastHandle.drag(p);
    } else if (westHandle.isDragging()) {
      westHandle.drag(p);
    } else if (northEastHandle.isDragging()) {
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

  public class TextHandle extends Handle {

    public TextHandle(int x, int y, boolean isDiagonalHandle, String direction) {
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
          case "west" -> setTextX1(x);
          case "east" -> setTextX2(x);
          case "north" -> setTextY1(y);
          case "south" -> setTextY2(y);
        }
      } else {
        switch (direction) {
          case "northWest" -> setTextX1Y1(x, y);
          case "southEast" -> setTextX2Y2(x, y);
          case "northEast" -> setTextX2Y1(x, y);
          case "southWest" -> setTextX1Y2(x, y);
        }
      }
    }
  }
}
