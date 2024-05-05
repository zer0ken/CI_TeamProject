package org.client.gui.components;

import org.client.gui.models.ToolbarMouseAdapter;

import javax.swing.*;
import java.awt.*;

import static org.client.gui.Constants.*;

public class Toolbar extends ComponentJPanel {
    public Toolbar() {
        super(TOOLBAR_SIZE);
        setLayout(new BorderLayout());

        JToolBar toolBar = new JToolBar(TOOLBAR_TITLE);

        for (int i = 0; i < TOOLBAR_BUTTONS.length; i++) {
            String buttonName = TOOLBAR_BUTTONS[i];
            JButton button = new JButton(new ImageIcon(TOOLBAR_ICONS[i]));
            button.setName(buttonName);     // 버튼을 구분할 때는 Component.getName()을 사용할 것
//            button.setFocusPainted(false);
            button.addMouseListener(new ToolbarMouseAdapter());
            button.setToolTipText(buttonName + "\n" + TOOLBAR_TOOL_TIPS[i]);
            toolBar.add(button);
        }

        // 관련 없는 버튼들 사이에는 구분선을 추가할 것
        // toolBar.addSeparator();

        add(toolBar, BorderLayout.CENTER);
    }
}
