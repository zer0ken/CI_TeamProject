package org.example;

import org.example.shapes.Shape;
import org.example.shapes.Style;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.Function;


public class ShapesViewModel {
    private Map<Long, Shape> shapes;
    private Shape selectedShape;
    private Style currentStyle;

    public enum Listener {
        SELECTION, CANVAS_SELECTION, SHAPES_WINDOW_SELECTION,
        CREATION, USER_CREATION, SERVER_CREATION,
        MODIFICATION, USER_MODIFICATION, SERVER_MODIFICATION,
        REMOVAL, USER_REMOVAL, SERVER_REMOVAL
    }

    private final ArrayList<Function<Shape, Void>> canvasSelectionListeners;
    private final ArrayList<Function<Shape, Void>> shapesWindowSelectionListeners;

    // 사용자가 도형을 생성, 수정, 제거하면 클라이언트 앱 내의 모든 컴포넌트 및 **서버**에 전파되어야 함
    private final ArrayList<Function<Shape, Void>> userCreationListeners;
    private final ArrayList<Function<Shape, Void>> userModificationListeners;
    private final ArrayList<Function<Shape, Void>> userRemovalListeners;

    // 서버에서 도형을 생성, 수정 제거하도록 요청하면 클라이언트 앱 내의 모든 컴포넌트에 전파되어야 함.
    private final ArrayList<Function<Shape, Void>> serverCreationListeners;
    private final ArrayList<Function<Shape, Void>> serverModificationListeners;
    private final ArrayList<Function<Shape, Void>> serverRemovalListeners;

    public ShapesViewModel() {
        canvasSelectionListeners = new ArrayList<>();
        shapesWindowSelectionListeners = new ArrayList<>();
        userCreationListeners = new ArrayList<>();
        userModificationListeners = new ArrayList<>();
        userRemovalListeners = new ArrayList<>();
        serverCreationListeners = new ArrayList<>();
        serverModificationListeners = new ArrayList<>();
        serverRemovalListeners = new ArrayList<>();
    }

    public void addListener(Listener type, Function<Shape, Void> callback) {
        switch (type) {
            case SELECTION -> {
                canvasSelectionListeners.add(callback);
                shapesWindowSelectionListeners.add(callback);
            }
            case CANVAS_SELECTION -> canvasSelectionListeners.add(callback);
            case SHAPES_WINDOW_SELECTION -> shapesWindowSelectionListeners.add(callback);
            case CREATION -> {
                userCreationListeners.add(callback);
                serverCreationListeners.add(callback);
            }
            case USER_CREATION -> userCreationListeners.add(callback);
            case SERVER_CREATION -> serverCreationListeners.add(callback);
            case MODIFICATION -> {
                userModificationListeners.add(callback);
                serverModificationListeners.add(callback);
            }
            case USER_MODIFICATION -> userModificationListeners.add(callback);
            case SERVER_MODIFICATION -> serverModificationListeners.add(callback);
            case REMOVAL -> {
                userRemovalListeners.add(callback);
                userRemovalListeners.add(callback);
            }
            case USER_REMOVAL -> userRemovalListeners.add(callback);
            case SERVER_REMOVAL -> serverRemovalListeners.add(callback);
        }
    }

    public void selectByCanvas(long id) {
        selectedShape = shapes.get(id);
        propagate(canvasSelectionListeners, selectedShape);
    }

    public void selectByShapesWindow(long id) {
        selectedShape = shapes.get(id);
        propagate(shapesWindowSelectionListeners, selectedShape);
    }

    public void createByUser(Shape shape) {
        add(shape);
        propagate(userCreationListeners, shape);
    }

    public void createByServer(Shape shape) {
        add(shape);
        propagate(serverCreationListeners, shape);
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
        listeners.forEach(f -> f.apply(shape));
    }

    private void add(Shape shape) {
        shapes.put(shape.getId(), shape);
    }

    private void modify(long id, Shape shape) {
        shapes.put(id, shape);
    }

    private Shape remove(long id) {
        return shapes.remove(id);
    }

    public Shape getSelectedShape() {
        return selectedShape;
    }

    public Style getCurrentStyle() {
        return currentStyle;
    }

    public void setCurrentStyle(Style currentStyle) {
        this.currentStyle = currentStyle;
    }
}
