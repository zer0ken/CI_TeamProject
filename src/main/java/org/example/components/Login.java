package org.example.components;

import javax.swing.*;

import static org.example.components._Constants.*;

public class Login {
    public static String login(String invalidName) {
        String userName = "";
        while (userName != null && userName.isBlank()) {
            userName = inputUserName(invalidName);
        }
        if (userName == null) {
            System.exit(0);
        }
        return userName;
    }

    private static String inputUserName(String invalidName) {
        String message;
        if(invalidName ==null)
            message = LOGIN_MESSAGE;
        else
            message = LOGIN_MESSAGE_WITH_INVALID_NAME.formatted(invalidName);
        return JOptionPane.showInputDialog(
                null,
                message,
                LOGIN_TITLE,
                JOptionPane.QUESTION_MESSAGE);
    }
}
