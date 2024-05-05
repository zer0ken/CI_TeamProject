package org.client.gui;

import com.formdev.flatlaf.FlatLightLaf;
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

        try {
            UIManager.setLookAndFeel( new FlatLightLaf() );
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }

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

        // init inner components
        StyleWindow styleWindow = new StyleWindow();

        JPanel leftPanel = new VerticalJPanel();
        leftPanel.add(styleWindow);
        leftPanel.add(new EditWindow());

        add(new Toolbar(), BorderLayout.NORTH);
        add(new Canvas(), BorderLayout.CENTER);

        JPanel rightPanel = new VerticalJPanel();
        rightPanel.add(new ClientsWindow());
        rightPanel.add(new ShapesWindow());

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);

        // show it
        setVisible(true);
    }
}

