package org.client.gui.utils;

import javax.swing.*;
import java.awt.*;

public class IconUtils {
    public static ImageIcon scaledIcon(String fileName, Dimension size) {
        return new ImageIcon((new ImageIcon(fileName)).getImage().getScaledInstance(
                (int) size.getWidth(), (int) size.getHeight(), java.awt.Image.SCALE_SMOOTH));
    }
}
