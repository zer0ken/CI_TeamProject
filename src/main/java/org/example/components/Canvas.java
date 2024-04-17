package org.example.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import org.example.shapes.Shape;
import org.example.shapes.Line;
import org.example.shapes.Rectangle;
import org.example.shapes.Oval;
import org.example.shapes.Text;

import static org.example.components._Constants.CANVAS_SIZE;

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
                for (int i = shapes.size() - 1; i >= 0; i--) {  // 도형 리스트를 순회하면서 클릭된 지점이 도형 내에 있는지 확인
                    Shape shape = shapes.get(i);
                    if (shape.contains(e.getPoint())) {     // 리스트 안의 도형일 경우

                        mousePoint = e.getPoint();
                        // 드래그 상태를 설정합니다.
                        if (currentCShape instanceof Line) {                 // 직선인 경우
                            ((Line) currentCShape).startHandle.stopDragging();
                            ((Line) currentCShape).endHandle.stopDragging();
                            if (((Line) currentCShape).startHandle.contains(e.getPoint())) {
                                ((Line) currentCShape).startHandle.startDragging(e.getPoint());
                            } else if (((Line) currentCShape).endHandle.contains(e.getPoint())) {
                                ((Line) currentCShape).endHandle.startDragging(e.getPoint());
                            }

                        } else if (currentCShape instanceof Rectangle) {   // 사각형인 경우
                            ((Rectangle) currentCShape).northHandle.stopDragging();
                            ((Rectangle) currentCShape).southHandle.stopDragging();
                            ((Rectangle) currentCShape).eastHandle.stopDragging();
                            ((Rectangle) currentCShape).westHandle.stopDragging();

                            ((Rectangle) currentCShape).northEastHandle.stopDragging();
                            ((Rectangle) currentCShape).northWestHandle.stopDragging();
                            ((Rectangle) currentCShape).southEastHandle.stopDragging();
                            ((Rectangle) currentCShape).southWestHandle.stopDragging();

                            if (((Rectangle) currentCShape).northHandle.contains(e.getPoint())) {
                                ((Rectangle) currentCShape).northHandle.startDragging(e.getPoint());
                            } else if (((Rectangle) currentCShape).southHandle.contains(e.getPoint())) {
                                ((Rectangle) currentCShape).southHandle.startDragging(e.getPoint());
                            } else if (((Rectangle) currentCShape).eastHandle.contains(e.getPoint())) {
                                ((Rectangle) currentCShape).eastHandle.startDragging(e.getPoint());
                            } else if (((Rectangle) currentCShape).westHandle.contains(e.getPoint())) {
                                ((Rectangle) currentCShape).westHandle.startDragging(e.getPoint());

                            } else if (((Rectangle) currentCShape).northEastHandle.contains(e.getPoint())) {
                                ((Rectangle) currentCShape).northEastHandle.startDragging(e.getPoint());
                            } else if (((Rectangle) currentCShape).northWestHandle.contains(e.getPoint())) {
                                ((Rectangle) currentCShape).northWestHandle.startDragging(e.getPoint());
                            } else if (((Rectangle) currentCShape).southEastHandle.contains(e.getPoint())) {
                                ((Rectangle) currentCShape).southEastHandle.startDragging(e.getPoint());
                            } else if (((Rectangle) currentCShape).southWestHandle.contains(e.getPoint())) {
                                ((Rectangle) currentCShape).southWestHandle.startDragging(e.getPoint());
                            }

                        } else if (currentCShape instanceof Oval) {        // 원인 경우
                            ((Oval) currentCShape).northEastHandle.stopDragging();
                            ((Oval) currentCShape).northWestHandle.stopDragging();
                            ((Oval) currentCShape).southEastHandle.stopDragging();
                            ((Oval) currentCShape).southWestHandle.stopDragging();

                            if (((Oval) currentCShape).northEastHandle.contains(e.getPoint())) {
                                ((Oval) currentCShape).northEastHandle.startDragging(e.getPoint());
                            } else if (((Oval) currentCShape).northWestHandle.contains(e.getPoint())) {
                                ((Oval) currentCShape).northWestHandle.startDragging(e.getPoint());
                            } else if (((Oval) currentCShape).southEastHandle.contains(e.getPoint())) {
                                ((Oval) currentCShape).southEastHandle.startDragging(e.getPoint());
                            } else if (((Oval) currentCShape).southWestHandle.contains(e.getPoint())) {
                                ((Oval) currentCShape).southWestHandle.startDragging(e.getPoint());
                            }

                        } else if (currentCShape instanceof Text) {          // 텍스트인 경우
                            ((Text) currentCShape).northHandle.stopDragging();
                            ((Text) currentCShape).southHandle.stopDragging();
                            ((Text) currentCShape).eastHandle.stopDragging();
                            ((Text) currentCShape).westHandle.stopDragging();

                            ((Text) currentCShape).northEastHandle.stopDragging();
                            ((Text) currentCShape).northWestHandle.stopDragging();
                            ((Text) currentCShape).southEastHandle.stopDragging();
                            ((Text) currentCShape).southWestHandle.stopDragging();

                            if (((Text) currentCShape).northHandle.contains(e.getPoint())) {
                                ((Text) currentCShape).northHandle.startDragging(e.getPoint());
                            } else if (((Text) currentCShape).southHandle.contains(e.getPoint())) {
                                ((Text) currentCShape).southHandle.startDragging(e.getPoint());
                            } else if (((Text) currentCShape).eastHandle.contains(e.getPoint())) {
                                ((Text) currentCShape).eastHandle.startDragging(e.getPoint());
                            } else if (((Text) currentCShape).westHandle.contains(e.getPoint())) {
                                ((Text) currentCShape).westHandle.startDragging(e.getPoint());

                            } else if (((Text) currentCShape).northEastHandle.contains(e.getPoint())) {
                                ((Text) currentCShape).northEastHandle.startDragging(e.getPoint());
                            } else if (((Text) currentCShape).northWestHandle.contains(e.getPoint())) {
                                ((Text) currentCShape).northWestHandle.startDragging(e.getPoint());
                            } else if (((Text) currentCShape).southEastHandle.contains(e.getPoint())) {
                                ((Text) currentCShape).southEastHandle.startDragging(e.getPoint());
                            } else if (((Text) currentCShape).southWestHandle.contains(e.getPoint())) {
                                ((Text) currentCShape).southWestHandle.startDragging(e.getPoint());
                            }

                        }

                        currentCShape = shape;
                        repaint();
                        return;
                    }
                }
                currentCShape = null;
                repaint();
            }
        });

        // Canvas에 마우스 드래그 이벤트를 처리하는 리스너 추가
        addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                // 마우스를 드래그할 때, 현재 선택된 도형이 있을 경우
                // 마우스 이동에 따라 해당 도형을 이동시키면서 다시 그림
                if (currentCShape != null) {
                    int dx = e.getX() - mousePoint.x;
                    int dy = e.getY() - mousePoint.y;

                    if (currentCShape instanceof Line) {         // 직선인 경우
                        if (((Line) currentCShape).startHandle.isDragging()) {
                            ((Line) currentCShape).startHandle.drag(e.getPoint());
                        } else if (((Line) currentCShape).endHandle.isDragging()) {
                            ((Line) currentCShape).endHandle.drag(e.getPoint());
                        } else {
                            currentCShape.move(dx, dy);
                        }
                        mousePoint = e.getPoint();
                        repaint();
                        return;

                    } else if (currentCShape instanceof Rectangle) {     // 사각형인 경우
                        if (((Rectangle) currentCShape).northHandle.isDragging()) {
                            ((Rectangle) currentCShape).northHandle.drag(e.getPoint());
                        } else if (((Rectangle) currentCShape).southHandle.isDragging()) {
                            ((Rectangle) currentCShape).southHandle.drag(e.getPoint());
                        } else if (((Rectangle) currentCShape).eastHandle.isDragging()) {
                            ((Rectangle) currentCShape).eastHandle.drag(e.getPoint());
                        } else if (((Rectangle) currentCShape).westHandle.isDragging()) {
                            ((Rectangle) currentCShape).westHandle.drag(e.getPoint());

                        } else if (((Rectangle) currentCShape).northEastHandle.isDragging()) {
                            ((Rectangle) currentCShape).northEastHandle.drag(e.getPoint());
                        } else if (((Rectangle) currentCShape).northWestHandle.isDragging()) {
                            ((Rectangle) currentCShape).northWestHandle.drag(e.getPoint());
                        } else if (((Rectangle) currentCShape).southEastHandle.isDragging()) {
                            ((Rectangle) currentCShape).southEastHandle.drag(e.getPoint());
                        } else if (((Rectangle) currentCShape).southWestHandle.isDragging()) {
                            ((Rectangle) currentCShape).southWestHandle.drag(e.getPoint());

                        } else {
                            currentCShape.move(dx, dy);
                        }
                        mousePoint = e.getPoint();
                        repaint();
                        return;

                    } else if (currentCShape instanceof Oval) {    // 원인 경우
                        if (((Oval) currentCShape).northEastHandle.isDragging()) {
                            ((Oval) currentCShape).northEastHandle.drag(e.getPoint());
                        } else if (((Oval) currentCShape).northWestHandle.isDragging()) {
                            ((Oval) currentCShape).northWestHandle.drag(e.getPoint());
                        } else if (((Oval) currentCShape).southEastHandle.isDragging()) {
                            ((Oval) currentCShape).southEastHandle.drag(e.getPoint());
                        } else if (((Oval) currentCShape).southWestHandle.isDragging()) {
                            ((Oval) currentCShape).southWestHandle.drag(e.getPoint());

                        } else {
                            currentCShape.move(dx, dy);
                        }
                        mousePoint = e.getPoint();
                        repaint();
                        return;
                    } else if (currentCShape instanceof Text) {     // 텍스트인 경우
                        if (((Text) currentCShape).northHandle.isDragging()) {
                            ((Text) currentCShape).northHandle.drag(e.getPoint());
                        } else if (((Text) currentCShape).southHandle.isDragging()) {
                            ((Text) currentCShape).southHandle.drag(e.getPoint());
                        } else if (((Text) currentCShape).eastHandle.isDragging()) {
                            ((Text) currentCShape).eastHandle.drag(e.getPoint());
                        } else if (((Text) currentCShape).westHandle.isDragging()) {
                            ((Text) currentCShape).westHandle.drag(e.getPoint());

                        } else if (((Text) currentCShape).northEastHandle.isDragging()) {
                            ((Text) currentCShape).northEastHandle.drag(e.getPoint());
                        } else if (((Text) currentCShape).northWestHandle.isDragging()) {
                            ((Text) currentCShape).northWestHandle.drag(e.getPoint());
                        } else if (((Text) currentCShape).southEastHandle.isDragging()) {
                            ((Text) currentCShape).southEastHandle.drag(e.getPoint());
                        } else if (((Text) currentCShape).southWestHandle.isDragging()) {
                            ((Text) currentCShape).southWestHandle.drag(e.getPoint());

                        } else {
                            currentCShape.move(dx, dy);
                        }
                        mousePoint = e.getPoint();
                        repaint();
                        return;

                    }

                    currentCShape.move(dx, dy);
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
        System.out.println("도형추가");
        shapes.add(shape);
    }
}
