package org.gui;

import org.client.ClientStub;
import org.gui.components.Canvas;
import org.gui.components.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static org.gui.components._Constants.*;

public class App extends JFrame {
    private ClientStub clientStub;

    private ClientsWindow clientsWindow;

    public App(ShapesViewModel shapesViewModel, ClientStub clientStub) {
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
        org.gui.components.Canvas canvas = new Canvas(shapesViewModel);
        StyleWindow styleWindow = new StyleWindow(shapesViewModel);

        JPanel leftPanel = new VerticalJPanel();
        leftPanel.add(styleWindow);
        leftPanel.add(new EditWindow(shapesViewModel));

        JPanel centerPanel = new VerticalJPanel();
        centerPanel.add(new Toolbar(shapesViewModel, styleWindow));
        centerPanel.add(canvas);

        JPanel rightPanel = new VerticalJPanel();
        rightPanel.add(clientsWindow = new ClientsWindow(shapesViewModel));
        rightPanel.add(new ShapesWindow(shapesViewModel));

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

