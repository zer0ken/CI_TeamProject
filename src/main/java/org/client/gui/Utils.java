package org.client.gui;

import javax.swing.*;
import java.awt.*;

public class Utils {
    public static ImageIcon scaleIcon(String fileName, Dimension size) {
        return new ImageIcon((new ImageIcon(fileName)).getImage().getScaledInstance(
                (int) size.getWidth(), (int) size.getHeight(), Image.SCALE_SMOOTH));
    }

    public static JPanel wrapWithBorderLayout(JComponent component, String borderLayoutConstraints) {
        JPanel wrapped = new JPanel(new BorderLayout());
        wrapped.add(component, borderLayoutConstraints);
        return wrapped;
    }

    public static JScrollPane wrapWithScrollPane(JComponent component) {
        return new JScrollPane(
                component,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
    }
}
