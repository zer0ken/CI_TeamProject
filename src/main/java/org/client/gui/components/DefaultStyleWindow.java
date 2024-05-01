package org.client.gui.components;

import org.client.gui.components.labeledslot.ColorInput;
import org.client.gui.components.labeledslot.NumberInput;
import org.client.gui.components.labeledslot.TextInput;
import org.client.gui.models.DefaultStyleWindowModel;

import javax.swing.*;
import java.awt.*;

import static org.client.gui.Constants.*;

public class DefaultStyleWindow extends ComponentJPanel {
    protected final NumberInput lineWidth;
    protected final ColorInput lineColor;
    protected final ColorInput fillColor;
    protected final NumberInput textSize;
    protected final ColorInput textColor;
    protected final TextInput textContent;

    public DefaultStyleWindow(Dimension styleWindowSize, String borderTitle) {
        super(styleWindowSize);
        setBorder(BorderFactory.createTitledBorder(borderTitle));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        lineWidth = new NumberInput(STYLE_LABELS[0]);
        lineColor = new ColorInput(STYLE_LABELS[1]);
        fillColor = new ColorInput(STYLE_LABELS[2]);
        textSize = new NumberInput(STYLE_LABELS[3]);
        textColor = new ColorInput(STYLE_LABELS[4]);
        textContent = new TextInput(STYLE_LABELS[5]);

        add(Box.createRigidArea(new Dimension(0, H_SPACE)));

        add(lineWidth);
        add(lineColor);
        add(fillColor);
        add(textSize);
        add(textColor);
        add(textContent);
    }

    protected void setModel(DefaultStyleWindowModel model) {
        model.bind(
                lineWidth.getNumberSpinner(),
                lineColor.getColorButton(),
                fillColor.getColorButton(),
                textSize.getNumberSpinner(),
                textColor.getColorButton(),
                textContent.getTextField()
        );
    }
}
