package org.example.components;

import javax.swing.*;

import java.awt.*;

import static org.example.components._Constants.*;

public class App extends JFrame {

    public App() {  // TODO: App 클래스는 .components 패키지 외부로 이동해야 함
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
        centerPanel.add(new Toolbar());
        centerPanel.add(new Canvas());

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

