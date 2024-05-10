package org.client.gui;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    public static <K, V extends Comparable<? super V>> Map<K, V> sortedByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }
}
