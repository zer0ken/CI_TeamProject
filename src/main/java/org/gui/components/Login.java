package org.gui.components;

import javax.swing.*;

import static org.gui.components._Constants.LOGIN_MESSAGE;
import static org.gui.components._Constants.LOGIN_TITLE;

public class Login {
    public static String login() {
        String userName = "";
        while (userName != null && userName.isBlank()) {
            userName = inputUserName();
        }
        if (userName == null) {
            System.exit(0);
        }
        return userName;
    }

    private static String inputUserName() {
        return JOptionPane.showInputDialog(
                null,
                LOGIN_MESSAGE,
                LOGIN_TITLE,
                JOptionPane.QUESTION_MESSAGE);
    }
}
