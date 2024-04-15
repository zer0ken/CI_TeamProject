package org.example.shapes;

import java.awt.*;
import java.util.Date;

public class _Shape {
  private long id;
  private Point startPoint;
  private Style style;

  public _Shape(Point startPoint, Style style) {
    this.id = new Date().getTime();
    this.startPoint = startPoint;
    this.style = style;
  }

  public long getId() {
    return id;
  }

  public void setId() {
    this.id = new Date().getTime();
  }

  public Point getStartPoint() {
    return startPoint;
  }

  public void setStartPoint(Point startPoint) {
    this.startPoint = startPoint;
  }

}
