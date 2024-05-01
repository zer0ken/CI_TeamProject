package org.client.gui.models;

import javax.swing.*;

abstract public class DefaultStyleWindowModel {
    protected final AppViewModel appViewModel;

    protected JSpinner lineWidthSpinner;
    protected JButton lineColorButton;
    protected JButton fillColorButton;
    protected JSpinner textSizeSpinner;
    protected JButton textColorButton;
    protected JTextField textContentField;

    public DefaultStyleWindowModel() {
        this.appViewModel = AppViewModel.getInstance();
    }

    private void setComponents(JSpinner lineWidthSpinner, JButton lineColorButton, JButton fillColorButton,
                               JSpinner textSizeSpinner, JButton textColorButton, JTextField textContentField) {
        this.lineWidthSpinner = lineWidthSpinner;
        this.lineColorButton = lineColorButton;
        this.fillColorButton = fillColorButton;
        this.textSizeSpinner = textSizeSpinner;
        this.textColorButton = textColorButton;
        this.textContentField = textContentField;
    }

    public void bind(JSpinner lineWidthSpinner, JButton lineColorButton, JButton fillColorButton,
                     JSpinner textSizeSpinner, JButton textColorButton, JTextField textContentField) {
        setComponents(lineWidthSpinner, lineColorButton, fillColorButton, textSizeSpinner, textColorButton, textContentField);
        setLineWidthSpinnerModel();
        setLineColorButtonModel();
        setFillColorButtonModel();
        setTextSizeSpinnerModel();
        setTextColorButtonModel();
        setTextContentFieldModel();
    }

    abstract void setLineWidthSpinnerModel();

    abstract void setLineColorButtonModel();

    abstract void setFillColorButtonModel();

    abstract void setTextSizeSpinnerModel();

    abstract void setTextColorButtonModel();

    abstract void setTextContentFieldModel();
}
