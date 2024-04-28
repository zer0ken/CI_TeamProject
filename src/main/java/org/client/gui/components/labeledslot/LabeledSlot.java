package org.client.gui.components.labeledslot;

import org.client.gui.components._Constants;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class LabeledSlot extends JPanel {
    public LabeledSlot(String label) {
        setLayout(new BorderLayout());
        setMaximumSize(_Constants.STYLE_ITEM_SIZE);
        setBorder(_Constants.STYLE_ITEM_BORDER);

        add(BorderLayout.WEST, new JLabel(label));
    }

    protected void addSlot(Component slot, String direction) {
        slot.setPreferredSize(new Dimension(_Constants.STYLE_SLOT_WIDTH, _Constants.STYLE_ITEM_HEIGHT));
        add(direction, slot);

        if (!Objects.equals(direction, BorderLayout.EAST)) {
            setMaximumSize(_Constants.STYLE_LARGE_ITEM_SIZE);
        }
    }

    protected void addSlot(Component slot) {
        addSlot(slot, BorderLayout.EAST);
    }
}

