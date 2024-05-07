package org.client.gui.models;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.function.Function;

import static org.client.gui.Constants.*;

public class StyleWindowController extends StylePanelController {

    private void setSpinnerModel(JSpinner spinner, int defaultValue, Function<Integer, Void> notify) {
        spinner.setModel(new StyleSpinnerModel(notify, defaultValue));
    }

    private void setColorButtonModel(JButton button, Color defaultColor, String chooserTitle,
                                     Function<Color, Void> notify) {
        button.setModel(new StyleColorButtonModel(lineColor -> {
            button.setBackground(lineColor);
            return null;
        }, notify, defaultColor, chooserTitle));
    }

    @Override
    void setLineWidthSpinnerModel() {
        setSpinnerModel(lineWidthSpinner, DEFAULT_LINE_WIDTH, lineWidth -> {
            appModel.setLineWidth(lineWidth);
            return null;
        });
    }

    @Override
    void setLineColorButtonModel() {
        setColorButtonModel(lineColorButton, DEFAULT_LINE_COLOR, STYLE_LINE_COLOR_TITLE, lineColor -> {
            appModel.setLineColor(lineColor);
            return null;
        });
    }

    @Override
    void setFillColorButtonModel() {
        setColorButtonModel(fillColorButton, DEFAULT_FILL_COLOR, STYLE_FILL_COLOR_TITLE, fillColor -> {
            appModel.setFillColor(fillColor);
            return null;
        });
    }

    @Override
    void setTextSizeSpinnerModel() {
        setSpinnerModel(textSizeSpinner, DEFAULT_TEXT_SIZE, textSize -> {
            appModel.setTextSize(textSize);
            return null;
        });
    }

    @Override
    void setTextColorButtonModel() {
        setColorButtonModel(textColorButton, DEFAULT_TEXT_COLOR, STYLE_TEXT_COLOR_TITLE, textColor -> {
            appModel.setTextColor(textColor);
            return null;
        });
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
