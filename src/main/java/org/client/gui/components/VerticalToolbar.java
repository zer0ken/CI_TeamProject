package org.client.gui.components;

import org.client.gui.Utils;
import org.client.gui.models.PanelVisibilityController;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

import static org.client.gui.Constants.TOOLBAR_ICON_SIZE;

public class VerticalToolbar extends JToolBar {

    public VerticalToolbar(String[] buttonNames, String[] iconFileNames, String[] tooltips, PanelVisibilityController controller) {
        super(JToolBar.VERTICAL);

        for (int i = 0; i < buttonNames.length; i++) {
            String buttonName = buttonNames[i];
            JToggleButton button = new JToggleButton(Utils.scaleIcon(iconFileNames[i], TOOLBAR_ICON_SIZE));
            button.setName(buttonName);
            button.setToolTipText(tooltips[i]);
            button.setSelected(true);
            button.addChangeListener(controller);
            add(button);
        }
    }

}
