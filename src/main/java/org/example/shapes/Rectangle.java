package org.example.shapes;

import java.awt.*;

public class Rectangle extends _Shape {
  private final Point endPoint;

  public Rectangle(Point startPoint, Point endPoint, Style style) {
    super(startPoint, style);
    this.endPoint = endPoint;
  }

  public Point getEndPoint() {
    return endPoint;
  }
}

