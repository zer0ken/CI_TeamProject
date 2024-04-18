package org.example.components;

import javax.swing.*;
import java.awt.*;

import org.example.shapes.Shape;
import org.example.shapes.Line;
import org.example.shapes.Rectangle;
import org.example.shapes.Oval;
import org.example.shapes.Text;
import org.example.shapes.Style;

import org.example.components.labeledslot.ColorInput;
import org.example.components.labeledslot.NumberInput;
import org.example.components.labeledslot.TextInput;

import org.example.components.StyleWindow.*;
import static org.example.components._Constants.CANVAS_SIZE;

import static org.example.components._Constants.*;

// 편집 화면
public class EditWindow extends _ComponentJPanel {

    private final NumberInput lineWidth;
    private final ColorInput lineColor;
    private final ColorInput fillColor;
    private final NumberInput textSize;
    private final ColorInput textColor;
    private final TextInput textContent;

    public java.util.List<Shape> shapes;
    private Shape editCurrentShape;

    EditWindow() {
        super(EDIT_WINDOW_SIZE);

        setBorder(BorderFactory.createTitledBorder(EDIT_WINDOW_SIZE);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        lineWidth = new NumberInput(STYLE_LABELS[0],
                new SpinnerNumberModel(10, 0, null, 1));
        lineColor = new ColorInput(STYLE_LABELS[1], STYLE_LINE_COLOR_TITLE);
        fillColor = new ColorInput(STYLE_LABELS[2], STYLE_FILL_COLOR_TITLE);

        textSize = new NumberInput(STYLE_LABELS[3],
                new SpinnerNumberModel(10, 0, null, 1));
        textColor = new ColorInput(STYLE_LABELS[4], STYLE_TEXT_COLOR_TITLE);
        textContent = new TextInput(STYLE_LABELS[5]);

        add(Box.createRigidArea(new Dimension(0, H_SPACE)));

        add(lineWidth);
        add(lineColor);
        add(fillColor);
        add(textSize);
        add(textColor);
        add(textContent);

        // 도형을 편집할 경우
        // 해당 도형의 Style을 선택
        // 기존 도형이 제거되고 수정된 도형이 Canvas에 나타남

        public void editShape(Shape shape) {
            shapes.remove(shape);
        }
        // 기존 도형을 제거하는 함수
        // ->
        // Component 수정
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (Shape shape : shapes) {
                if (shape != null) {
                    shape.draw(g);
                }
            }
            if (editCurrentShape != null) {
                editCurrentShape.drawSelection(g);
            }
        }
        // 새로운 Style이 적용된 도형이 새로 Canvas에 그려짐
    }

    public Style getStyle() {
        return new Style(
                lineWidth.getValue(),
                lineColor.getValue(),
                fillColor.getValue(),
                textSize.getValue(),
                textColor.getValue(),
                textContent.getValue()
        );
    }
}
