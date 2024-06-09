package org.client.gui.components;

import org.client.gui.models.AppModel;
import org.client.gui.models.AppModel.Listener;
import org.client.gui.shapes.Rectangle;
import org.client.gui.shapes.Shape;
import org.client.gui.shapes.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;
import java.util.Set;

public class Canvas extends JPanel {
    private final AppModel appModel = AppModel.getInstance();

    public Map<String, Shape> shapes;
    private Point clickStartPoint;
    private final int DRAG_THRESHOLD = 3;
    private Point mousePoint;
    private Shape selectedShape;
    //private int count = 0;


    public Canvas() {
        setLayout(new BorderLayout());
        setBackground(Color.white);
        setFocusable(true);
        requestFocusInWindow();

        shapes = appModel.getShapes();
        appModel.addListener(Listener.UPDATE, unused -> {
            repaint();
            return null;
        });
        appModel.addListener(Listener.SELECTION, selected -> {
            selectedShape = selected;
            repaint();
            return null;
        });
        appModel.addVoidListener(Listener.CLEAR, unused -> {
            repaint();
            return null;
        });

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                Set<String> keySet = shapes.keySet();
                for(String key : keySet) {
                    if (shapes.get(key).contains(e.getPoint())) {
                        clickStartPoint = e.getPoint();
                        mousePoint = e.getPoint();

                        selectedShape = shapes.get(key);
                        appModel.select(selectedShape);  // 뷰모델에 선택된 도형 변경 전달1
                        if (selectedShape instanceof Line || selectedShape instanceof Rectangle
                            || selectedShape instanceof Oval || selectedShape instanceof Text) {
                            selectedShape.allHandleStopDrag();
                            selectedShape.fineAndStartDrag(e.getPoint());
                        }
                        return;
                    }
                }

                // 이미 도형이 선택된 상태에서 다음번에 클릭한 지점이 도형이 아닌 경우
                if(selectedShape != null){
                    selectedShape.allHandleStopDrag();
                }
                selectedShape = null;
                appModel.select(null);           // 뷰모델에 현재 선택된 도형 없음 전달
            }

            public void mouseReleased(MouseEvent e) {
                if(clickStartPoint != null && selectedShape != null) {
                    int dx = e.getX() - clickStartPoint.x;
                    int dy = e.getY() - clickStartPoint.y;
                    double distance = Math.sqrt(dx * dx + dy * dy);

                    // 맵에서 도형 변경 -> 다시 그림 -> 뷰모델에 도형 변경 전달
                    appModel.modifyByUser(selectedShape);
                    appModel.setModifying(false);
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

                    appModel.setModifying(true);
                    // 맵에서 도형 변경 -> 다시 그림 -> 뷰모델에 도형 변경 전달
                    appModel.modifyByUser(selectedShape);
                }
            }
        });
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.WHITE);
        for(String key : shapes.keySet()) {
            shapes.get(key).draw(g);
        }
        if (selectedShape != null) {
            selectedShape.drawSelected(g);
        }
    }
}
