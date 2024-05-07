package org.client.gui.components;

import org.client.gui.Theme;
import org.client.gui.Utils;
import org.client.gui.models.ToolbarMouseAdapter;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

import static org.client.gui.Constants.*;

public class Toolbar extends JPanel {
    public Toolbar() {
//        setPreferredSize(TOOLBAR_SIZE);
        setLayout(new BorderLayout());
        setBorder(new MatteBorder(1, 0, 1, 0, Theme.getBorderColor()));

        ToolbarMouseAdapter toolbarMouseAdapter = new ToolbarMouseAdapter();

        JToolBar toolbar = new JToolBar(TOOLBAR_TITLE);

        for (int i = 0; i < TOOLBAR_SHAPE_TOOLS.length; i++) {
            String buttonName = TOOLBAR_SHAPE_TOOLS[i];
            JButton button = new JButton(Utils.scaleIcon(TOOLBAR_SHAPE_ICONS[i], TOOLBAR_ICON_SIZE));
            button.setName(buttonName);
            button.addMouseListener(toolbarMouseAdapter);
            button.setToolTipText(TOOLBAR_SHAPE_TOOLTIPS[i]);
            toolbar.add(button);
        }

        // 관련 없는 버튼들 사이에는 구분선을 추가
        // toolBar.addSeparator();

        Box wrapped = Box.createHorizontalBox();
        wrapped.add(Box.createGlue());
        wrapped.add(toolbar);
        wrapped.add(Box.createGlue());

        add(wrapped, BorderLayout.CENTER);
    }
}
