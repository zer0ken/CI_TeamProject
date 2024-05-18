package org.client.gui.models;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.util.function.Function;

public class StyleSpinnerModel extends SpinnerNumberModel {
    private final ChangeListener notifier;

    public StyleSpinnerModel(Function<Integer, Void> notify, int defaultValue) {
        setValue(defaultValue);
        notifier = e -> {
            if (getNumber().intValue() >= 0) {
                notify.apply(getNumber().intValue());
            }
        };
        addChangeListener(notifier);
    }

    @Override
    public void setValue(Object value) {
        int old = getNumber().intValue();
        super.setValue(value);
        if (getNumber().intValue() < 0) {
            super.setValue(old);
        }
    }

    public void setNumber(int number) {
        removeChangeListener(notifier);
        setValue(number);
        addChangeListener(notifier);
    }
}
