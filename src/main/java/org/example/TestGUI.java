package org.example;

import org.example.components.App;
import org.example.components.Login;

public class TestGUI {
    public static void main(String[] args) {
        System.out.println(Login.login());
        new App();
    }
}