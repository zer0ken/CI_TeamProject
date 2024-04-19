package org.example.components;

import org.example.shapes.Rectangle;
import org.example.shapes.Shape;
import org.example.shapes.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.function.Function;

import static org.example.components._Constants.CANVAS_SIZE;

public class Canvas extends _ComponentJPanel {
    //public java.util.List<Shape> shapes;
    public Map<Long, Shape> shapes;
    public Stack<Act> _shapes;
    private Point mousePoint;
    private Shape currentCShape;
    private Shape currentPreShape;
    private int count = 0;
    private Function<Shape, Void> onSelected;

    Canvas() {
        super(CANVAS_SIZE);
        setLayout(new BorderLayout());
        setBackground(Color.white);
        setFocusable(true);
        requestFocusInWindow();

        shapes = Collections.synchronizedMap(new TreeMap<>());
        _shapes = new Stack<>();
        currentPreShape = null;

        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).
            put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "removeShape");
        getActionMap().put("removeShape", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentCShape != null) {
//                    removeShape(currentCShape);
                    currentCShape = null;
                    repaint();
                }
            }
        });

        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).
            put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK), "unDo");
        getActionMap().put("unDo", new AbstractAction() {
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
                        OnSelectionChanged();
                        //currentPreShape = new Shape(currentCShape)
                        repaint();
                        return;
                    }
                }
                if(currentCShape != null){
                    currentCShape.allHandleStopDrag();
                }
                currentCShape = null;
                OnSelectionChanged();
                repaint();
            }

            public void mouseReleased(MouseEvent e) {
                // 드래그 이벤트 종료 시 실행할 코드
                System.out.println("드래그 이벤트 종료");
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
        Set<Long> keySet = shapes.keySet();
        for(Long key : keySet) {
            shapes.get(key).draw(g);
        }

        if (currentCShape != null) {
            currentCShape.drawSelection(g);
        }
    }

    // 도형 선택
    public void chooseShape(Shape shape) {
        if (currentCShape != null) {
            currentCShape = null;
        }

        Set<Long> keySet = shapes.keySet();
        for(Long key : keySet) {
            if (shapes.get(key).getId() == shape.getId()) {
                currentCShape = shape;
                OnSelectionChanged();
                repaint();
            }
        }
    }

    // 도형 수정
    public void modifyShape(Shape shape) {
        if (currentCShape != null) {
            currentCShape = null;
        }

        Set<Long> keySet = shapes.keySet();
        for(Long key : keySet) {
            if (shapes.get(key).getId() == shape.getId()) {
                currentCShape = shape;
                OnSelectionChanged();
                repaint();
            }
        }
    }

    // 도형 추가
    public void addShape(Long id, Shape shape) {
        shapes.put(id, shape);
        _shapes.push(new Act(Act.Action.CREATE, shape, null, null));
    }

    // 도형 삭제
    public void removeShape(Long id) {
        _shapes.push(new Act(Act.Action.DELETE, shapes.get(id), null, null));
        shapes.remove(id);
        OnSelectionChanged();
    }

    // 선택된 도형 변경
    public Shape OnSelectionChanged() {
        return currentCShape;
    }

    public void unDo() {
//        if(!_shapes.isEmpty()) {
//            Act act = _shapes.pop();
//            if(act.getAction() == Act.Action.CREATE) {
//                shapes.remove(act.getShapeTarget());
//                if(act.getShapeTarget() == currentCShape) {
//                    currentCShape = null;
//                }
//            } else if (act.getAction() == Act.Action.DELETE) {
//                shapes.add(act.getShapeTarget());
//            } else if (act.getAction() == Act.Action.MODIFY) {
//                shapes.remove(act.getShapeTarget());
//                if(act.getShapeTarget() == currentCShape) {
//                    currentCShape = null;
//                }
//                shapes.add(act.getPreShape());
//            } else if (act.getAction() == Act.Action.STYLE_CHANGE) {
//                // 스타일 변경된 것 되돌리기
//            }
//        }
    }

    // TODO: 선택이 바뀔 때마다 이 함수 호출 (선택 해제되면 null)
    //       호출은 onSelected.apply(shape); 이렇게 하시면 돼요!
    public void setOnSelected(Function<Shape, Void> onSelected) {
        this.onSelected = onSelected;
    }
}
