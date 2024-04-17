package org.example.components;

import javax.swing.*;
import java.awt.*;
import static org.example.components._Constants.*;

import org.example.shapes.Shape;
import org.example.shapes.Line;
import org.example.shapes.Rectangle;
import org.example.shapes.Oval;
import org.example.shapes.Text;

public class App extends JFrame {
    //public Shape currentShape;

    public static Shape currentShape;

    public App() {
        super(APP_TITLE);

        // init itself
        setLayout(new BorderLayout());
        setSize(APP_WIDTH, APP_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // init inner components
        JPanel leftPanel = new VerticalJPanel();
        leftPanel.add(new StyleWindow());
        leftPanel.add(new EditWindow());

        JPanel centerPanel = new VerticalJPanel();
        Canvas canvas = new Canvas();
        centerPanel.add(new Toolbar(canvas));
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

class VerticalJPanel extends JPanel {
    VerticalJPanel() {
        super();
        LayoutManager layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(layout);
    }
}