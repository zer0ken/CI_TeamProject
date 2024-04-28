package org.client.gui;

import org.client.gui.shapes.Shape;
import org.client.gui.shapes.Style;

public class UserAction {
  public enum Action {
    CREATE,         // 생성
    DELETE,         // 삭제
    CHANGE,         // 이동 및 크기 변경
    STYLE_CHANGE   // 스타일 변경
  }

  // 공통 변수
  private Action action;
  private Shape targetShape, previousShape;
  private Style targetStyle, previousStyle;


  // 생성자
  public UserAction(Action action, Shape targetShape, Shape previousShape,
                    Style targetStyle, Style previousStyle) {
    this.action = action;
    this.targetShape = targetShape;
    this.previousShape = previousShape;
    this.targetStyle = targetStyle;
    this.previousStyle = previousStyle;
  }

  // get/set 메소드
  public Action getAction() {
    return action;
  }

  public void setAction(Action action) {
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


  public Style getTargetStyle() {
    return targetStyle;
  }

  public void setTargetStyle(Style targetStyle) {
    this.targetStyle = targetStyle;
  }

  public Style getPreviousStyle() {
    return previousStyle;
  }

  public void setPreviousStyle(Style previousStyle) {
    this.previousStyle = previousStyle;
  }

}
