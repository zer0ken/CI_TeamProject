package org.client.gui;

import org.client.gui.components.Canvas;
import org.client.gui.components.EastPanel;
import org.client.gui.components.Toolbar;
import org.client.gui.components.WestPanel;

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
        setMinimumSize(APP_MIN_SIZE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                onWindowClosed.apply(null);
            }
        });

        // init inner components
        add(new Toolbar(), BorderLayout.NORTH);
        add(new Canvas(), BorderLayout.CENTER);

        add(new WestPanel(), BorderLayout.WEST);
        add(new EastPanel(), BorderLayout.EAST);

        // show it
        setVisible(true);
    }

    public static void start(Function<Void, Void> onWindowClosed) {
        Theme.setup();
        SwingUtilities.invokeLater(() -> new App(onWindowClosed));
    }
}

