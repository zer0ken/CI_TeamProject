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
    private Stack<Act> undoStack;
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

        //shapes = Collections.synchronizedMap(new TreeMap<>());   // 도형 객체들을 저장하기 위해 맵 이용
        shapes = shapesViewModel.getShapes();
        undoStack = new Stack<>();
        redoStack = new Stack<>();

        //shapesViewModel.addListener(ShapesViewModel.Listener.USER_CREATION, );

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
                    //removeShape(selectedShape.getId());
                    remove(selectedShape);   // 맵에서 도형 삭제 -> 다시 그림 -> 뷰모델에 도형 삭제 전달
                    selectedShape = null;
                    select(null);    // 선택된 도형 x -> 다시 그림 -> 뷰모델에 현재 선택된 도형 없음 전달
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
                        shapesViewModel.selectByCanvas(selectedShape.getId());  // 뷰모델에 선택된 도형 변경 전달
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
                select(null);           // 뷰모델에 현재 선택된 도형 없음 전달
                repaint();
            }

            public void mouseReleased(MouseEvent e) {
                if(clickStartPoint != null && selectedShape != null) {
                    int dx = e.getX() - clickStartPoint.x;
                    int dy = e.getY() - clickStartPoint.y;
                    double distance = Math.sqrt(dx * dx + dy * dy);

                    if (distance > DRAG_THRESHOLD) {
                        // 뷰모델에 최종 드래그 후 변경된 도형 정보 전달
                        shapesViewModel.modifyByUser(selectedShape.getId(), selectedShape);

                        // 도형 변경 동작 스택에 저장
                        //changeShape(selectedShape, previousShape);
                        modify(selectedShape, previousShape);
                    }
                }
            }
        });



        addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (selectedShape != null) {
                    int dx = e.getX() - mousePoint.x;
                    int dy = e.getY() - mousePoint.y;

                    // 인스턴스로 도형 특정
                    if (selectedShape instanceof Line || selectedShape instanceof Rectangle
                        || selectedShape instanceof Oval || selectedShape instanceof Text) {

                        selectedShape.DragOrMove(e.getPoint(), dx, dy);
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

    // 도형 추가 및 CREATE 동작 저장 (원래 툴바에서 썻던 메소드)
    public void addShape(Long id, Shape shape) {
        storeUndoStack(Act.Action.CREATE, shape, null);           // CREATE 동작 스택에 저장
        shapes.put(id, shape);
    }

    // 도형 제거 및 DELETE 동작 저장 (원래 캔버스에서 delete 키로 도형 삭제 시 썻던 메소드)
    public void removeShape(Long id) {
        storeUndoStack(Act.Action.DELETE, shapes.get(id), null);  // DELETE 동작 스택에 저장
        shapes.remove(id);
    }

    // CHANGE 동작 저장 (원래 드래그 종료 후 변경 된 도형 저장 시 썻던 메소드)
    public void changeShape(Shape targetShape, Shape previousShape) {
        storeUndoStack(Act.Action.CHANGE, targetShape, previousShape);
    }


    // CREATE, DELETE, CHANGE 동작을 스택에 저장
    public void storeUndoStack(Act.Action action, Shape targetShape, Shape previousShape) {
        Shape temp = null;
        if (targetShape instanceof Line) {
            temp = new Line();
        } else if (targetShape instanceof Rectangle) {
            temp = new Rectangle();
        } else if (targetShape instanceof Oval) {
            temp = new Oval();
        } else if (targetShape instanceof Text) {
            temp = new Text();
        }
        Objects.requireNonNull(temp).copy(targetShape);

        if (previousShape != null) {        // CHANGE
            undoStack.push(new Act(action, temp, previousShape,
                null,null));
        } else {                            // CREATE, DELETE
            undoStack.push(new Act(action, temp, null,
                null,null));
        }
        while (!redoStack.isEmpty()) {
            redoStack.pop();
        }
    }

    // 되돌리기 메소드
    public void unDo() {
        if(!undoStack.isEmpty()) {
            Act act = undoStack.pop();
            if(act.getAction() == Act.Action.CREATE) {
                shapes.remove(act.getTargetShape().getId());
                if(selectedShape != null && act.getTargetShape().getId() == selectedShape.getId()) {
                    selectedShape = null;
                    select(null);           // 뷰모델에 현재 선택된 도형 없음 전달
                }

            } else if (act.getAction() == Act.Action.DELETE) {
                shapes.put(act.getTargetShape().getId(), act.getTargetShape());
                if(selectedShape != null && act.getTargetShape().getId() == selectedShape.getId()) {
                    selectedShape = act.getTargetShape();
                    shapesViewModel.selectByCanvas(selectedShape.getId());  // 뷰모델에 선택된 도형 변경 전달
                }

            } else if (act.getAction() == Act.Action.CHANGE) {
                shapes.remove(act.getTargetShape().getId());
                shapes.put(act.getPreviousShape().getId(), act.getPreviousShape());
                if (selectedShape != null && act.getTargetShape().getId() == selectedShape.getId()) {
                    selectedShape = act.getPreviousShape();
                    shapesViewModel.selectByCanvas(selectedShape.getId());  // 뷰모델에 선택된 도형 변경 전달
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
        if(!redoStack.isEmpty()) {
            Act act = redoStack.pop();
            if (act.getAction() == Act.Action.CREATE) {
                shapes.put(act.getTargetShape().getId(), act.getTargetShape());

            } else if (act.getAction() == Act.Action.DELETE) {
                shapes.remove(act.getTargetShape().getId());
                if(selectedShape != null && act.getTargetShape().getId() == selectedShape.getId()) {
                    selectedShape = null;
                    select(null);           // 뷰모델에 현재 선택된 도형 없음 전달
                }

            } else if (act.getAction() == Act.Action.CHANGE) {
                shapes.remove(act.getTargetShape().getId());
                shapes.put(act.getPreviousShape().getId(), act.getPreviousShape());
                if (selectedShape != null && act.getTargetShape().getId() == selectedShape.getId()) {
                    selectedShape = act.getPreviousShape();
                    shapesViewModel.selectByCanvas(selectedShape.getId());  // 뷰모델에 선택된 도형 변경 전달
                }
                act = new Act(Act.Action.CHANGE, act.getPreviousShape(), act.getTargetShape(),
                    null, null);

            } else if (act.getAction() == Act.Action.STYLE_CHANGE) {
                // 스타일 변경된 것 redo
            }

            undoStack.push(act);
        }
    }


//    // 도형 선택 (도형목록 창에서 요청)
//    public void chooseShape(Shape shape) {
//        if (selectedShape != null) {
//            selectedShape = null;
//            shapesViewModel.selectByCanvas(-1);
//        }
//
//        Set<Long> keySet = shapes.keySet();
//        for(Long key : keySet) {
//            if (shapes.get(key).getId() == shape.getId()) {
//                selectedShape = shape;
//                shapesViewModel.selectByCanvas(selectedShape.getId());
//                //OnSelectionChanged();
//                repaint();
//            }
//        }
//    }

    // 도형 스타일 수정
    public void changeShapeStyle(Long id, Style style) {
        if(selectedShape.getId() == id){
            selectedShape.setStyle(style);
            repaint();
        }
    }


//    // 선택된 도형 변경
//    public Shape OnSelectionChanged() {
//        return selectedShape;
//    }



    public void select(Shape shape) {
        selectSilently(shape);
        if (shape == null) {
            shapesViewModel.selectByCanvas(-1);
        } else {
            shapesViewModel.selectByCanvas(shape.getId());
        }
    }

    public void create(Shape newShape) {
        createSilently(newShape);
        shapesViewModel.createByUser(newShape);
    }

    public void remove(Shape shape) {
        removeSilently(shape);
        shapesViewModel.removeByUser(shape.getId());
    }

    public void modify(Shape modifiedShape, Shape previousShape) {
        modifySilently(modifiedShape, previousShape);
        shapesViewModel.modifyByUser(modifiedShape, previousShape);
    }


    private Void selectSilently(Shape selected) {
        // TODO: 도형 선택
        // 도형 편집 창에서 도형 선택 -> 맵에 선택된 도형 업데이트
        // -> 다른 컴포넌트에 전파 -> 캔버스에 선택된 도형 그림
        shapesViewModel.selectByShapesWindow(selected.getId());
        repaint();
        return null;
    }

    private Void createSilently(Shape newShape) {
        // TODO: 도형 추가
        // 서버에서 도형 추가 요청 -> 뷰모델의 맵에 도형 추가
        // -> 다른 컴포넌트에 전파 -> 캔버스에 추가된 도형 그림
        shapesViewModel.createByServer(newShape);
        repaint();
        return null;
    }

    private Void removeSilently(Shape removedShape) {
        // TODO: 도형 삭제
        // 서버에서 도형 삭제 요청 -> 뷰모델의 맵에서 도형 삭제
        // -> 다른 컴포넌트에 전파 -> 캔버스에서 삭제된 도형 지움
        shapesViewModel.removeByServer(removedShape.getId());
        repaint();
        return null;
    }

    private Void modifySilently(Shape modifiedShape) {
        // TODO: 도형 수정
        // 서버에서 도형 변경 요청 -> 뷰모델의 맵에서 변경 전 도형을 변경 후 도형으로 수정
        // -> 다른 컴포넌트에 전파
        shapesViewModel.modifyByServer(modifiedShape.getId(), modifiedShape);
        return null;
    }

}
