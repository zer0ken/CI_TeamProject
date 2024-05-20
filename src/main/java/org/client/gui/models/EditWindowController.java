package org.client.gui.models;

import org.client.gui.shapes.Shape;
import org.client.gui.shapes.Style;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;

import static org.client.gui.Constants.*;

public class EditWindowController extends StylePanelController {
    StyleSpinnerModel lineWidthModel;
    StyleColorButtonModel lineColorModel;
    StyleColorButtonModel fillColorModel;
    StyleSpinnerModel textSizeModel;
    StyleColorButtonModel textColorModel;

    private boolean hasPendingChanges = false;


    public EditWindowController() {
        super();
        appModel.addListener(AppModel.Listener.SELECTION, this::setStyleBy);
    }

    @Override
    public void bind(JComponent... components) {
        super.bind(components);

        JButton removeButton = (JButton) components[6];

        removeButton.addActionListener(new RemoveActionListener());
    }

    public Void setStyleBy(Shape shape) {
        if (shape == null) {
            return null;
        }

        Style style = shape.getStyle();
        lineWidthModel.setNumber(style.lineWidth());
        lineColorModel.setColor(style.lineColor());
        fillColorModel.setColor(style.fillColor());
        textSizeModel.setNumber(style.textSize());
        textColorModel.setColor(style.textColor());

        if (textContentField.hasFocus()) {
            hasPendingChanges = true;
        } else {
            textContentField.setText(style.textContent());
        }

        return null;
    }

    @Override
    void setLineWidthSpinnerModel() {
        lineWidthModel = setSpinnerModel(lineWidthSpinner, DEFAULT_LINE_WIDTH);
    }

    @Override
    void setLineColorButtonModel() {
        lineColorModel = setColorButtonModel(lineColorButton, DEFAULT_LINE_COLOR, STYLE_LINE_COLOR_TITLE);
    }

    @Override
    void setFillColorButtonModel() {
        fillColorModel = setColorButtonModel(fillColorButton, DEFAULT_FILL_COLOR, STYLE_FILL_COLOR_TITLE);
    }

    @Override
    void setTextSizeSpinnerModel() {
        textSizeModel = setSpinnerModel(textSizeSpinner, DEFAULT_TEXT_SIZE);
    }

    @Override
    void setTextColorButtonModel() {
        textColorModel = setColorButtonModel(textColorButton, DEFAULT_TEXT_COLOR, STYLE_TEXT_COLOR_TITLE);
    }

    @Override
    void setTextContentFieldModel() {
        textContentField.setText(STYLE_DEFAULT_TEXT_CONTENT);
        FocusListener focusListener = new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (hasPendingChanges) {
                    setStyleBy(appModel.getSelectedShape());
                    hasPendingChanges = false;
                }
            }
        };
        textContentField.getDocument().addDocumentListener(
                new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        if (textContentField.hasFocus()) {
                            updateSelectedShape();
                        }
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        if (textContentField.hasFocus()) {
                            updateSelectedShape();
                        }
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        if (textContentField.hasFocus()) {
                            updateSelectedShape();
                        }
                    }
                }
        );
        textContentField.addFocusListener(focusListener);
    }

    private StyleSpinnerModel setSpinnerModel(JSpinner spinner, int defaultValue) {
        StyleSpinnerModel model = new StyleSpinnerModel(
                unused -> {
                    updateSelectedShape();
                    return null;
                },
                defaultValue
        );
        spinner.setModel(model);
        return model;
    }

    private StyleColorButtonModel setColorButtonModel(JButton button, Color defaultColor, String chooserTitle) {
        StyleColorButtonModel model = new StyleColorButtonModel(
                color -> {
                    button.setBackground(color);
                    return null;
                },
                unused -> {
                    updateSelectedShape();
                    return null;
                },
                defaultColor,
                chooserTitle
        );
        button.setModel(model);
        return model;
    }

    private void updateSelectedShape() {
        Shape selectedShape = appModel.getSelectedShape();
        if (selectedShape == null) {
            return;
        }
        appModel.modifyByUser(selectedShape.copy(getModifiedStyle()));
    }

    private Style getModifiedStyle() {
        return new Style(
                lineWidthModel.getNumber().intValue(),
                lineColorModel.getColor(),
                fillColorModel.getColor(),
                textSizeModel.getNumber().intValue(),
                textColorModel.getColor(),
                textContentField.getText()
        );
    }
}
