package org.example.components;

import javax.swing.*;

import java.awt.*;
import java.util.Objects;

import static org.example.components._Constants.*;

public class StyleWindow extends _ComponentJPanel {
    private final NumberInput lineWidth;
    private final ColorInput lineColor;
    private final ColorInput fillColor;
    private final NumberInput textSize;
    private final ColorInput textColor;
    private final TextInput textContent;

    StyleWindow() {
        super(STYLE_WINDOW_BOUNDS);
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
}

class LabeledSlot extends JPanel {
    LabeledSlot(String label) {
        setLayout(new BorderLayout());
        setMaximumSize(STYLE_ITEM_SIZE);
        setBorder(STYLE_ITEM_BORDER);

        add(BorderLayout.WEST, new JLabel(label));
    }

    protected void addSlot(Component slot, String direction) {
        slot.setPreferredSize(new Dimension(STYLE_SLOT_WIDTH, STYLE_ITEM_HEIGHT));
        add(direction, slot);

        if (!Objects.equals(direction, BorderLayout.EAST)) {
            setMaximumSize(STYLE_LARGE_ITEM_SIZE);
        }
    }

    protected void addSlot(Component slot) {
        addSlot(slot, BorderLayout.EAST);
    }
}

class NumberInput extends LabeledSlot {
    JSpinner numberSpinner;

    NumberInput(String label, SpinnerNumberModel model) {
        super(label);
        numberSpinner = new JSpinner(model);
        addSlot(numberSpinner);
    }
}

class TextInput extends LabeledSlot {
    JTextField textField;

    TextInput(String label) {
        super(label);
        textField = new JTextField(STYLE_DEFAULT_TEXT_CONTENT);
        addSlot(textField, BorderLayout.SOUTH);
    }
}

class ColorInput extends LabeledSlot {
    Color color = Color.BLACK;

    ColorInput(String label, String chooserTitle) {
        super(label);
        JButton lineColorButton = new JButton();
        lineColorButton.setBackground(color);

        lineColorButton.addActionListener(e -> {
            changeColor(
                    JColorChooser.showDialog(null, chooserTitle, color)
            );
            lineColorButton.setBackground(color);
        });

        addSlot(lineColorButton);
    }

    protected void changeColor(Color color) {
        this.color = color;
    }
}