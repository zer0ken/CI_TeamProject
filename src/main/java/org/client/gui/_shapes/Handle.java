package org.client.gui._shapes;

import java.awt.*;
import java.io.Serial;
import java.io.Serializable;

abstract class Handle implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  protected int x, y;
  protected boolean isDiagonalHandle;
  protected String direction;
  protected boolean isDragging = false;
  protected int offsetX, offsetY;
  protected static final int HANDLE_SIZE = 6;


  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public boolean isDiagonalHandle() {
    return isDiagonalHandle;
  }

  public String getDirection() {
    return direction;
  }

  public boolean getIsDragging() {
    return isDragging;
  }

  public int getOffsetX() {
    return offsetX;
  }

  public int getOffsetY() {
    return offsetY;
  }


  public void setHandleLocation(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public void drawHandle(Graphics g) {
    g.setColor(Color.BLUE);
    g.fillRect(x - HANDLE_SIZE / 2, y - HANDLE_SIZE / 2, HANDLE_SIZE, HANDLE_SIZE);
  }

  public boolean containsHandle(Point p) {
    return p.x >= x - HANDLE_SIZE / 2 && p.x <= x + HANDLE_SIZE / 2 &&
        p.y >= y - HANDLE_SIZE / 2 && p.y <= y + HANDLE_SIZE / 2;
  }

  public void startDragging(Point mousePoint){
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

  public abstract void drag(Point mousePoint);

}
