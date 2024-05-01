package org.client.gui.models;

import org.client.gui.shapes.Shape;
import org.client.gui.shapes.Style;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import static org.client.gui.Constants.*;

public class EditWindowModel extends DefaultStyleWindowModel {
    StyleSpinnerModel lineWidthModel;
    StyleColorButtonModel lineColorModel;
    StyleColorButtonModel fillColorModel;
    StyleSpinnerModel textSizeModel;
    StyleColorButtonModel textColorModel;

    public EditWindowModel() {
        super();
        appViewModel.addListener(AppViewModel.Listener.SELECTION, this::setStyleOf);
    }

    public Void setStyleOf(Shape shape) {
        if (shape == null) {
            return null;
        }
        Style style = shape.getStyle();
        if (lineWidthModel == null || lineColorModel == null || fillColorModel == null ||
                textSizeModel == null || textColorModel == null) {
            return null;
        }
        lineWidthModel.setNumber(style.getLineWidth());
        lineColorModel.setColor(style.getLineColor());
        fillColorModel.setColor(style.getFillColor());
        textSizeModel.setNumber(style.getTextSize());
        textColorModel.setColor(style.getTextColor());
        textContentField.setText(style.getTextContent());
        return null;
    }

    private StyleSpinnerModel setSpinnerModel(JSpinner spinner, int defaultValue) {
        StyleSpinnerModel model = new StyleSpinnerModel(
                unused -> {
                    Shape selectedShape = appViewModel.getSelectedShape();
                    if (selectedShape == null) {
                        return null;
                    }
                    appViewModel.modifyByUser(selectedShape.getId(), selectedShape.copy(getModifiedStyle()));
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
                    Shape selectedShape = appViewModel.getSelectedShape();
                    if (selectedShape == null) {
                        return null;
                    }
                    appViewModel.modifyByUser(selectedShape.getId(), selectedShape.copy(getModifiedStyle()));
                    return null;
                },
                defaultColor,
                chooserTitle
        );
        button.setModel(model);
        return model;
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
                Shape selectedShape = appViewModel.getSelectedShape();
                if (selectedShape == null) {
                    return;
                }
                appViewModel.modifyByUser(selectedShape.getId(), selectedShape.copy(getModifiedStyle()));
            }
        };
        textContentField.addFocusListener(focusListener);

        appViewModel.addListener(AppViewModel.Listener.SELECTION, shape -> {
            if (shape == null) {
                return  null;
            }
            textContentField.setText(shape.getStyle().getTextContent());
            return null;
        });
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
