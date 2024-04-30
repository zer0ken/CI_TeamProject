package org.client.gui.components;

import org.client.gui.models.AppViewModel;
import org.client.gui.components.labeledslot.ColorInput;
import org.client.gui.components.labeledslot.NumberInput;
import org.client.gui.components.labeledslot.TextInput;
import org.client.gui.shapes.Style;

import javax.swing.*;

import java.awt.*;

import static org.client.gui.Constants.*;

public class StyleWindow extends ComponentJPanel {
    private final NumberInput lineWidth;
    private final ColorInput lineColor;
    private final ColorInput fillColor;
    private final NumberInput textSize;
    private final ColorInput textColor;
    private final TextInput textContent;

    public StyleWindow(AppViewModel appViewModel) {
        super(STYLE_WINDOW_SIZE, appViewModel);
        setBorder(BorderFactory.createTitledBorder(STYLE_WINDOW_TITLE));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        lineWidth = new NumberInput(STYLE_LABELS[0], 1,
                new SpinnerNumberModel(1, 0, null, 1));
        lineColor = new ColorInput(STYLE_LABELS[1], STYLE_LINE_COLOR_TITLE, Color.BLACK);
        fillColor = new ColorInput(STYLE_LABELS[2], STYLE_FILL_COLOR_TITLE, Color.WHITE);
        textSize = new NumberInput(STYLE_LABELS[3], 12,
                new SpinnerNumberModel(12, 0, null, 1));
        textColor = new ColorInput(STYLE_LABELS[4], STYLE_TEXT_COLOR_TITLE, Color.BLACK);
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
