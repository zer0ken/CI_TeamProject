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

    private final Stack<Undoable> actionHistory;
    private final Stack<Undoable> undoneActions;

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

    public void createByUser(Shape shape) {
        perform(new Undoable(
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

        perform(new Undoable(
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

        perform(new Undoable(Undoable.Bulk.MOVE,
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

        perform(new Undoable(Undoable.Bulk.RESIZE,
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
        perform(new Undoable(
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

    private void perform(Undoable action) {
        action.perform();
        if (!actionHistory.isEmpty() &&
                actionHistory.peek().getBulk() != Undoable.Bulk.NONE &&
                actionHistory.peek().getBulk() != action.getBulk()
        ) {
            actionHistory.peek().extend(action);
        } else {
            actionHistory.push(action);
        }
        undoneActions.clear();
    }

    public void undo() {
        Undoable action;
        do {
            if (actionHistory.isEmpty()) {
                return;
            }
            action = actionHistory.pop();
        } while (!action.undo());
        undoneActions.push(action);
        printDebugInfo();
    }

    public void redo() {
        if (undoneActions.isEmpty()) {
            return;
        }
        Undoable action = undoneActions.pop();
        action.perform();
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
        if (shape.equals(selectedShape)) {
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
