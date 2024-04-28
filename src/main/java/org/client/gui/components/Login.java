package org.client.gui.components;

import javax.swing.*;

import static org.client.gui.Constants.*;

public class Login {
    public static String login(boolean invalid) {
        String userName = "";
        while (userName != null && userName.isBlank()) {
            userName = inputUserName(invalid);
        }
        if (userName == null) {
            System.exit(0);
        }
        return userName;
    }

    private static String inputUserName(boolean invalid) {
        String message;
        if (invalid)
            message = LOGIN_MESSAGE_WITH_INVALID_NAME;
        else
            message = LOGIN_MESSAGE;
        return JOptionPane.showInputDialog(
                null,
                message,
                LOGIN_TITLE,
                JOptionPane.QUESTION_MESSAGE);
    }
}
