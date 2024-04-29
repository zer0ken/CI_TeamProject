package org.client.gui;

import org.client.ClientStub;
import org.client.gui.components.*;
import org.client.gui.components.Canvas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static org.client.gui.Constants.*;

public class App extends JFrame {
    private ClientStub clientStub;

    private ClientsWindow clientsWindow;

    public App(AppViewModel appViewModel, ClientStub clientStub) {
        super(APP_TITLE);
        this.clientStub = clientStub;

        // init itself
        setLayout(new BorderLayout());
        setSize(APP_WIDTH, APP_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                clientStub.requestLeave();
            }
        });

        // init inner components
        Canvas canvas = new Canvas(appViewModel);
        StyleWindow styleWindow = new StyleWindow(appViewModel);

        JPanel leftPanel = new VerticalJPanel();
        leftPanel.add(styleWindow);
        leftPanel.add(new EditWindow(appViewModel));

        JPanel centerPanel = new VerticalJPanel();
        centerPanel.add(new Toolbar(appViewModel, styleWindow));
        centerPanel.add(canvas);

        JPanel rightPanel = new VerticalJPanel();
        rightPanel.add(clientsWindow = new ClientsWindow(appViewModel));
        rightPanel.add(new ShapesWindow(appViewModel));

        add(leftPanel, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);

        // show it
        setVisible(true);
    }

    public void updateClientsWindow() {
        clientsWindow.setClients(clientStub.getClients());
    }
}

