package org.gui.components;

import org.gui.components.labeledslot.ColorInput;
import org.gui.components.labeledslot.NumberInput;
import org.gui.components.labeledslot.TextInput;
import org.gui.shapes.Style;

import javax.swing.*;

import java.awt.*;

import static org.gui.components._Constants.*;

public class StyleWindow extends _ComponentJPanel {
    private final NumberInput lineWidth;
    private final ColorInput lineColor;
    private final ColorInput fillColor;
    private final NumberInput textSize;
    private final ColorInput textColor;
    private final TextInput textContent;

    StyleWindow() {
        super(STYLE_WINDOW_SIZE);
        setBorder(BorderFactory.createTitledBorder(STYLE_WINDOW_TITLE));
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
