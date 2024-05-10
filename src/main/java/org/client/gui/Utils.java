package org.client.gui;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.text.SimpleDateFormat;

public class Utils {
    public static ImageIcon scaleIcon(URL iconURL, Dimension size) {
        return new ImageIcon((new ImageIcon(iconURL)).getImage().getScaledInstance(
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
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
    }

    public static JScrollPane wrapWithVerticalScrollPane(JComponent component) {
        return new JScrollPane(
                component,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
    }

    public static String formatTime(long time) {
        return (new SimpleDateFormat("a h시 m분 s초")).format(time);
    }
}
