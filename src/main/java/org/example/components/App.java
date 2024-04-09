package org.example.components;

import javax.swing.*;

import static org.example.components._Constants.*;

public class App extends JFrame {

    public App() {
        super(APP_TITLE);

        // init itself
        setLayout(null);
        setSize(APP_WIDTH, APP_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // init inner components
        add(new StyleWindow());
        add(new EditWindow());
        add(new Toolbar());
        add(new Canvas());
        add(new ClientsWindow());
        add(new ShapesWindow());


        // show it
        setVisible(true);
    }
}