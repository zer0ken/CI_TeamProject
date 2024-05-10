package org.client.gui.components;

import org.client.gui.models.AppModel.Listener;
import org.client.gui.shapes.Line;
import org.client.gui.shapes.Oval;
import org.client.gui.shapes.Shape;
import org.client.gui.shapes.Style;
import org.client.gui.shapes.Text;
import org.client.gui.models.UserAction;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

import static org.client.gui.Constants.CANVAS_SIZE;

public class Canvas extends ComponentJPanel {

    public Map<Long, Shape> shapes;
    private Point clickStartPoint;
    private final int DRAG_THRESHOLD = 3;
    private Point mousePoint;
    private Shape selectedShape;
    private Shape previousShape;
    //private int count = 0;


    public Canvas() {
        super(CANVAS_SIZE);
        setLayout(new BorderLayout());
        setBackground(Color.white);
        setFocusable(true);
        requestFocusInWindow();

        shapes = appModel.getShapes();
        appModel.addListener(Listener.USER_CREATION, this::createSilently);
        appModel.addListener(Listener.USER_RECREATION, this::createSilently);
        appModel.addListener(Listener.USER_REMOVAL, this::removeSilently);

        appModel.addListener(Listener.SELECTION, this::selectSilently);
        appModel.addListener(Listener.SERVER_CREATION, this::createSilently);
        appModel.addListener(Listener.SERVER_RECREATION, this::createSilently);
        appModel.addListener(Listener.SERVER_MODIFICATION, this::modifySilently);
        appModel.addListener(Listener.SERVER_REMOVAL, this::removeSilently);

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                Set<Long> keySet = shapes.keySet();
                for(Long key : keySet) {
                    if (shapes.get(key).contains(e.getPoint())) {
                        clickStartPoint = e.getPoint();
                        mousePoint = e.getPoint();

                        selectedShape = shapes.get(key);
                        appModel.select(selectedShape.getId());  // 뷰모델에 선택된 도형 변경 전달
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

                        // CHANGE 동작 앱모델의 스택에 저장
                        appModel.storeUndoStack(UserAction.Type.MODIFY, selectedShape, previousShape);
                        appModel.redoStackEmptying();
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


    // 도형 스타일 수정
    public void changeShapeStyle(Long id, Style style) {
        if(selectedShape.getId() == id){
            selectedShape.setStyle(style);
            repaint();
        }
    }


    public void select(Shape shape) {           // 캔버스에서 도형 선택 시
        if (shape == null) {                    // 도형이 아닐 시 앱모델에 0 전달
            appModel.select(0);
        } else {                                // 선택된 도형 id 앱모델에 전달
            appModel.select(shape.getId());
        }
    }

    public void modify(Shape modifiedShape) {                           // 유저가 도형 변경할 때
        appModel.modifyByUser(modifiedShape.getId(), modifiedShape);    // 맵 도형변경 후 다른 컴포넌트에 전파
        modifySilently(modifiedShape);                                  // 캔버스에 반영
    }


    private Void selectSilently(Shape selected) {
        selectedShape = appModel.getSelectedShape();
        repaint();
        return null;
    }

    private Void createSilently(Shape newShape) {
        repaint();
        return null;
    }

    private Void removeSilently(Shape removedShape) {
        repaint();
        return null;
    }

    private Void modifySilently(Shape modifiedShape) {
        repaint();
        return null;
    }

}
