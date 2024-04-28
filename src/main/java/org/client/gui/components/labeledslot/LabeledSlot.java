package org.client.gui.components.labeledslot;

import org.client.gui.Constants;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class LabeledSlot extends JPanel {
    public LabeledSlot(String label) {
        setLayout(new BorderLayout());
        setMaximumSize(Constants.STYLE_ITEM_SIZE);
        setBorder(Constants.STYLE_ITEM_BORDER);

        add(BorderLayout.WEST, new JLabel(label));
    }

    protected void addSlot(Component slot, String direction) {
        slot.setPreferredSize(new Dimension(Constants.STYLE_SLOT_WIDTH, Constants.STYLE_ITEM_HEIGHT));
        add(direction, slot);

        if (!Objects.equals(direction, BorderLayout.EAST)) {
            setMaximumSize(Constants.STYLE_LARGE_ITEM_SIZE);
        }
    }

    protected void addSlot(Component slot) {
        addSlot(slot, BorderLayout.EAST);
    }
}

