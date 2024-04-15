package org.example.shapes;

import java.awt.*;

public class Circle extends _Shape {
  private final Point endPoint;

  public Circle(Point startPoint, Point endPoint, Style style) {
    super(startPoint, style);
    this.endPoint = endPoint;
  }

  public Point getEndPoint() {
    return endPoint;
  }
}

