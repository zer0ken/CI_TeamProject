package org.client.gui.models;

import javax.swing.*;

abstract public class StylePanelController {
    protected final AppModel appModel;

    protected JSpinner lineWidthSpinner;
    protected JButton lineColorButton;
    protected JButton fillColorButton;
    protected JSpinner textSizeSpinner;
    protected JButton textColorButton;
    protected JTextField textContentField;

    public StylePanelController() {
        this.appModel = AppModel.getInstance();
    }

    public void bind(JComponent... components) {
        this.lineWidthSpinner = (JSpinner) components[0];
        this.lineColorButton = (JButton) components[1];
        this.fillColorButton = (JButton) components[2];
        this.textSizeSpinner = (JSpinner) components[3];
        this.textColorButton = (JButton) components[4];
        this.textContentField = (JTextField) components[5];

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
