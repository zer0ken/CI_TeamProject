package org.client.gui.components;

import org.client.gui.Theme;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;

import static org.client.gui.Constants.DEFAULT_PANEL_PADDING;

public class TitlePanel extends JPanel {
    public TitlePanel(String title, String tooltip) {
        super();
        setLayout(new BorderLayout());
        setBorder(new MatteBorder(0, 0, 1, 0, Theme.getBorderColor()));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setToolTipText(tooltip);
        add(titleLabel, BorderLayout.CENTER);
    }

    @Override
    public void setBorder(Border border) {
        super.setBorder(new CompoundBorder(
                border,
                DEFAULT_PANEL_PADDING
        ));
    }
}
