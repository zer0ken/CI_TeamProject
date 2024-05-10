package org.client.gui.models;

import org.client.gui.shapes.Shape;
import org.client.gui.shapes.Style;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static org.client.gui.Constants.*;

public class EditWindowController extends StylePanelController {
    private JLabel applyLabel;

    StyleSpinnerModel lineWidthModel;
    StyleColorButtonModel lineColorModel;
    StyleColorButtonModel fillColorModel;
    StyleSpinnerModel textSizeModel;
    StyleColorButtonModel textColorModel;

    public EditWindowController() {
        super();
        appModel.addListener(AppModel.Listener.SELECTION, this::setStyleBy);
        appModel.addListener(AppModel.Listener.USER_MODIFICATION, this::setModifiedStyleBy);
    }

    @Override
    public void bind(JComponent... components) {
        super.bind(components);

        applyLabel = (JLabel) components[6];
        JButton removeButton = (JButton) components[7];

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
        textContentField.setText(style.textContent());
        return null;
    }

    public Void setModifiedStyleBy(Shape shape) {
        if (!appModel.getSelectedShape().equals(shape)) {
            return null;
        }
        setStyleBy(shape);
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
                applyLabel.setVisible(true);
            }

            @Override
            public void focusLost(FocusEvent e) {
                applyLabel.setVisible(false);
                Shape selectedShape = appModel.getSelectedShape();
                if (selectedShape == null) {
                    return;
                }
                appModel.modifyByUser(selectedShape.copy(getModifiedStyle()));
                appModel.storeUndoStack(UserAction.Type.STYLE_MODIFY,
                    selectedShape.copy(getModifiedStyle()), selectedShape);
            }
        };
        textContentField.addFocusListener(focusListener);
    }

    private StyleSpinnerModel setSpinnerModel(JSpinner spinner, int defaultValue) {
        StyleSpinnerModel model = new StyleSpinnerModel(
                this::spinnerNotify,
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
                    Shape selectedShape = appModel.getSelectedShape();
                    if (selectedShape == null) {
                        return null;
                    }
                    appModel.modifyByUser(selectedShape.copy(getModifiedStyle()));
                    appModel.storeUndoStack(UserAction.Type.STYLE_MODIFY,
                        selectedShape.copy(getModifiedStyle()), selectedShape);
                    return null;
                },
                defaultColor,
                chooserTitle
        );
        button.setModel(model);
        return model;
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

    private Void spinnerNotify(Integer unused) {
        Shape selectedShape = appModel.getSelectedShape();
        if (selectedShape == null) {
            return null;
        }
        appModel.modifyByUser(selectedShape.copy(getModifiedStyle()));
        appModel.storeUndoStack(UserAction.Type.STYLE_MODIFY,
                selectedShape.copy(getModifiedStyle()), selectedShape);
        return null;
    }
}
