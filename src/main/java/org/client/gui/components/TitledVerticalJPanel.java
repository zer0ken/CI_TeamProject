package org.client.gui.components;

import org.client.gui.Theme;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

import static org.client.gui.Constants.*;

public class TitledVerticalJPanel extends JPanel {
    protected JPanel innerPanel;

    TitledVerticalJPanel(String title) {
        setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));
        titlePanel.setBorder(
                BorderFactory.createCompoundBorder(
                        new MatteBorder(0, 0, 1, 0, Theme.getBorderColor()),
                        TITLED_PANEL_PADDING
                )
        );

        titlePanel.add(new JLabel(title), BorderLayout.CENTER);

        innerPanel = new JPanel();
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));
        innerPanel.setBorder(DEFAULT_PANEL_PADDING);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(innerPanel, BorderLayout.NORTH);

        add(titlePanel, BorderLayout.NORTH);
        add(wrapper, BorderLayout.CENTER);
    }

    public Component add(Component comp) {
        innerPanel.add(comp, BorderLayout.CENTER);
        return this;
    }
}
