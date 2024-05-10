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

    private final Map<Long, Shape> shapes;
    private Shape selectedShape = null;
    private Stack<UserAction> undoStack;
    private Stack<UserAction> redoStack;

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
                USER_RECREATION(CREATION),
                SERVER_CREATION(CREATION),
                SERVER_RECREATION(CREATION),
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
    private final ArrayList<Function<Shape, Void>> userReCreationListeners;
    private final ArrayList<Function<Shape, Void>> userModificationListeners;
    private final ArrayList<Function<Shape, Void>> userRemovalListeners;

    // 서버에서 도형을 생성, 수정 제거하도록 요청하면 클라이언트 앱 내의 모든 컴포넌트에 전파되어야 함.
    private final ArrayList<Function<Shape, Void>> serverCreationListeners;
    private final ArrayList<Function<Shape, Void>> serverReCreationListeners;

    private final ArrayList<Function<Shape, Void>> serverModificationListeners;
    private final ArrayList<Function<Shape, Void>> serverRemovalListeners;

    private AppModel() {
        joinListeners = new ArrayList<>();
        leaveListeners = new ArrayList<>();
        setNameListeners = new ArrayList<>();

        selectionListeners = new ArrayList<>();
        userCreationListeners = new ArrayList<>();
        userReCreationListeners = new ArrayList<>();
        userModificationListeners = new ArrayList<>();
        userRemovalListeners = new ArrayList<>();
        serverCreationListeners = new ArrayList<>();
        serverReCreationListeners = new ArrayList<>();
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
        if (type.includes(USER_RECREATION))
            userReCreationListeners.add(callback);
        if (type.includes(SERVER_CREATION))
            serverCreationListeners.add(callback);
        if (type.includes(SERVER_RECREATION))
            serverReCreationListeners.add(callback);
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
        propagate(joinListeners, name);
    }

    public void leave(String name) {
        propagate(leaveListeners, name);
    }

    public void createByUser(Shape shape) {
        add(shape);
        propagate(userCreationListeners, shape);
    }

    public void createByServer(long newId, Shape shape) {
        long oldId = shape.getId();
        Shape newShape = shape;
        Shape oldSelectedShape = selectedShape;
        if (newId != oldId) {
            newShape = shape.copy(newId);
            storeUndoStack(UserAction.Type.CREATE, newShape, null);
            removeByServer(oldId);
        }
        add(newShape);
        propagate(serverCreationListeners, newShape);
        if (oldSelectedShape != null && oldSelectedShape.getId() == oldId) {
            select(newId);
        }
    }

    public void reCreateByUser(Shape shape) {
        add(shape);
        propagate(userReCreationListeners, shape);
    }

    public void reCreateByServer(Shape shape) {
        add(shape);
        propagate(serverReCreationListeners, shape);
    }

    public void modifyByUser(long id, Shape shape) {
        modify(id, shape);
        propagate(userModificationListeners, shape);
    }

    public void modifyByServer(long id, Shape shape) {
        modify(id, shape);
        propagate(serverModificationListeners, shape);
    }

    public void removeByUser(long id) {
        Shape removed = remove(id);
        propagate(userRemovalListeners, removed);
    }

    public void removeByServer(long id) {
        Shape removed = remove(id);
        propagate(serverRemovalListeners, removed);
    }

    private void propagate(ArrayList<Function<Shape, Void>> listeners, Shape shape) {
        listeners.forEach(f -> SwingUtilities.invokeLater(() -> f.apply(shape)));
        printDebugInfo();
    }

    private void propagate(ArrayList<Function<String, Void>> listeners, String string, StringListener... ignored) {
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
        propagate(setNameListeners, name);
    }

    public void select(long id) {
        if (id == 0) {
            selectedShape = null;
            propagate(selectionListeners, selectedShape);
            return;
        }
        selectedShape = shapes.get(id);
        propagate(selectionListeners, selectedShape);
    }

    private void add(Shape shape) {
        shapes.put(shape.getId(), shape);
    }

    private void modify(long id, Shape newShape) {
        shapes.put(id, newShape);
        if (selectedShape != null && selectedShape.getId() == id) {
            select(id);
        }
    }

    private Shape remove(long id) {
        Shape removedShape = shapes.remove(id);
        if (selectedShape != null && selectedShape.getId() == id) {
            select(id);
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
                if(selectedShape != null && act.getTargetShape().getId() == selectedShape.getId()) {
                    select(0);
                }

            } else if (act.getAction() == UserAction.Type.DELETE) {
                reCreateByUser(act.getTargetShape());
                if(selectedShape != null && act.getTargetShape().getId() == selectedShape.getId()) {
                    select(act.getTargetShape().getId());
                }

            } else if (act.getAction() == UserAction.Type.MODIFY
                || act.getAction() == UserAction.Type.STYLE_MODIFY) {
                modifyByUser(act.getPreviousShape().getId(), act.getPreviousShape());
                if (selectedShape != null && act.getTargetShape().getId() == selectedShape.getId()) {
                    select(act.getPreviousShape().getId());     // 변경전 도형 선택
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
                reCreateByUser(act.getTargetShape());

            } else if (act.getAction() == UserAction.Type.DELETE) {
                removeByUser(act.getTargetShape().getId());
                if(selectedShape != null && act.getTargetShape().getId() == selectedShape.getId()) {
                    select(0);
                }

            } else if (act.getAction() == UserAction.Type.MODIFY
                || act.getAction() == UserAction.Type.STYLE_MODIFY) {
                modifyByUser(act.getPreviousShape().getId(), act.getPreviousShape());
                if (selectedShape != null && act.getTargetShape().getId() == selectedShape.getId()) {
                    select(act.getPreviousShape().getId());     // 변경전 도형 선택
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

    public Map<Long, Shape> getShapes() {
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
}
