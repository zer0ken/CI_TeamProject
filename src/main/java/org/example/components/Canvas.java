package org.example.components;

import static org.example.components._Constants.CANVAS_SIZE;

import java.awt.*;
import java.awt.event.*;
import java.util.Stack;

import org.example.shapes.Shape;
import org.example.shapes.Line;
import org.example.shapes.Rectangle;
import org.example.shapes.Oval;
import org.example.shapes.Text;

import javax.swing.*;

public class Canvas extends _ComponentJPanel {
    public java.util.List<Shape> shapes;
    public Stack<Act> _shapes;
    private Point mousePoint;
    private Shape currentCShape;

    Canvas() {
        super(CANVAS_SIZE);
        setLayout(new BorderLayout());
        setBackground(Color.white);
        setFocusable(true);
        requestFocusInWindow();

        shapes = new java.util.ArrayList<>();
        _shapes = new Stack<>();

        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).
            put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "removeShape");
        getActionMap().put("removeShape", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentCShape != null) {
                    rmShape(currentCShape);
                    currentCShape = null;
                    repaint();
                }
            }
        });

        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).
            put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK), "restoreShape");
        getActionMap().put("restoreShape", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                unDo();
                repaint();
            }
        });

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
        setBackground(Color.WHITE);
        for (Shape shape : shapes) {
            if (shape != null) {
                shape.draw(g);
            }
        }
        if (currentCShape != null) {
            currentCShape.drawSelection(g);
        }
    }

    public void addShape(Shape shape) {
        shapes.add(shape);
        _shapes.push(new Act(Act.Action.CREATE, shape));
    }

    public void rmShape(Shape shape) {
        shapes.remove(shape);
        _shapes.push(new Act(Act.Action.DELETE, shape));
    }

    public void unDo() {
        if(!_shapes.isEmpty()) {
            Act act = _shapes.pop();
            if(act.getAction() == Act.Action.CREATE) {
                shapes.remove(act.getShapeTarget());
            } else if (act.getAction() == Act.Action.DELETE) {
                shapes.add(act.getShapeTarget());
            } else if (act.getAction() == Act.Action.STYLE_CHANGE) {
                // 스타일 변경된 것 되돌리기
            }
        }
    }
}
