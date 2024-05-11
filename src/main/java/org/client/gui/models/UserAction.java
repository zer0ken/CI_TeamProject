package org.client.gui.models;

import org.client.gui.shapes.Shape;

public class UserAction {
  public enum Type {
    CREATE,         // 생성
    DELETE,         // 삭제
    MODIFY,         // 이동 및 크기 변경
    STYLE_MODIFY   // 스타일 변경
  }

  // 공통 변수
  private Type action;
  private Shape targetShape, previousShape;


  // 생성자
  public UserAction(Type action, Shape targetShape, Shape previousShape) {
    this.action = action;
    this.targetShape = targetShape;
    this.previousShape = previousShape;
  }

  // get/set 메소드
  public Type getAction() {
    return action;
  }

  public void setAction(Type action) {
    this.action = action;
  }


  public Shape getTargetShape() {
    return targetShape;
  }

  public void setTargetShape(Shape targetShape) {
    this.targetShape = targetShape;
  }

  public Shape getPreviousShape() {
    return previousShape;
  }

  public void setPreviousShape(Shape previousShape) {
    this.previousShape = previousShape;
  }

}
