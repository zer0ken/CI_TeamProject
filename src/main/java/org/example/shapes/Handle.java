package org.example.shapes;

import java.awt.*;

public abstract class Handle {
  protected int x, y;
  protected boolean isDiagonalHandle;
  protected String direction;
  protected boolean isDragging;
  protected int offsetX, offsetY;
  protected static final int HANDLE_SIZE = 6;

  public void drawHandle(Graphics g) {
    g.setColor(Color.BLACK);
    g.fillRect(x - HANDLE_SIZE / 2, y - HANDLE_SIZE / 2, HANDLE_SIZE, HANDLE_SIZE);
  }
  public boolean contains(Point p) {
    // 마우스 클릭한 지점이 핸들 내에 있는지 확인
    return p.x >= x - HANDLE_SIZE / 2 && p.x <= x + HANDLE_SIZE / 2 &&
        p.y >= y - HANDLE_SIZE / 2 && p.y <= y + HANDLE_SIZE / 2;
  }
  public void setLocation(int x, int y) {
    this.x = x;
    this.y = y;
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
