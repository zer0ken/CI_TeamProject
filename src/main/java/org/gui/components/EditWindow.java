package org.gui.components;

import org.gui.ShapesViewModel;
import org.gui.components.labeledslot.ColorInput;
import org.gui.components.labeledslot.NumberInput;
import org.gui.components.labeledslot.TextInput;
import org.gui.shapes.Shape;
import org.gui.shapes.Style;

import javax.swing.*;
import java.awt.*;

import static org.gui.components._Constants.*;

public class EditWindow extends _ComponentJPanel {
    private final NumberInput lineWidth;
    private final ColorInput lineColor;
    private final ColorInput fillColor;
    private final NumberInput textSize;
    private final ColorInput textColor;
    private final TextInput textContent;
    private final JButton applyButton;

    public EditWindow(ShapesViewModel shapesViewModel) {
        super(EDIT_WINDOW_SIZE, shapesViewModel);
        setBorder(BorderFactory.createTitledBorder(EDIT_WINDOW_TITLE));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        lineWidth = new NumberInput(STYLE_LABELS[0], 1,
                new SpinnerNumberModel(1, 0, null, 1));
        lineColor = new ColorInput(STYLE_LABELS[1], STYLE_LINE_COLOR_TITLE, Color.BLACK);
        fillColor = new ColorInput(STYLE_LABELS[2], STYLE_FILL_COLOR_TITLE, Color.WHITE);
        textSize = new NumberInput(STYLE_LABELS[3], 12,
                new SpinnerNumberModel(12, 0, null, 1));
        textColor = new ColorInput(STYLE_LABELS[4], STYLE_TEXT_COLOR_TITLE, Color.BLACK);
        textContent = new TextInput(STYLE_LABELS[5]);
        applyButton = new JButton(EDIT_APPLY_BUTTON);

        applyButton.setPreferredSize(new Dimension(STYLE_SLOT_WIDTH, STYLE_ITEM_HEIGHT));
        applyButton.addActionListener(e -> {
            editStyle();
        });

        add(Box.createRigidArea(new Dimension(0, H_SPACE)));

        add(lineWidth);
        add(lineColor);
        add(fillColor);
        add(textSize);
        add(textColor);
        add(textContent);
        add(new Resized(applyButton));

        shapesViewModel.addListener(ShapesViewModel.Listener.SELECTION, this::select);
    }

    private Void select(Shape shape) {
        if (shape == null) {
            return null;
        }

        Style style = shape.getStyle();
        lineWidth.setValue(style.getLineWidth());
        lineColor.setColor(style.getLineColor());
        fillColor.setColor(style.getFillColor());
        textSize.setValue(style.getTextSize());
        textColor.setColor(style.getTextColor());
        textContent.setValue(style.getTextContent());
        return null;
    }

    private void editStyle() {
        Shape before = shapesViewModel.getSelectedShape();
        Shape after = before.copy(getStyle());
        shapesViewModel.modifyByUser(after.getId(), after);
    }

    private Style getStyle() {
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

class Resized extends JPanel {
    Resized(JButton button) {
        setLayout(new BorderLayout());
        setMaximumSize(STYLE_ITEM_SIZE);
        setBorder(STYLE_ITEM_BORDER);

        button.setPreferredSize(new Dimension(STYLE_SLOT_WIDTH, STYLE_ITEM_HEIGHT));
        add(BorderLayout.CENTER, button);
    }
}
