package org.client.gui.components;

import org.client.gui.ShapesViewModel;
import org.client.gui.components.labeledslot.ColorInput;
import org.client.gui.components.labeledslot.TextInput;
import org.client.gui.shapes.Shape;
import org.client.gui.shapes.Style;
import org.client.gui.components.labeledslot.NumberInput;

import javax.swing.*;
import java.awt.*;

public class EditWindow extends _ComponentJPanel {
    private final NumberInput lineWidth;
    private final ColorInput lineColor;
    private final ColorInput fillColor;
    private final NumberInput textSize;
    private final ColorInput textColor;
    private final TextInput textContent;
    private final JButton applyButton;

    public EditWindow(ShapesViewModel shapesViewModel) {
        super(_Constants.EDIT_WINDOW_SIZE, shapesViewModel);
        setBorder(BorderFactory.createTitledBorder(_Constants.EDIT_WINDOW_TITLE));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        lineWidth = new NumberInput(_Constants.STYLE_LABELS[0], 1,
                new SpinnerNumberModel(1, 0, null, 1));
        lineColor = new ColorInput(_Constants.STYLE_LABELS[1], _Constants.STYLE_LINE_COLOR_TITLE, Color.BLACK);
        fillColor = new ColorInput(_Constants.STYLE_LABELS[2], _Constants.STYLE_FILL_COLOR_TITLE, Color.WHITE);
        textSize = new NumberInput(_Constants.STYLE_LABELS[3], 12,
                new SpinnerNumberModel(12, 0, null, 1));
        textColor = new ColorInput(_Constants.STYLE_LABELS[4], _Constants.STYLE_TEXT_COLOR_TITLE, Color.BLACK);
        textContent = new TextInput(_Constants.STYLE_LABELS[5]);
        applyButton = new JButton(_Constants.EDIT_APPLY_BUTTON);

        applyButton.setPreferredSize(new Dimension(_Constants.STYLE_SLOT_WIDTH, _Constants.STYLE_ITEM_HEIGHT));
        applyButton.addActionListener(e -> {
            editStyle();
        });

        add(Box.createRigidArea(new Dimension(0, _Constants.H_SPACE)));

        add(lineWidth);
        add(lineColor);
        add(fillColor);
        add(textSize);
        add(textColor);
        add(textContent);
        add(new Resized(applyButton));

        shapesViewModel.addListener(ShapesViewModel.Listener.SELECTION, this::select);
    }

    private Void select(org.client.gui.shapes.Shape shape) {
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
        org.client.gui.shapes.Shape before = shapesViewModel.getSelectedShape();
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
        setMaximumSize(_Constants.STYLE_ITEM_SIZE);
        setBorder(_Constants.STYLE_ITEM_BORDER);

        button.setPreferredSize(new Dimension(_Constants.STYLE_SLOT_WIDTH, _Constants.STYLE_ITEM_HEIGHT));
        add(BorderLayout.CENTER, button);
    }
}
