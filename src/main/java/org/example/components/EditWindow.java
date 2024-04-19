package org.example.components;

import javax.swing.*;
import java.awt.*;

import org.example.shapes.Shape;
import org.example.shapes.Style;

import org.example.components.labeledslot.ColorInput;
import org.example.components.labeledslot.NumberInput;
import org.example.components.labeledslot.TextInput;

import static org.example.components._Constants.*;

// 편집 화면
public class EditWindow extends _ComponentJPanel {

    private NumberInput lineWidth;
    private ColorInput lineColor;
    private ColorInput fillColor;
    private NumberInput textSize;
    private ColorInput textColor;
    private TextInput textContent;

    public java.util.List<Shape> shapes;
    private Shape editCurrentShape;

    EditWindow() {
        super(EDIT_WINDOW_SIZE);

        setBorder(BorderFactory.createTitledBorder(EDIT_WINDOW_SIZE));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        lineWidth = new NumberInput(STYLE_LABELS[0], 1, new SpinnerNumberModel(10, 0, null, 1));
        lineColor = new ColorInput(STYLE_LABELS[1], STYLE_LINE_COLOR_TITLE);
        fillColor = new ColorInput(STYLE_LABELS[2], STYLE_FILL_COLOR_TITLE);
        textSize = new NumberInput(STYLE_LABELS[3], 12, new SpinnerNumberModel(10, 0, null, 1));
        textColor = new ColorInput(STYLE_LABELS[4], STYLE_TEXT_COLOR_TITLE);
        textContent = new TextInput(STYLE_LABELS[5]);

        add(Box.createRigidArea(new Dimension(0, H_SPACE)));

        add(lineWidth);
        add(lineColor);
        add(fillColor);
        add(textSize);
        add(textColor);
        add(textContent);

        // 선택된 도형의 스타일이 보여짐

        /*
        public void showStyle() {
            lineWidth = getStyle(lineWidth);
            lineColor = getStyle(lineColor);
            fillColor = getStyle(fillColor);
            textSize = getStyle(textSize);
            textColor = getStyle(textColor);
            textContent = getStyle(textContent);
        }
        */

        // 도형을 편집할 경우
        // 해당 도형의 Style을 선택
        // 기존 도형이 제거되고 수정된 도형이 Canvas에 나타남

        public void editShape(Shape shapes) {
            shapes.remove(shapes);
        }

        // 기존 도형을 제거
        // ->
        // Component 수정

        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            for (Shape shape : shapes) {
                if (shape != null) {
                    continue;
                }
            }

            if (editCurrentShape != null) {
                editCurrentShape.drawSelection(g);
                repaint();
            }
        }
        // 새로운 Style이 적용된 도형이 새로 Canvas에 그려짐

    }

    /*
    public NumberInput getStyle() {
        return new Style(
                lineWidth.getValue(),
                lineColor.getValue(),
                fillColor.getValue(),
                textSize.getValue(),
                textColor.getValue(),
                textContent.getValue()
        );
    } */
}
