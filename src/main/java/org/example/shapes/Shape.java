package org.example.shapes;

import java.awt.*;

public interface Shape {
  void setLocation(int x1, int y1, int x2, int y2);
  void draw(Graphics g);
  void drawSelection(Graphics g);
  boolean contains(Point p);
  void move(int dx, int dy);
}
