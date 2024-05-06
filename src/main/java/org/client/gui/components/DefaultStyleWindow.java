package org.client.gui.components;

import org.client.gui.models.DefaultStyleWindowModel;

import static org.client.gui.Constants.STYLE_LABELS;

public class DefaultStyleWindow extends TitledVerticalJPanel {
    protected final LabeledNumberSpinner lineWidth;
    protected final LabeledColorButton lineColor;
    protected final LabeledColorButton fillColor;
    protected final LabeledNumberSpinner textSize;
    protected final LabeledColorButton textColor;
    protected final LabeledTextField textContent;

    public DefaultStyleWindow(String borderTitle) {
        super(borderTitle);

        lineWidth = new LabeledNumberSpinner(STYLE_LABELS[0]);
        lineColor = new LabeledColorButton(STYLE_LABELS[1]);
        fillColor = new LabeledColorButton(STYLE_LABELS[2]);
        textSize = new LabeledNumberSpinner(STYLE_LABELS[3]);
        textColor = new LabeledColorButton(STYLE_LABELS[4]);
        textContent = new LabeledTextField(STYLE_LABELS[5]);

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

    protected void setTooltips(String[] toolTips) {
        lineWidth.setToolTipText(toolTips[0]);
        lineColor.setToolTipText(toolTips[1]);
        fillColor.setToolTipText(toolTips[2]);
        textSize.setToolTipText(toolTips[3]);
        textColor.setToolTipText(toolTips[4]);
        textContent.setToolTipText(toolTips[5]);
    }
}
