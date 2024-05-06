package org.client.gui;

import com.formdev.flatlaf.FlatLightLaf;
import org.client.gui.components.Canvas;
import org.client.gui.components.*;

import javax.swing.*;
import javax.swing.border.MatteBorder;
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
        setMinimumSize(APP_MIN_SIZE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
//        setResizable(false);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                onWindowClosed.apply(null);
            }
        });

        // init inner components
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(new MatteBorder(0, 0, 0, 1, PANEL_SEPERATOR_COLOR));
        leftPanel.add(new StyleWindow(), BorderLayout.NORTH);
        leftPanel.add(new EditWindow(), BorderLayout.CENTER);

        add(new Toolbar(), BorderLayout.NORTH);
        add(new Canvas(), BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setPreferredSize(APP_RIGHT_SIZE);
        rightPanel.add(new ClientsWindow(), BorderLayout.NORTH);
        rightPanel.add(new ShapesWindow(), BorderLayout.CENTER);

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);

        // show it
        setVisible(true);
    }
}

