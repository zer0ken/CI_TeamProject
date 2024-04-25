package org.example.components;

import org.example.ShapesViewModel;

import javax.swing.*;

import java.awt.*;

import static org.example.components._Constants.*;

public class App extends JFrame {

    public App(ShapesViewModel shapesViewModel) {
        super(APP_TITLE);

        // init itself
        setLayout(new BorderLayout());
        setSize(APP_WIDTH, APP_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // init inner components
        Canvas canvas = new Canvas(shapesViewModel);
        StyleWindow styleWindow = new StyleWindow(shapesViewModel);

        JPanel leftPanel = new VerticalJPanel();
        leftPanel.add(styleWindow);
        leftPanel.add(new EditWindow(shapesViewModel));

        JPanel centerPanel = new VerticalJPanel();
        centerPanel.add(new Toolbar(shapesViewModel, styleWindow));
        centerPanel.add(canvas);

        JPanel rightPanel = new VerticalJPanel();
        rightPanel.add(new ClientsWindow(shapesViewModel));
        rightPanel.add(new ShapesWindow(shapesViewModel));

        add(leftPanel, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);

        // show it
        setVisible(true);
    }
}

class VerticalJPanel extends JPanel {
    VerticalJPanel() {
        super();
        LayoutManager layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(layout);
    }
}