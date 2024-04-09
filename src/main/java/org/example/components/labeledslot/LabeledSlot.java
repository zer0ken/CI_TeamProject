package org.example.components.labeledslot;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import static org.example.components._Constants.*;

public class LabeledSlot extends JPanel {
    public LabeledSlot(String label) {
        setLayout(new BorderLayout());
        setMaximumSize(STYLE_ITEM_SIZE);
        setBorder(STYLE_ITEM_BORDER);

        add(BorderLayout.WEST, new JLabel(label));
    }

    protected void addSlot(Component slot, String direction) {
        slot.setPreferredSize(new Dimension(STYLE_SLOT_WIDTH, STYLE_ITEM_HEIGHT));
        add(direction, slot);

        if (!Objects.equals(direction, BorderLayout.EAST)) {
            setMaximumSize(STYLE_LARGE_ITEM_SIZE);
        }
    }

    protected void addSlot(Component slot) {
        addSlot(slot, BorderLayout.EAST);
    }
}

