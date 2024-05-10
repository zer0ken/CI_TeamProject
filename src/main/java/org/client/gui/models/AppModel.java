package org.client.gui.models;

import org.client.gui.shapes.Shape;
import org.client.gui.shapes.Style;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.function.Function;

import static org.client.gui.Constants.*;
import static org.client.gui.models.AppModel.Listener.*;


public class AppModel {
    private static AppModel instance = null;

    private String myself;

    private final Map<String, Shape> shapes;
    private Shape selectedShape = null;

    private final Stack<UserAction> undoStack;
    private final Stack<UserAction> redoStack;

    private int lineWidth = DEFAULT_LINE_WIDTH;
    private Color lineColor = DEFAULT_LINE_COLOR;
    private Color fillColor = DEFAULT_FILL_COLOR;
    private int textSize = DEFAULT_TEXT_SIZE;
    private Color textColor = DEFAULT_TEXT_COLOR;
    private String textContent = STYLE_DEFAULT_TEXT_CONTENT;

    public enum Listener {
        JOIN(null),
        LEAVE(null),
        SET_NAME(null),
        SELECTION(null),
        UPDATE(null),
            CREATION(UPDATE),
                USER_CREATION(CREATION),
                SERVER_CREATION(CREATION),
            MODIFICATION(UPDATE),
                USER_MODIFICATION(MODIFICATION),
                SERVER_MODIFICATION(MODIFICATION),
            REMOVAL(UPDATE),
                USER_REMOVAL(REMOVAL),
                SERVER_REMOVAL(REMOVAL);

        private final Listener parent;

        Listener(Listener o) {
            parent = o;
        }

        public boolean includes(Listener o) {
            for (; o != null; o = o.parent)
                if (o == this) return true;
            return false;
        }
    }

    public static class StringListener {
    }

    private final ArrayList<Function<String, Void>> joinListeners;
    private final ArrayList<Function<String, Void>> leaveListeners;
    private final ArrayList<Function<String, Void>> setNameListeners;

    private final ArrayList<Function<Shape, Void>> selectionListeners;

    // 사용자가 도형을 생성, 수정, 제거하면 클라이언트 앱 내의 모든 컴포넌트 및 **서버**에 전파되어야 함
    private final ArrayList<Function<Shape, Void>> userCreationListeners;
    private final ArrayList<Function<Shape, Void>> userModificationListeners;
    private final ArrayList<Function<Shape, Void>> userRemovalListeners;

    // 서버에서 도형을 생성, 수정 제거하도록 요청하면 클라이언트 앱 내의 모든 컴포넌트에 전파되어야 함.
    private final ArrayList<Function<Shape, Void>> serverCreationListeners;
    private final ArrayList<Function<Shape, Void>> serverModificationListeners;
    private final ArrayList<Function<Shape, Void>> serverRemovalListeners;

    private AppModel() {
        joinListeners = new ArrayList<>();
        leaveListeners = new ArrayList<>();
        setNameListeners = new ArrayList<>();

        selectionListeners = new ArrayList<>();
        userCreationListeners = new ArrayList<>();
        userModificationListeners = new ArrayList<>();
        userRemovalListeners = new ArrayList<>();
        serverCreationListeners = new ArrayList<>();
        serverModificationListeners = new ArrayList<>();
        serverRemovalListeners = new ArrayList<>();

        shapes = Collections.synchronizedMap(new TreeMap<>());
        undoStack = new Stack<>();
        redoStack = new Stack<>();

        instance = this;
    }

    public static AppModel getInstance() {
        if (instance == null) {
            return new AppModel();
        }
        return instance;
    }

    public void addStringListener(Listener type, Function<String, Void> callback) {
        if (type.includes(JOIN))
            joinListeners.add(callback);
        if (type.includes(LEAVE))
            leaveListeners.add(callback);
        if (type.includes(SET_NAME))
            setNameListeners.add(callback);
    }

    public void addListener(Listener type, Function<Shape, Void> callback) {
        if (type.includes(SELECTION))
            selectionListeners.add(callback);
        if (type.includes(USER_CREATION))
            userCreationListeners.add(callback);
        if (type.includes(SERVER_CREATION))
            serverCreationListeners.add(callback);
        if (type.includes(USER_MODIFICATION))
            userModificationListeners.add(callback);
        if (type.includes(SERVER_MODIFICATION))
            serverModificationListeners.add(callback);
        if (type.includes(USER_REMOVAL))
            userRemovalListeners.add(callback);
        if (type.includes(SERVER_REMOVAL))
            serverRemovalListeners.add(callback);
    }

    public void join(String name) {
        notify(joinListeners, name);
    }

    public void leave(String name) {
        notify(leaveListeners, name);
    }

    public void createByUser(Shape shape) {
        add(shape);
        notify(userCreationListeners, shape);
    }

