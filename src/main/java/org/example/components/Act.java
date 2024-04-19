package org.example.components;

import org.example.shapes.Shape;
import org.example.shapes.Style;

public class Act {
  public enum Action {
    CREATE,
    DELETE,
    MODIFY,
    STYLE_CHANGE
  }

  private Action action;
  private Shape shapeTarget, preShape;
  private Style styleTarget;


  public Act(Action action, Shape shapeTarget, Shape preShape, Style styleTarget) {
    this.action = action;
    this.shapeTarget = shapeTarget;
    this.preShape = preShape;
    this.styleTarget = styleTarget;
  }

  public Action getAction() {
    return action;
  }

  public Shape getShapeTarget() {
    return shapeTarget;
  }

  public Shape getPreShape() {
    return preShape;
  }

  public Style getStyleTarget() {
    return styleTarget;
  }
}
