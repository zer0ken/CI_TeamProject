package org.client.gui;

import org.client.gui.components.Canvas;
import org.client.gui.components.*;
import org.client.gui.models.AppModel;

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

        JPanel disablePane = new DisablePanel();
        setGlassPane(disablePane);
        disablePane.setVisible(true);
        getContentPane().setVisible(false);

        AppModel.getInstance().addStringListener(AppModel.Listener.SET_NAME, unused -> {
            disablePane.setVisible(false);
            getContentPane().setVisible(true);
            return null;
        });

        // show it
        setVisible(true);
    }

    public static void start(Function<Void, Void> onWindowClosed) {
        Theme.setup();
        SwingUtilities.invokeLater(() -> new App(onWindowClosed));
    }
}

