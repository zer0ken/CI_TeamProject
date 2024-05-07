package org.client.gui.utils;

import javax.swing.*;
import java.awt.*;

public class PanelUtils {
    public static JPanel stick(JComponent component, String stickTo) {
        JPanel wrapped = new JPanel(new BorderLayout());
        wrapped.add(component, stickTo);
        return wrapped;
    }
}
