package org.client.gui;

import com.formdev.flatlaf.intellijthemes.FlatArcOrangeIJTheme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Theme {
    public static void setup() {
        try {
            FlatArcOrangeIJTheme.setup();
            UIManager.put("ScrollBar.thumbInsets", new Insets(2, 2, 2, 2));
            UIManager.put("List.border", new EmptyBorder(6, 12, 6, 12));
            UIManager.put("List.selectionArc", 8);
            UIManager.put("List.cellMargins", new Insets(2, 6, 2, 6));
            UIManager.put("ScrollPane.border", BorderFactory.createEmptyBorder());
            UIManager.put("Button.toolbar.spacingInsets", new Insets(4, 1, 4, 1));
            UIManager.put("Button.selectedBackground", UIManager.get("Button.selectedBackground"));
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
    }

    public static Color getBorderColor() {
        return (Color) UIManager.get("Component.borderColor");
    }
}
