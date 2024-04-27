package org.gui;

import org.gui.components.Login;

public class Main {
    public static void main(String[] args) {
        System.out.println(Login.login());
        new App(new ShapesViewModel());
    }
}