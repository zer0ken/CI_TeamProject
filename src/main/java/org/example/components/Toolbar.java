package org.example.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import static org.example.components._Constants.*;

public class Toolbar extends _ComponentJPanel {
    private String selectedTool = TOOLBAR_DEFAULT_BUTTON;
    private JButton[] toolButtons;
    private JLabel titleLabel;
    Toolbar() {
        super(TOOLBAR_SIZE);
        setLayout(new FlowLayout(FlowLayout.LEFT));

        // Toolbar title
        titleLabel = new JLabel(TOOLBAR_TITLE);
        add(titleLabel); // Add the title label before adding buttons

        // Toolbar button setting
        toolButtons = new JButton[TOOLBAR_BUTTONS.length];
        for (int i = 0; i < TOOLBAR_BUTTONS.length; i++){
            JButton button = new JButton(TOOLBAR_BUTTONS[i]);
            button.addActionListener(new ToolButtonListener());
            button.setFocusPainted(false);
            add(button);
            toolButtons[i] = button;
        }

    }

    // selectedButton setting method
    private void selectTool(String toolName) {
        selectedTool = toolName;
        for (JButton button : toolButtons) {
            if (button.getText().equals(toolName)) {
                button.setBackground(new Color(173, 216, 230));
            } else {
                button.setBackground(null);
            }
        }
    }

    // Toolbar button ActionListener
    class ToolButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            selectTool(e.getActionCommand());
        }
    }
}

