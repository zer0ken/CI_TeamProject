package org.example.components;

import javax.swing.*;
import java.awt.*;

import org.example.components.labeledslot.ColorInput;
import org.example.components.labeledslot.NumberInput;
import org.example.components.labeledslot.TextInput;
import org.example.shapes.Style;
import org.example.shapes.Shape;

import static org.example.components._Constants.*;

public class EditWindow extends _ComponentJPanel {

    private final NumberInput lineWidth;
    private final ColorInput lineColor;
    private final ColorInput fillColor;
    private final NumberInput textSize;
    private final ColorInput textColor;
    private final TextInput textContent;

    private Canvas canvas;
    private Long ShapeID;

    EditWindow(Canvas canvas) {

    }
    EditWindow() {
        super(EDIT_WINDOW_SIZE);

        setBorder(BorderFactory.createTitledBorder(EDIT_WINDOW_SIZE));
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
    }

    public void showStyle(Shape shape) {
        if (shape != null) {
            lineWidth.setValue(shape.getStyle().getLineWidth());
            lineColor.setValue(shape.getStyle().getLineColor());
            fillColor.setValue(shape.getStyle().getFillColor());
            textSize.setValue(shape.getStyle().getTextSize());
            textColor.setValue(shape.getStyle().getTextColor());
            textContent.setValue(shape.getStyle().getTextContent());
        }
    }

    public void editShapeStyle(Shape shape, Style editStyle) {
        if (ShapeID != null) {
            Style editStyle = getStyle();
            canvas.// 도형 편집 메소드 (selectedShapeID, editStyle);
        }
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
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
