package org.example;

import org.example.components.App;
import org.example.components.Login;

public class Main {
    public static void main(String[] args) {
        System.out.println(Login.login());
        new App(new ShapesViewModel());
    }
}