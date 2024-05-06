package org.client.gui.components;

import org.client.gui.Theme;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

import static org.client.gui.Constants.TITLED_PANEL_PADDING;

public class TitledBorderJPanel extends JPanel{
    TitledBorderJPanel(String title) {
        setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBorder(
                BorderFactory.createCompoundBorder(
                        new MatteBorder(0, 1, 0, 0, Theme.getBorderColor()),
                        TITLED_PANEL_PADDING
                )
        );

        titlePanel.add(new JLabel(title), BorderLayout.NORTH);

        add(titlePanel, BorderLayout.NORTH);
    }
}
