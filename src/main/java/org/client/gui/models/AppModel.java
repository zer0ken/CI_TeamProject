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

    private final Stack<UndoableAction> actionHistory;
    private final Stack<UndoableAction> undoneActions;

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
        UNDO_AVAILABLE(null),
        UNDO_UNAVAILABLE(null),
        REDO_AVAILABLE(null),
        REDO_UNAVAILABLE(null),
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
                SERVER_REMOVAL(REMOVAL),
        CLEAR(null);


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

    private final ArrayList<Function<String, Void>> joinListeners;
    private final ArrayList<Function<String, Void>> leaveListeners;
    private final ArrayList<Function<String, Void>> setNameListeners;

    private final ArrayList<Function<Boolean, Void>> undoAvailableListeners;
    private final ArrayList<Function<Boolean, Void>> undoUnAvailableListeners;
    private final ArrayList<Function<Boolean, Void>> redoAvailableListeners;
    private final ArrayList<Function<Boolean, Void>> redoUnAvailableListeners;

    private final ArrayList<Function<Shape, Void>> selectionListeners;

    // 사용자가 도형을 생성, 수정, 제거하면 클라이언트 앱 내의 모든 컴포넌트 및 **서버**에 전파되어야 함
    private final ArrayList<Function<Shape, Void>> userCreationListeners;
    private final ArrayList<Function<Shape, Void>> userModificationListeners;
    private final ArrayList<Function<Shape, Void>> userRemovalListeners;

    // 서버에서 도형을 생성, 수정 제거하도록 요청하면 클라이언트 앱 내의 모든 컴포넌트에 전파되어야 함.
    private final ArrayList<Function<Shape, Void>> serverCreationListeners;
    private final ArrayList<Function<Shape, Void>> serverModificationListeners;
    private final ArrayList<Function<Shape, Void>> serverRemovalListeners;

    private final ArrayList<Function<Void, Void>> clearListeners;

    private AppModel() {
        joinListeners = new ArrayList<>();
        leaveListeners = new ArrayList<>();
        setNameListeners = new ArrayList<>();

        undoAvailableListeners = new ArrayList<>();
        undoUnAvailableListeners = new ArrayList<>();
        redoAvailableListeners = new ArrayList<>();
        redoUnAvailableListeners = new ArrayList<>();

        selectionListeners = new ArrayList<>();
        userCreationListeners = new ArrayList<>();
        userModificationListeners = new ArrayList<>();
        userRemovalListeners = new ArrayList<>();
        serverCreationListeners = new ArrayList<>();
        serverModificationListeners = new ArrayList<>();
        serverRemovalListeners = new ArrayList<>();

        clearListeners = new ArrayList<>();

        shapes = Collections.synchronizedMap(new TreeMap<>());

        actionHistory = new SizedStack<>(50);
        undoneActions = new SizedStack<>(50);

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

    public void addStackListener(Listener type, Function<Boolean, Void> callback) {
        if (type.includes(UNDO_AVAILABLE))
            undoAvailableListeners.add(callback);
        if (type.includes(UNDO_UNAVAILABLE))
            undoUnAvailableListeners.add(callback);
        if (type.includes(REDO_AVAILABLE))
            redoAvailableListeners.add(callback);
        if (type.includes(REDO_UNAVAILABLE))
            redoUnAvailableListeners.add(callback);
    }

    public void addVoidListener(Listener type, Function<Void, Void> callback) {
      if (type.includes(CLEAR))
        clearListeners.add(callback);
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

    public void setUndoButtonOn(Boolean bool) {
        notify(undoAvailableListeners, bool);
    }

    public void setUndoButtonOff(Boolean bool) {
        notify(undoUnAvailableListeners, bool);
    }

    public void setRedoButtonOn(Boolean bool) {
        notify(redoAvailableListeners, bool);
    }

    public void setRedoButtonOff(Boolean bool) {
        notify(redoUnAvailableListeners, bool);
    }

    public void createByUser(Shape shape) {
        perform(new UndoableAction(
                () -> {
                    if (!getShapes().containsKey(shape.getId())) {
                        add(shape);
                        notify(userCreationListeners, shape);
                    }
                },
                () -> {
                    if (getShapes().containsKey(shape.getId())) {
                        remove(shape.getId());
                        notify(userRemovalListeners, shape);
                        return true;
                    }
                    return false;
                }
        ));
    }

    public void modifyByUser(Shape after) {
        Shape before = getShapes().get(after.getId());

        perform(new UndoableAction(
                () -> {
                    if (getShapes().containsKey(before.getId())) {
                        modify(after);
                        notify(userModificationListeners, after);
                    }
                },
                () -> {
                    if (getShapes().containsKey(after.getId())) {
                        modify(before);
                        notify(userModificationListeners, before);
                        return true;
                    }
                    return false;
                }
        ));
    }

    public void moveByUser(Shape after) {
        Shape before = getShapes().get(after.getId());

        perform(new UndoableAction(UndoableAction.Bulk.MOVE,
                () -> {
                    if (getShapes().containsKey(before.getId())) {
                        modify(after);
                        notify(userModificationListeners, after);
                    }
                },
                () -> {
                    if (getShapes().containsKey(after.getId())) {
                        modify(before);
                        notify(userModificationListeners, before);
                        return true;
                    }
                    return false;
                }
        ));
    }

    public void resizeByUser(Shape after) {
        Shape before = getShapes().get(after.getId());

        perform(new UndoableAction(UndoableAction.Bulk.RESIZE,
                () -> {
                    if (getShapes().containsKey(before.getId())) {
                        modify(after);
                        notify(userModificationListeners, after);
                    }
                },
                () -> {
                    if (getShapes().containsKey(after.getId())) {
                        modify(before);
                        notify(userModificationListeners, before);
                        return true;
                    }
                    return false;
                }
        ));
    }

    public void removeByUser(String id) {
        Shape removed = getShapes().get(id);
        perform(new UndoableAction(
                () -> {
                    if (getShapes().containsKey(id)) {
                        remove(id);
                        notify(userRemovalListeners, removed);
                    }
                },
                () -> {
                    if (!getShapes().containsKey(id)) {
                        add(removed);
                        notify(userCreationListeners, removed);
                        return true;
                    }
                    return false;
                }
        ));
    }

    private void perform(UndoableAction action) {
        action.perform();
        if (!actionHistory.isEmpty() &&
                actionHistory.peek().getBulk() != UndoableAction.Bulk.NONE &&
                actionHistory.peek().getBulk() != action.getBulk()
        ) {
            actionHistory.peek().extend(action);
        } else {
            if (actionHistory.isEmpty()) {
                setUndoButtonOn(true);
            }
            actionHistory.push(action);
        }
        undoneActions.clear();
        setRedoButtonOff(false);
    }

    public void undo() {
        UndoableAction action;
        do {
            if (actionHistory.isEmpty()) {
                return;
            }
            action = actionHistory.pop();
        } while (!action.undo());
        if (actionHistory.isEmpty()) {
            setUndoButtonOff(false);
        }
        if (undoneActions.isEmpty()) {
            setRedoButtonOn(true);
        }
        undoneActions.push(action);
        printDebugInfo();
    }

    public void redo() {
        if (undoneActions.isEmpty()) {
            return;
        }
        UndoableAction action = undoneActions.pop();
        if (undoneActions.isEmpty()) {
            setRedoButtonOff(false);
        }
        action.perform();
        if (actionHistory.isEmpty()) {
            setUndoButtonOn(true);
        }
        actionHistory.push(action);
        printDebugInfo();
    }

    public void createByServer(Shape shape) {
        if (shapes.containsKey(shape.getId())) {
            return;
        }
        add(shape);
        notify(serverCreationListeners, shape);
    }

    public void modifyByServer(Shape shape) {
        modify(shape);
        notify(serverModificationListeners, shape);
    }

    public void removeByServer(String id) {
        Shape removed = remove(id);
        notify(serverRemovalListeners, removed);
    }

    private void notify(ArrayList<Function<Shape, Void>> listeners, Shape shape) {
        listeners.forEach(f -> SwingUtilities.invokeLater(() -> f.apply(shape)));
        printDebugInfo();
    }

    private void notify(ArrayList<Function<Void, Void>> listeners) {
        listeners.forEach(f -> SwingUtilities.invokeLater(() -> f.apply(null)));
        printDebugInfo();
    }

    private void notify(ArrayList<Function<String, Void>> listeners, String string) {
        listeners.forEach(f -> SwingUtilities.invokeLater(() -> f.apply(string)));
        printDebugInfo();
    }

    private void notify(ArrayList<Function<Boolean, Void>> listeners, Boolean bool) {
        listeners.forEach(f -> SwingUtilities.invokeLater(() -> f.apply(bool)));
        printDebugInfo();
    }

    private void printDebugInfo() {
        System.out.println("### 현재 뷰모델");
        System.out.println("selected: " + selectedShape);
        shapes.forEach((i, s) -> System.out.println("\t" + i + ": " + s.toString()));
        System.out.println("actionHistory: " + actionHistory);
        System.out.println("undoneActions: " + undoneActions);
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

    public void clear() {
        shapes.clear();
        select(null);
        notify(clearListeners);
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
        if (removedShape.equals(selectedShape)) {
            select(null);
        }
        return removedShape;
    }

//    // CREATE, DELETE, MODIFY, STYLE_MODIFY 동작을 스택에 저장
//    public void storeUndoStack(UserAction.Type action, Shape targetShape, Shape previousShape) {
//        if (previousShape != null) {        // CHANGE
//            undoStack.push(new UserAction(action, targetShape.copy(), previousShape));
//        } else {                            // CREATE, DELETE
//            undoStack.push(new UserAction(action, targetShape.copy(), null));
//        }
//        redoStackEmptying();
//        redoUnAvailable(false);
//    }
//
//    // undo 메소드
//    public void unDo() {
//        if (!undoStack.isEmpty()) {
//            UserAction act = undoStack.pop();
//            if(undoStack.isEmpty()) {
//                undoUnAvailable(false);
//            }
//
//            if(act.getAction() == UserAction.Type.CREATE) {
//                removeByUser(act.getTargetShape().getId());
//            } else if (act.getAction() == UserAction.Type.DELETE) {
//                System.out.println("삭제 undo");
//                createByUser(act.getTargetShape());
//                if(act.getTargetShape().equals(selectedShape)) {
//                    select(act.getTargetShape());
//                }
//            } else if (act.getAction() == UserAction.Type.MODIFY
//                || act.getAction() == UserAction.Type.STYLE_MODIFY) {
//                modifyByUser(act.getPreviousShape());
//                if (act.getTargetShape().equals(selectedShape)) {
//                    select(act.getPreviousShape());     // 변경전 도형 선택
//                }
//                act = new UserAction(UserAction.Type.MODIFY, act.getPreviousShape(), act.getTargetShape());
//            }
//            redoStack.push(act);
//            redoAvailable(true);
//        }
//    }
//
//    // redo 메소드
//    public void reDo() {
//        if (!redoStack.isEmpty()) {
//            UserAction act = redoStack.pop();
//            if(redoStack.isEmpty()) {
//                redoUnAvailable(false);
//            }
//
//            if (act.getAction() == UserAction.Type.CREATE) {
//                createByUser(act.getTargetShape());
//                if(act.getTargetShape().equals(selectedShape)) {
//                    select(act.getTargetShape());
//                }
//            } else if (act.getAction() == UserAction.Type.DELETE) {
//                removeByUser(act.getTargetShape().getId());
//            } else if (act.getAction() == UserAction.Type.MODIFY
//                    || act.getAction() == UserAction.Type.STYLE_MODIFY) {
//                modifyByUser(act.getPreviousShape());
//                if (act.getTargetShape().equals(selectedShape)) {
//                    select(act.getPreviousShape());     // 변경전 도형 선택
//                }
//                act = new UserAction(UserAction.Type.MODIFY, act.getPreviousShape(), act.getTargetShape());
//
//            }
//
//            undoStack.push(act);
//            undoAvailable(true);
//        }
//    }
//
//    public void redoStackEmptying() {
//        while (!redoStack.isEmpty()) {
//            redoStack.pop();
//        }
//    }

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
