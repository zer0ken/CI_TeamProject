package org.client.gui.components;

import org.client.gui.models.AppViewModel.Listener;
import org.client.gui.shapes.Line;
import org.client.gui.shapes.Oval;
import org.client.gui.shapes.Shape;
import org.client.gui.shapes.Style;
import org.client.gui.shapes.Text;
import org.client.gui.models.AppViewModel;
import org.client.gui.UserAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

import static org.client.gui.Constants.CANVAS_SIZE;

public class Canvas extends ComponentJPanel {

    public Map<Long, Shape> shapes;
    private Stack<UserAction> undoStack;
    private Stack<UserAction> redoStack;
    private Point clickStartPoint;
    private final int DRAG_THRESHOLD = 3;
    private Point mousePoint;
    private Shape selectedShape;
    private Shape previousShape;
    //private int count = 0;


    public Canvas(AppViewModel appViewModel) {
        super(CANVAS_SIZE, appViewModel);
        setLayout(new BorderLayout());
        setBackground(Color.white);
        setFocusable(true);
        requestFocusInWindow();

        //shapes = Collections.synchronizedMap(new TreeMap<>());   // 도형 객체들을 저장하기 위해 맵 이용
        shapes = appViewModel.getShapes();
        undoStack = new Stack<>();
        redoStack = new Stack<>();

        appViewModel.addListener(Listener.USER_CREATION, this::createToolbar);

        appViewModel.addListener(Listener.SELECTION, this::selectSilently);
        appViewModel.addListener(Listener.SERVER_CREATION, this::createSilently);
        appViewModel.addListener(Listener.SERVER_MODIFICATION, this::modifySilently);
        appViewModel.addListener(Listener.SERVER_REMOVAL, this::removeSilently);

        getInputMap(WHEN_IN_FOCUSED_WINDOW).
            put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "removeShape");
        getActionMap().put("removeShape", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedShape != null) {
                    // DELETE 동작 스택에 저장
                    storeUndoStack(UserAction.Type.DELETE, selectedShape, null);
                    remove(selectedShape);   // 맵에서 도형 삭제 -> 다시 그림 -> 뷰모델에 도형 삭제 전달
                    selectedShape = null;
                    select(null);    // 선택된 도형 x -> 다시 그림 -> 뷰모델에 현재 선택된 도형 없음 전달
                    repaint();
                }
            }
        });

        getInputMap(WHEN_IN_FOCUSED_WINDOW).
            put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK), "unDo");
        getActionMap().put("unDo", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                unDo();
                repaint();
            }
        });

        getInputMap(WHEN_IN_FOCUSED_WINDOW).
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
                        appViewModel.select(selectedShape.getId());  // 뷰모델에 선택된 도형 변경 전달
                        if (selectedShape instanceof Line || selectedShape instanceof org.client.gui.shapes.Rectangle
                            || selectedShape instanceof Oval || selectedShape instanceof Text) {
                            selectedShape.allHandleStopDrag();
                            selectedShape.fineAndStartDrag(e.getPoint());
                            previousShape = selectedShape.copy();
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
                        // 맵에서 도형 변경 -> 다시 그림 -> 뷰모델에 도형 변경 전달
                        modify(selectedShape);

                        // 도형 변경 동작 스택에 저장
                        //changeShape(selectedShape, previousShape);
                        // CHANGE 동작 스택에 저장
                        storeUndoStack(UserAction.Type.CHANGE, selectedShape, previousShape);
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
                    if (selectedShape instanceof Line || selectedShape instanceof org.client.gui.shapes.Rectangle
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
        storeUndoStack(UserAction.Type.CREATE, shape, null);           // CREATE 동작 스택에 저장
        shapes.put(id, shape);
    }

    // 도형 제거 및 DELETE 동작 저장 (원래 캔버스에서 delete 키로 도형 삭제 시 썻던 메소드)
    public void removeShape(Long id) {
        storeUndoStack(UserAction.Type.DELETE, shapes.get(id), null);  // DELETE 동작 스택에 저장
        shapes.remove(id);
    }

    // CHANGE 동작 저장 (원래 드래그 종료 후 변경 된 도형 저장 시 썻던 메소드)
    public void changeShape(Shape targetShape, Shape previousShape) {
        storeUndoStack(UserAction.Type.CHANGE, targetShape, previousShape);
    }


    // CREATE, DELETE, CHANGE 동작을 스택에 저장
    public void storeUndoStack(UserAction.Type action, Shape targetShape, Shape previousShape) {
        if (previousShape != null) {        // CHANGE
            undoStack.push(new UserAction(action, targetShape.copy(), previousShape,
                null,null));
        } else {                            // CREATE, DELETE
            undoStack.push(new UserAction(action, targetShape.copy(), null,
                null,null));
        }
        while (!redoStack.isEmpty()) {
            redoStack.pop();
        }
    }

    // undo 메소드
    public void unDo() {
        if(!undoStack.isEmpty()) {
            UserAction act = undoStack.pop();
            if(act.getAction() == UserAction.Type.CREATE) {
                remove(act.getTargetShape());
                if(selectedShape != null && act.getTargetShape().getId() == selectedShape.getId()) {
                    selectedShape = null;
                    select(null);           // 뷰모델에 현재 선택된 도형 없음 전달
                }

            } else if (act.getAction() == UserAction.Type.DELETE) {
                create(act.getTargetShape());
                if(selectedShape != null && act.getTargetShape().getId() == selectedShape.getId()) {
                    selectedShape = act.getTargetShape();
                    appViewModel.select(selectedShape.getId());  // 뷰모델에 선택된 도형 변경 전달
                }

            } else if (act.getAction() == UserAction.Type.CHANGE) {
                modify(act.getPreviousShape());
                if (selectedShape != null && act.getTargetShape().getId() == selectedShape.getId()) {
                    selectedShape = act.getPreviousShape();
                    appViewModel.select(selectedShape.getId());  // 뷰모델에 선택된 도형 변경 전달
                }
                act = new UserAction(UserAction.Type.CHANGE, act.getPreviousShape(), act.getTargetShape(),
                    null, null);

            } else if (act.getAction() == UserAction.Type.STYLE_CHANGE) {
                // 스타일 변경된 것 되돌리기
            }

            redoStack.push(act);
        }
    }

    // redo 메소드
    public void reDo() {
        if(!redoStack.isEmpty()) {
            UserAction act = redoStack.pop();
            if (act.getAction() == UserAction.Type.CREATE) {
                create(act.getTargetShape());

            } else if (act.getAction() == UserAction.Type.DELETE) {
                remove(act.getTargetShape());
                if(selectedShape != null && act.getTargetShape().getId() == selectedShape.getId()) {
                    selectedShape = null;
                    select(null);           // 뷰모델에 현재 선택된 도형 없음 전달
                }

            } else if (act.getAction() == UserAction.Type.CHANGE) {
                modify(act.getTargetShape());
                if (selectedShape != null && act.getTargetShape().getId() == selectedShape.getId()) {
                    selectedShape = act.getPreviousShape();
                    appViewModel.select(selectedShape.getId());  // 뷰모델에 선택된 도형 변경 전달
                }
                act = new UserAction(UserAction.Type.CHANGE, act.getPreviousShape(), act.getTargetShape(),
                    null, null);

            } else if (act.getAction() == UserAction.Type.STYLE_CHANGE) {
                // 스타일 변경된 것 redo
            }

            undoStack.push(act);
        }
    }

    // 도형 스타일 수정
    public void changeShapeStyle(Long id, Style style) {
        if(selectedShape.getId() == id){
            selectedShape.setStyle(style);
            repaint();
        }
    }


    public void select(Shape shape) {
        if (shape == null) {
            appViewModel.select(-1);
        } else {
            appViewModel.select(shape.getId());
        }
//        selectSilently(shape);
    }


    public void create(Shape shape) {                     // 유저가 도형 생성할 때
        appViewModel.createByUser(shape);              // 맵에서 도형 생성 후 다른 컴포넌트에 전파
        createSilently(shape);                            // 캔버스에 반영
    }

    public void remove(Shape shape) {                     // 유저가 도형 지울 때
        appViewModel.removeByUser(shape.getId());      // 맵에서 도형 지우고 다른 컴포넌트에 전파
        removeSilently(shape);                            // 캔버스에 반영
    }

    public void modify(Shape modifiedShape) {                               // 유저가 도형 변경할 때
        appViewModel.modifyByUser(modifiedShape.getId(), modifiedShape); // 맵 도형변경 후 다른 컴포넌트에 전파
        modifySilently(modifiedShape);                                      // 캔버스에 반영
    }


    public Void createToolbar(Shape newShape) {                              // 유저가 툴바에서 도형 생성 시
        storeUndoStack(UserAction.Type.CREATE, newShape, null);  // CREATE 동작 스택에 저장
        repaint();
        return null;
    }

    private Void selectSilently(Shape selected) {
        // 2. 뷰모델의 맵에 선택된 도형이 바뀌어 있음 -> 캔버스에서 선택된 도형 변경 -> 리페인트

        selectedShape = appViewModel.getSelectedShape();
        repaint();
        return null;
    }

    private Void createSilently(Shape newShape) {
        // 2. 이미 뷰모델의 맵에 서버로 갔다가 돌아온 도형이 추가되있음, 다른 클라이언트의 뷰모델에도 추가됨
        // -> 캔버스에 추가된 도형 그림(리페인트)

        repaint();
        return null;
    }

    private Void removeSilently(Shape removedShape) {
        // 2. 이미 뷰모델의 맵에 서버로 갔다가 돌아온 도형이 삭제되있음, 다른 클라이언트의 뷰모델에도 삭제됨
        // -> 캔버스에 삭제된 도형 지움(리페인트)

        repaint();
        return null;
    }

    private Void modifySilently(Shape modifiedShape) {
        // 2. 이미 뷰모델의 맵에 서버로 갔다가 돌아온 도형이 수정되있음, 다른 클라이언트의 뷰모델에도 수정됨
        // -> 캔버스에 수정된 도형 반영(리페인트)

        repaint();
        return null;
    }

}
