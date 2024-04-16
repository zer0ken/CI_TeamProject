package org.example.components;

import javax.swing.*;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.function.BiFunction;
import java.util.function.Function;

import static org.example.components._Constants.*;

public class App extends JFrame {
    private Function<String, Void> onAdd;
    private BiFunction<Long, String, Void> onEdit;
    private Function<Long, Void> onRemove;
    private Function<Void, Void> onLeave;

    public App(
            Function<String, Void> onAdd,
            BiFunction<Long, String, Void> onEdit,
            Function<Long, Void> onRemove,
            Function<Void, Void> onLeave
    ) {
        this();
        this.onAdd = onAdd;
        this.onEdit = onEdit;
        this.onRemove = onRemove;
        this.onLeave = onLeave;
    }

    private App() {  // TODO: App 클래스는 .components 패키지 외부로 이동해야 함
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
                onLeave.apply(null);
                super.windowClosed(e);
            }
        });

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

