package org.example.components;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import static org.example.components._Constants.CANVAS_SIZE;

public class Canvas extends _ComponentJPanel {

    private ArrayList<Shape> shapes; // shape list
    Canvas() {
        super(CANVAS_SIZE);
        setLayout(new BorderLayout());
        setBackground(Color.white);
        shapes = new ArrayList<>();
    }

    // draw shape
    public void drawShape(Shape shape) {
        shapes.add(shape);
        repaint();
    }

    // modify shape
    public void modifyShape(int index, Shape modifiedShape) {
        if (index >= 0 && index < shapes.size()) {
            shapes.set(index, modifiedShape);
            repaint();
        }
    }

    // delete shape
    public void deleteShape(int index) {
        if (index >= 0 && index < shapes.size()) {
            shapes.remove(index);
            repaint(); // 컴포넌트를 다시 그리도록 요청
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // paint all stored shape
//        for (Shape shape : shapes) {
//            shape.draw(g);
//        }
    }
}
