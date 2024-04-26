package org.example.components;

import org.example.ShapesViewModel;
import org.example.act.Act;
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

import static org.example.components._Constants.CANVAS_SIZE;

public class Canvas extends _ComponentJPanel {

    public Map<Long, Shape> shapes;
    public Stack<Act> undoStack;
    private Stack<Act> redoStack;
    private Point clickStartPoint;
    private final int DRAG_THRESHOLD = 3;
    private Point mousePoint;
    private Shape selectedShape;
    private Shape previousShape;
    //private int count = 0;


    Canvas(ShapesViewModel shapesViewModel) {
        super(CANVAS_SIZE, shapesViewModel);
        setLayout(new BorderLayout());
        setBackground(Color.white);
        setFocusable(true);
        requestFocusInWindow();

        shapes = Collections.synchronizedMap(new TreeMap<>());   // 도형 객체들을 저장하기 위해 맵 이용
        undoStack = new Stack<>();
        redoStack = new Stack<>();

        shapesViewModel.addListener(ShapesViewModel.Listener.SHAPES_WINDOW_SELECTION, this::selectSilently);
        shapesViewModel.addListener(ShapesViewModel.Listener.SERVER_CREATION, this::createSilently);
        shapesViewModel.addListener(ShapesViewModel.Listener.SERVER_MODIFICATION, this::modifySilently);
        shapesViewModel.addListener(ShapesViewModel.Listener.SERVER_REMOVAL, this::removeSilently);

        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).
            put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "removeShape");
        getActionMap().put("removeShape", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedShape != null) {
                    removeShape(selectedShape.getId());
                    selectedShape = null;
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

        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).
            put(KeyStroke.getKeyStroke(KeyEvent.VK_Z,
                KeyEvent.CTRL_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK), "reDo");
        getActionMap().put("reDo", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reDo();
                repaint();
            }
        });

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                Set<Long> keySet = shapes.keySet();
                for(Long key : keySet) {
                    if (shapes.get(key).contains(e.getPoint())) {
                        clickStartPoint = e.getPoint();
                        mousePoint = e.getPoint();

                        selectedShape = shapes.get(key);
                        if (selectedShape instanceof Line || selectedShape instanceof Rectangle
                            || selectedShape instanceof Oval || selectedShape instanceof Text) {
                            selectedShape.allHandleStopDrag();
                            selectedShape.fineAndStartDrag(e.getPoint());

                            if (selectedShape instanceof Line) {
                                previousShape = new Line();
                            } else if (selectedShape instanceof Rectangle) {
                                previousShape = new Rectangle();
                            } else if (selectedShape instanceof Oval) {
                                previousShape = new Oval();
                            } else if (selectedShape instanceof Text) {
                                previousShape = new Text();
                            }
                            previousShape.copy(selectedShape);
                        }
                        repaint();
                        return;
                    }
                }

                // 이미 도형이 선택된 상태에서 다음번에 클릭한 지점이 도형이 아닌 경우
                if(selectedShape != null){
                    selectedShape.allHandleStopDrag();
                }
                selectedShape = null;
                repaint();
            }

            public void mouseReleased(MouseEvent e) {
                if(clickStartPoint != null && selectedShape != null) {
                    int dx = e.getX() - clickStartPoint.x;
                    int dy = e.getY() - clickStartPoint.y;
                    double distance = Math.sqrt(dx * dx + dy * dy);

                    if (distance > DRAG_THRESHOLD) {
                        undoStack.push(new Act(Act.Action.CHANGE, selectedShape, previousShape, null, null));
                        while (!redoStack.isEmpty()) {
                            redoStack.pop();
                        }
                    }
                }
            }
        });



        addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                // 마우스 이동에 따라 해당 도형을 이동시키면서 다시 그림
                if (selectedShape != null) {            // 마우스를 드래그할 때, 현재 선택된 도형이 있을 경우
                    int dx = e.getX() - mousePoint.x;     // x좌표 차이
                    int dy = e.getY() - mousePoint.y;     // y좌표 차이

                    // 인스턴스로 도형 특정
                    if (selectedShape instanceof Line || selectedShape instanceof Rectangle
                        || selectedShape instanceof Oval || selectedShape instanceof Text) {

                        selectedShape.DragOrMove(e.getPoint(), dx, dy);    // 선택된 도형 위치 정보 수정
                        mousePoint = e.getPoint();
                        repaint();
                    }

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
        if (selectedShape != null) {
            selectedShape.drawSelected(g);
        }
    }

    // 도형 추가 및 CREATE 행동 저장
    public void addShape(Long id, Shape shape) {
        Shape temp = null;
        if (shape instanceof Line) {
            temp = new Line();
        } else if (shape instanceof Rectangle) {
            temp = new Rectangle();
        } else if (shape instanceof Oval) {
            temp = new Oval();
        } else if (shape instanceof Text) {
            temp = new Text();
        }
        Objects.requireNonNull(temp).copy(shape);           // 도형 복사
        undoStack.push(new Act(Act.Action.CREATE, temp, null, null, null));
        while (!redoStack.isEmpty()) {
            redoStack.pop();
        }
        shapes.put(id, shape);
    }

    // 도형 제거 및 DELETE 행동 저장
    public void removeShape(Long id) {
        Shape temp = null;
        if (shapes.get(id) instanceof Line) {
            temp = new Line();
        } else if (shapes.get(id) instanceof Rectangle) {
            temp = new Rectangle();
        } else if (shapes.get(id) instanceof Oval) {
            temp = new Oval();
        } else if (shapes.get(id) instanceof Text) {
            temp = new Text();
        }
        Objects.requireNonNull(temp).copy(shapes.get(id));           // 도형 복사
        undoStack.push(new Act(Act.Action.DELETE, shapes.get(id), null, null,null));
        while (!redoStack.isEmpty()) {
            redoStack.pop();
        }
        shapes.remove(id);
    }

    // 되돌리기 메소드
    public void unDo() {
        if(!undoStack.isEmpty()) {          // 빈 스택이 아닌 경우
            Act act = undoStack.pop();        // 하나 꺼내옴
            if(act.getAction() == Act.Action.CREATE) {            // 생성인 경우
                shapes.remove(act.getTargetShape().getId());
                if(selectedShape != null && act.getTargetShape().getId() == selectedShape.getId()) {
                    selectedShape = null;
                }
            } else if (act.getAction() == Act.Action.DELETE) {    // 삭제인 경우
                shapes.put(act.getTargetShape().getId(), act.getTargetShape());
                if(selectedShape != null && act.getTargetShape().getId() == selectedShape.getId()) {
                    selectedShape = act.getTargetShape();
                }
            } else if (act.getAction() == Act.Action.CHANGE) {    // 변경인 경우
                shapes.remove(act.getTargetShape().getId());
                shapes.put(act.getPreviousShape().getId(), act.getPreviousShape());
                if (selectedShape != null && act.getTargetShape().getId() == selectedShape.getId()) {
                    selectedShape = act.getPreviousShape();
                }
                act = new Act(Act.Action.CHANGE, act.getPreviousShape(), act.getTargetShape(),
                    null, null);
            } else if (act.getAction() == Act.Action.STYLE_CHANGE) {
                // 스타일 변경된 것 되돌리기
            }

            redoStack.push(act);
        }
    }

    // redo 메소드
    public void reDo() {
        if(!redoStack.isEmpty()) {          // 빈 스택이 아닌 경우
            Act act = redoStack.pop();        // 하나 꺼내옴
            if (act.getAction() == Act.Action.CREATE) {           // 생성인 경우
                shapes.put(act.getTargetShape().getId(), act.getTargetShape());
                System.out.println("직선 다시 생성");
            } else if (act.getAction() == Act.Action.DELETE) {    // 삭제인 경우
                shapes.remove(act.getTargetShape().getId());
                if(selectedShape != null && act.getTargetShape().getId() == selectedShape.getId()) {
                    selectedShape = null;
                }
            } else if (act.getAction() == Act.Action.CHANGE) {    // 변경인 경우
                shapes.remove(act.getTargetShape().getId());
                shapes.put(act.getPreviousShape().getId(), act.getPreviousShape());
                if (selectedShape != null && act.getTargetShape().getId() == selectedShape.getId()) {
                    selectedShape = act.getPreviousShape();
                }
                act = new Act(Act.Action.CHANGE, act.getPreviousShape(), act.getTargetShape(),
                    null, null);
            } else if (act.getAction() == Act.Action.STYLE_CHANGE) {
                // 스타일 변경된 것 redo
            }

            undoStack.push(act);
        }
    }


    // 도형 선택
    public void chooseShape(Shape shape) {
        if (selectedShape != null) {
            selectedShape = null;
        }

        Set<Long> keySet = shapes.keySet();
        for(Long key : keySet) {
            if (shapes.get(key).getId() == shape.getId()) {
                selectedShape = shape;
                OnSelectionChanged();
                repaint();
            }
        }
    }

    // 도형 스타일 수정
    public void changeShapeStyle(Long id, Style style) {
        if(selectedShape.getId() == id){
            selectedShape.setStyle(style);
            repaint();
        }
    }


    // 선택된 도형 변경
    public Shape OnSelectionChanged() {
        return selectedShape;
    }



    public void select(Shape shape) {
        selectSilently(shape);
        shapesViewModel.selectByCanvas(shape.getId());
    }

    public void create(Shape newShape) {
        createSilently(newShape);
        shapesViewModel.createByUser(newShape);
    }

    public void modify(long id, Shape modifiedShape) {
        modifySilently(modifiedShape);
        shapesViewModel.modifyByUser(id, modifiedShape);
    }

    public void remove(Shape shape) {
        removeSilently(shape);
        shapesViewModel.removeByUser(shape.getId());
    }

    private Void selectSilently(Shape selected) {
        // TODO: 도형 선택

        return null;
    }

    private Void createSilently(Shape newShape) {
        // TODO: 도형 추가

        return null;
    }

    private Void modifySilently(Shape modifiedShape) {
        // TODO: 도형 수정

        return null;
    }

    private Void removeSilently(Shape removedShape) {
        // TODO: 도형 삭제

        return null;
    }
}
