package org.client.gui;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.ui.FlatBorder;

import javax.swing.*;
import java.awt.*;

public class Theme {
    public static void setup() {
        try {
            FlatLightLaf.setup();
            UIManager.put("ScrollPane.border", new FlatBorder());
            UIManager.put("ScrollBar.thumbInsets", new Insets(2, 2, 2, 2));
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
    }

    public static Color getBorderColor() {
        return (Color) UIManager.get("Component.borderColor");
    }
}
