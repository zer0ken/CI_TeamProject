package org.example.components;

import static org.example.components._Constants.CANVAS_SIZE;

import java.awt.*;
import java.awt.event.*;

import org.example.shapes.Shape;
import org.example.shapes.Line;
import org.example.shapes.Rectangle;
import org.example.shapes.Oval;
import org.example.shapes.Text;

public class Canvas extends _ComponentJPanel {
    public java.util.List<Shape> shapes;
    private Point mousePoint;
    private Shape currentCShape;

    Canvas() {
        super(CANVAS_SIZE);
        setLayout(new BorderLayout());
        setBackground(Color.white);

        shapes = new java.util.ArrayList<>();

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                for (int i = shapes.size() - 1; i >= 0; i--) {
                    Shape shape = shapes.get(i);
                    if (shape.contains(e.getPoint())) {
                        mousePoint = e.getPoint();

                        if (currentCShape instanceof Line || currentCShape instanceof Rectangle
                            || currentCShape instanceof Oval || currentCShape instanceof Text) {
                            currentCShape.allHandleStopDrag();
                            currentCShape.fineAndStartDrag(e.getPoint());
                        }

                        currentCShape = shape;
                        repaint();
                        return;
                    }
                }
                if(currentCShape != null){
                    currentCShape.allHandleStopDrag();
                }
                currentCShape = null;
                repaint();
            }
        });

        // Canvas에 마우스 드래그 이벤트를 처리하는 리스너 추가
        addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (currentCShape != null) {
                    int dx = e.getX() - mousePoint.x;
                    int dy = e.getY() - mousePoint.y;

                    if (currentCShape instanceof Line || currentCShape instanceof Rectangle
                        || currentCShape instanceof Oval || currentCShape instanceof Text) {

                        currentCShape.handleDrag(e.getPoint(), dx, dy);
                        mousePoint = e.getPoint();
                        repaint();
                        return;
                    }

                    mousePoint = e.getPoint();
                    repaint();
                }
            }
        });
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Shape shape : shapes) {
            if (shape != null) {
                shape.draw(g);
            }
        }
        if (currentCShape != null) {
            currentCShape.drawSelection(g);
        }
    }

    // Canvas 객체에 새로운 도형을 추가하는 메소드
    public void addShape(Shape shape) {
        shapes.add(shape);
    }
}
