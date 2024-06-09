package org.client.gui.components;

import javax.swing.*;

public class DisablePanel extends JPanel {
    public DisablePanel() {
        add(Box.createGlue());
        add(new JLabel("앱이 정상적으로 동작하기 위해 서버에 로그인해야 합니다."));
        add(Box.createGlue());
    }
}
