package org.client.gui.components;

import org.client.gui.Utils;
import org.client.gui.models.DefaultStyleWindowModel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static org.client.gui.Constants.*;

public class StylePanel extends JPanel {
    protected JSpinner lineWidth;
    protected JButton lineColor;
    protected JButton fillColor;
    protected JSpinner textSize;
    protected JButton textColor;
    protected JTextField textContent;
    private final GridBagConstraints constraints;

    protected StylePanel(DefaultStyleWindowModel model) {
        setLayout(new GridBagLayout());
        setBorder(DEFAULT_PANEL_PADDING);

        this.constraints = new GridBagConstraints();

        lineWidth = new JSpinner();
        lineColor = new JButton();
        fillColor = new JButton();
        textSize = new JSpinner();
        textColor = new JButton();
        textContent = new JTextField();
        setModel(model);
    }

    public void initVertically(String[] tooltips) {
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = GRID_CELL_PADDING;
        constraints.gridx = 0;
        constraints.weightx = 1.0;

        for (int i = 0; i < 6; i++) {
            constraints.gridy = i;
            if (i == 5) {
                constraints.gridwidth = 2;
            }

            JLabel label = new JLabel(STYLE_LABELS[i]);
            label.setToolTipText(tooltips[i]);

            JPanel resized = Utils.wrapWithBorderLayout(label, BorderLayout.CENTER);
            resized.setPreferredSize(GRID_CELL_SIZE);

            add(resized, constraints);
        }

        constraints.gridwidth = 1;
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weightx = 0.0;

        for (JComponent component : List.of(lineWidth, lineColor, fillColor, textSize, textColor, textContent)) {
            if (component == textContent) {
                constraints.gridx = 0;
                constraints.gridy++;
                constraints.gridwidth = 2;
            }
            add(component, constraints);
            constraints.gridy++;
        }
    }

    public void setModel(DefaultStyleWindowModel model) {
        model.bind(
                lineWidth,
                lineColor,
                fillColor,
                textSize,
                textColor,
                textContent
        );
    }

    public GridBagConstraints getConstraints() {
        return constraints;
    }
}
