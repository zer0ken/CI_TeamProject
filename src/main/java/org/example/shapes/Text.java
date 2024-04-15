package org.example.shapes;

import java.awt.*;

public class Text extends _Shape {
  private final String textContent;

  public Text(Point startPoint, String textContent, Style style) {
    super(startPoint, style);
    this.textContent = textContent;
  }

  public String getTextContent() {
    return textContent;
  }
}
