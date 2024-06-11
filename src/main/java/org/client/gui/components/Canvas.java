package org.client.gui.components;

import org.client.gui.models.AppModel;
import org.client.gui.models.CanvasModel;
import org.client.gui.shape.Shape;
import org.client.gui.shape.ShapeHandler;

import javax.swing.*;
import java.awt.*;

public class Canvas extends JPanel {
    private final AppModel appModel = AppModel.getInstance();
    private final ShapeHandler handler;

    public Canvas() {
        setLayout(new BorderLayout());
        setBackground(Color.white);
        setFocusable(true);
        requestFocusInWindow();

        CanvasModel canvasMouseAdapter = CanvasModel.getInstance();
        handler = canvasMouseAdapter.getHandler();
        addMouseListener(canvasMouseAdapter);
        addMouseMotionListener(canvasMouseAdapter);

        appModel.addListener(AppModel.Listener.UPDATE, unused -> {
            repaint();
            return null;
        });
        appModel.addListener(AppModel.Listener.SELECTION, selected -> {
            repaint();
            return null;
        });
        appModel.addVoidListener(AppModel.Listener.CLEAR, unused -> {
            repaint();
            return null;
        });
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.WHITE);
        for (Shape shape : appModel.getShapes().values()) {
            shape.draw(g);
        }
        handler.draw(g);
    }
}
