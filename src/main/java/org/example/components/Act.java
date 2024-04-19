package org.example.components;

import org.example.shapes.Shape;
import org.example.shapes.Style;

public class Act {
  public enum Action {
    CREATE,
    DELETE,
    STYLE_CHANGE
  }

  private Action action;
  private Shape shapeTarget;
  private Style styleTarget;

  public Act(Action action, Shape shapeTarget) {
    this.action = action;
    this.shapeTarget = shapeTarget;
    this.styleTarget = null;
  }

  public Act(Action action, Shape shapeTarget, Style styleTarget) {     // 스타일 변경 시 사용 생성자
    this.action = action;
    this.shapeTarget = shapeTarget;
    this.styleTarget = styleTarget;
  }

  public Action getAction() {
    return action;
  }

  public Shape getShapeTarget() {
    return shapeTarget;
  }

  public Style getStyleTarget() {
    return styleTarget;
  }
}
