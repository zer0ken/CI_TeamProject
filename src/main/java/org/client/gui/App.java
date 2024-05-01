package org.client.gui;

import org.client.gui.components.Canvas;
import org.client.gui.components.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.function.Function;

import static org.client.gui.Constants.*;

public class App extends JFrame {
    public App(Function<Void, Void> onWindowClosed) {
        super(APP_TITLE);

        // init itself
        setLayout(new BorderLayout());
        setSize(APP_WIDTH, APP_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                onWindowClosed.apply(null);
            }
        });

        Canvas canvas = new Canvas();
        // init inner components
        StyleWindow styleWindow = new StyleWindow();

        JPanel leftPanel = new VerticalJPanel();
        leftPanel.add(styleWindow);
        leftPanel.add(new EditWindow());

        JPanel centerPanel = new VerticalJPanel();
        centerPanel.add(new Toolbar(styleWindow));
        centerPanel.add(canvas);

        JPanel rightPanel = new VerticalJPanel();
        rightPanel.add(new ClientsWindow());
        rightPanel.add(new ShapesWindow());

        add(leftPanel, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);

        // show it
        setVisible(true);
    }
}

