package org.client.gui.components;

import org.client.gui.models.ToolbarMouseAdapter;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

import static org.client.gui.Constants.*;

public class Toolbar extends JPanel {
    public Toolbar() {
        setPreferredSize(TOOLBAR_SIZE);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(new MatteBorder(0, 0, 1, 0, PANEL_SEPERATOR_COLOR));

        JToolBar toolBar = new JToolBar(TOOLBAR_TITLE);

        for (int i = 0; i < TOOLBAR_SHAPE_TOOLS.length; i++) {
            String buttonName = TOOLBAR_SHAPE_TOOLS[i];
            JButton button = new JButton(new ImageIcon(TOOLBAR_ICONS[i]));
            button.setName(buttonName);     // 버튼을 구분할 때는 Component.getName()을 사용
            button.addMouseListener(new ToolbarMouseAdapter());
            button.setToolTipText(buttonName + "\n" + TOOLBAR_TOOL_TIPS[i]);
            toolBar.add(button);
        }

        // 관련 없는 버튼들 사이에는 구분선을 추가
        // toolBar.addSeparator();

        add(Box.createHorizontalGlue(), BorderLayout.CENTER);
        add(toolBar, BorderLayout.CENTER);
        add(Box.createHorizontalGlue(), BorderLayout.CENTER);
    }
}
