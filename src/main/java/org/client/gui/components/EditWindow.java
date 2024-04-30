package org.client.gui.components;

import org.client.gui.models.AppViewModel.Listener;
import org.client.gui.Constants;
import org.client.gui.models.AppViewModel;
import org.client.gui.components.labeledslot.ColorInput;
import org.client.gui.components.labeledslot.TextInput;
import org.client.gui.shapes.Shape;
import org.client.gui.shapes.Style;
import org.client.gui.components.labeledslot.NumberInput;

import javax.swing.*;
import java.awt.*;

public class EditWindow extends ComponentJPanel {
    private final NumberInput lineWidth;
    private final ColorInput lineColor;
    private final ColorInput fillColor;
    private final NumberInput textSize;
    private final ColorInput textColor;
    private final TextInput textContent;
    private final JButton applyButton;

    public EditWindow(AppViewModel appViewModel) {
        super(Constants.EDIT_WINDOW_SIZE, appViewModel);
        setBorder(BorderFactory.createTitledBorder(Constants.EDIT_WINDOW_TITLE));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        lineWidth = new NumberInput(Constants.STYLE_LABELS[0], 1,
                new SpinnerNumberModel(1, 0, null, 1));
        lineColor = new ColorInput(Constants.STYLE_LABELS[1], Constants.STYLE_LINE_COLOR_TITLE, Color.BLACK);
        fillColor = new ColorInput(Constants.STYLE_LABELS[2], Constants.STYLE_FILL_COLOR_TITLE, Color.WHITE);
        textSize = new NumberInput(Constants.STYLE_LABELS[3], 12,
                new SpinnerNumberModel(12, 0, null, 1));
        textColor = new ColorInput(Constants.STYLE_LABELS[4], Constants.STYLE_TEXT_COLOR_TITLE, Color.BLACK);
        textContent = new TextInput(Constants.STYLE_LABELS[5]);
        applyButton = new JButton(Constants.EDIT_APPLY_BUTTON);

        applyButton.setPreferredSize(new Dimension(Constants.STYLE_SLOT_WIDTH, Constants.STYLE_ITEM_HEIGHT));
        applyButton.addActionListener(e -> {
            editStyle();
        });

        add(Box.createRigidArea(new Dimension(0, Constants.H_SPACE)));

        add(lineWidth);
        add(lineColor);
        add(fillColor);
        add(textSize);
        add(textColor);
        add(textContent);
        add(new Resized(applyButton));

        appViewModel.addListener(Listener.SELECTION, this::select);
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
        org.client.gui.shapes.Shape before = appViewModel.getSelectedShape();
        Shape after = before.copy(getStyle());
        appViewModel.modifyByUser(after.getId(), after);
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
        setMaximumSize(Constants.STYLE_ITEM_SIZE);
        setBorder(Constants.STYLE_ITEM_BORDER);

        button.setPreferredSize(new Dimension(Constants.STYLE_SLOT_WIDTH, Constants.STYLE_ITEM_HEIGHT));
        add(BorderLayout.CENTER, button);
    }
}
