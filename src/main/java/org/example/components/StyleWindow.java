package org.example.components;

import javax.swing.*;

import java.awt.*;

import static org.example.components._Constants.*;

public class StyleWindow extends _ComponentJPanel {
    private final NumberInput lineWidth;
    private final ColorInput lineColor;
    private final ColorInput fillColor;
    private final NumberInput textSize;
    private final ColorInput textColor;

    StyleWindow() {
        super(STYLE_WINDOW_BOUNDS);
        setBorder(BorderFactory.createTitledBorder(STYLE_WINDOW_TITLE));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentY(JPanel.TOP_ALIGNMENT);

        lineWidth = new NumberInput(STYLE_LABELS[0],
                new SpinnerNumberModel(10, 0, null, 1));
        lineColor = new ColorInput(STYLE_LABELS[1], STYLE_LINE_COLOR_TITLE);
        fillColor = new ColorInput(STYLE_LABELS[2], STYLE_FILL_COLOR_TITLE);

        textSize = new NumberInput(STYLE_LABELS[3],
                new SpinnerNumberModel(10, 0, null, 1));
        textColor = new ColorInput(STYLE_LABELS[4], STYLE_TEXT_COLOR_TITLE);

        add(Box.createRigidArea(new Dimension(0, H_SPACE)));
        add(lineWidth);
        add(lineColor);
        add(fillColor);
        add(textSize);
        add(textColor);
    }
}

class LabeledSlot extends JPanel {
    LabeledSlot(String label) {
        setLayout(new BorderLayout());
        setMaximumSize(STYLE_ITEM_SIZE);
        setBorder(STYLE_ITEM_BORDER);

        add(BorderLayout.WEST, new JLabel(label));
    }

    protected void addSlot(Component slot) {
        slot.setPreferredSize(new Dimension(STYLE_SLOT_WIDTH, STYLE_ITEM_HEIGHT));
        add(BorderLayout.EAST, slot);
    }
}

class NumberInput extends LabeledSlot {
    NumberInput(String label, SpinnerNumberModel model) {
        super(label);
        JSpinner lineWidthSpinner = new JSpinner(model);
        addSlot(lineWidthSpinner);
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