    public void createByServer(Shape shape) {
        if (shapes.containsKey(shape.getId())) {
            return;
        }
        add(shape);
        notify(serverCreationListeners, shape);
    }

    public void modifyByUser(Shape shape) {
        modify(shape);
        notify(userModificationListeners, shape);
    }

    public void modifyByServer(Shape shape) {
        modify(shape);
        notify(serverModificationListeners, shape);
    }

    public void removeByUser(String id) {
        Shape removed = remove(id);
        notify(userRemovalListeners, removed);
    }

    public void removeByServer(String id) {
        Shape removed = remove(id);
        notify(serverRemovalListeners, removed);
    }

    private void notify(ArrayList<Function<Shape, Void>> listeners, Shape shape) {
        listeners.forEach(f -> SwingUtilities.invokeLater(() -> f.apply(shape)));
        printDebugInfo();
    }

    private void notify(ArrayList<Function<String, Void>> listeners, String string, StringListener... ignored) {
        listeners.forEach(f -> SwingUtilities.invokeLater(() -> f.apply(string)));
        printDebugInfo();
    }

    private void printDebugInfo() {
        System.out.println("### 현재 뷰모델");
        System.out.println("selected: " + selectedShape);
        shapes.forEach((i, s) -> System.out.println("\t" + i + ": " + s.toString()));
        System.out.println("###");
    }

    public void setMyself(String name) {
        myself = name;
        notify(setNameListeners, name);
    }

    public void select(Shape shape) {
        selectedShape = shape;
        notify(selectionListeners, selectedShape);
    }

    private void add(Shape shape) {
        shapes.put(shape.getId(), shape);
    }

    private void modify(Shape shape) {
        shapes.put(shape.getId(), shape);
        if (selectedShape != null && selectedShape.equals(shape)) {
            select(shape);
        }
    }

    private Shape remove(String id) {
        if (!shapes.containsKey(id)) {
            return null;
        }
        Shape removedShape = shapes.remove(id);
        if (selectedShape.equals(removedShape)) {
            select(null);
        }
        return removedShape;
    }

    // CREATE, DELETE, MODIFY, STYLE_MODIFY 동작을 스택에 저장
    public void storeUndoStack(UserAction.Type action, Shape targetShape, Shape previousShape) {
        if (previousShape != null) {        // CHANGE
            undoStack.push(new UserAction(action, targetShape.copy(), previousShape));
        } else {                            // CREATE, DELETE
            undoStack.push(new UserAction(action, targetShape.copy(), null));
        }
        redoStackEmptying();
    }

    // undo 메소드
    public void unDo() {
        if (!undoStack.isEmpty()) {
            UserAction act = undoStack.pop();
            if(act.getAction() == UserAction.Type.CREATE) {
                removeByUser(act.getTargetShape().getId());
                if(act.getTargetShape().equals(selectedShape)) {
                    select(null);
                }
            } else if (act.getAction() == UserAction.Type.DELETE) {
                createByUser(act.getTargetShape());
                if(act.getTargetShape().equals(selectedShape)) {
                    select(act.getTargetShape());
                }
            } else if (act.getAction() == UserAction.Type.MODIFY
                || act.getAction() == UserAction.Type.STYLE_MODIFY) {
                modifyByUser(act.getPreviousShape());
                if (act.getTargetShape().equals(selectedShape)) {
                    select(act.getPreviousShape());     // 변경전 도형 선택
                }
                act = new UserAction(UserAction.Type.MODIFY, act.getPreviousShape(), act.getTargetShape());
            }
            redoStack.push(act);
        }
    }

    // redo 메소드
    public void reDo() {
        if (!redoStack.isEmpty()) {
            UserAction act = redoStack.pop();
            if (act.getAction() == UserAction.Type.CREATE) {
                createByUser(act.getTargetShape());

            } else if (act.getAction() == UserAction.Type.DELETE) {
                removeByUser(act.getTargetShape().getId());
                if (act.getTargetShape().equals(selectedShape)) {
                    select(null);
                }

            } else if (act.getAction() == UserAction.Type.MODIFY
                    || act.getAction() == UserAction.Type.STYLE_MODIFY) {
                modifyByUser(act.getPreviousShape());
                if (act.getTargetShape().equals(selectedShape)) {
                    select(act.getPreviousShape());     // 변경전 도형 선택
                }
                act = new UserAction(UserAction.Type.MODIFY, act.getPreviousShape(), act.getTargetShape());

            }

            undoStack.push(act);
        }
    }

    public void redoStackEmptying() {
        while (!redoStack.isEmpty()) {
            redoStack.pop();
        }
    }

    public Map<String, Shape> getShapes() {
        return Collections.unmodifiableMap(shapes);
    }

    public Shape getSelectedShape() {
        return selectedShape;
    }

    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
    }

    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public Style getStyle() {
        return new Style(lineWidth, lineColor, fillColor, textSize, textColor, textContent);
    }

    public String getMyself() {
        return myself;
    }
}
