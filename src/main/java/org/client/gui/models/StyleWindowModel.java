package org.client.gui.models;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import static org.client.gui.Constants.*;

public class StyleWindowModel extends DefaultStyleWindowModel {
    @Override
    void setLineWidthSpinnerModel() {
        lineWidthSpinner.setModel(
                new StyleSpinnerModel(
                        lineWidth -> {
                            appModel.setLineWidth(lineWidth);
                            return null;
                        },
                        DEFAULT_LINE_WIDTH
                )
        );
    }

    @Override
    void setLineColorButtonModel() {
        lineColorButton.setModel(
                new StyleColorButtonModel(
                        lineColor -> {
                            lineColorButton.setBackground(lineColor);
                            return null;
                        },
                        lineColor -> {
                            appModel.setLineColor(lineColor);
                            return null;
                        },
                        DEFAULT_LINE_COLOR,
                        STYLE_LINE_COLOR_TITLE
                )
        );
    }

    @Override
    void setFillColorButtonModel() {
        fillColorButton.setModel(
                new StyleColorButtonModel(
                        fillColor -> {
                            fillColorButton.setBackground(fillColor);
                            return null;
                        },
                        fillColor -> {
                            appModel.setFillColor(fillColor);
                            return null;
                        },
                        DEFAULT_FILL_COLOR,
                        STYLE_FILL_COLOR_TITLE
                )
        );
    }

    @Override
    void setTextSizeSpinnerModel() {
        textSizeSpinner.setModel(
                new StyleSpinnerModel(
                        textSize -> {
                            appModel.setTextSize(textSize);
                            return null;
                        },
                        DEFAULT_TEXT_SIZE
                )
        );
    }

    @Override
    void setTextColorButtonModel() {
        textColorButton.setModel(
                new StyleColorButtonModel(
                        textColor -> {
                            textColorButton.setBackground(textColor);
                            return null;
                        },
                        textColor -> {
                            appModel.setTextColor(textColor);
                            return null;
                        },
                        DEFAULT_TEXT_COLOR,
                        STYLE_TEXT_COLOR_TITLE
                )
        );
    }

    @Override
    void setTextContentFieldModel() {
        textContentField.setText(STYLE_DEFAULT_TEXT_CONTENT);
        textContentField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                appModel.setTextContent(textContentField.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                appModel.setTextContent(textContentField.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                appModel.setTextContent(textContentField.getText());
            }
        });
    }
}
