package org.client.gui;

import org.client.gui.shapes.Shape;

import java.util.*;
import java.util.function.Function;


public class ShapesViewModel {
    private final Map<Long, Shape> shapes;
    private Shape selectedShape;

    public enum Listener {
        SELECTION,
        CREATION, USER_CREATION, SERVER_CREATION,
        MODIFICATION, USER_MODIFICATION, SERVER_MODIFICATION,
        REMOVAL, USER_REMOVAL, SERVER_REMOVAL
    }

    private final ArrayList<Function<Shape, Void>> selectionListeners;

    // 사용자가 도형을 생성, 수정, 제거하면 클라이언트 앱 내의 모든 컴포넌트 및 **서버**에 전파되어야 함
    private final ArrayList<Function<Shape, Void>> userCreationListeners;
    private final ArrayList<Function<Shape, Void>> userModificationListeners;
    private final ArrayList<Function<Shape, Void>> userRemovalListeners;

    // 서버에서 도형을 생성, 수정 제거하도록 요청하면 클라이언트 앱 내의 모든 컴포넌트에 전파되어야 함.
    private final ArrayList<Function<Shape, Void>> serverCreationListeners;
    private final ArrayList<Function<Shape, Void>> serverModificationListeners;
    private final ArrayList<Function<Shape, Void>> serverRemovalListeners;

    public ShapesViewModel() {
        selectionListeners = new ArrayList<>();
        userCreationListeners = new ArrayList<>();
        userModificationListeners = new ArrayList<>();
        userRemovalListeners = new ArrayList<>();
        serverCreationListeners = new ArrayList<>();
        serverModificationListeners = new ArrayList<>();
        serverRemovalListeners = new ArrayList<>();

        shapes = Collections.synchronizedMap(new TreeMap<>());
    }

    public void addListener(Listener type, Function<Shape, Void> callback) {
        switch (type) {
            case SELECTION -> selectionListeners.add(callback);
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
                serverRemovalListeners.add(callback);
            }
            case USER_REMOVAL -> userRemovalListeners.add(callback);
            case SERVER_REMOVAL -> serverRemovalListeners.add(callback);
        }
    }

    public void select(long id) {
        selectedShape = shapes.get(id);
        propagate(selectionListeners, selectedShape);
    }

    public void createByUser(Shape shape) {
        add(shape);
        propagate(userCreationListeners, shape);
    }

    public void createByServer(long newId, Shape shape) {
        long oldId = shape.getId();
        Shape newShape = shape;
        Shape oldSelectedShape = selectedShape;
        if(newId != oldId) {
            newShape = shape.copy(newId);
            removeByServer(oldId);
        }
        add(newShape);
        propagate(serverCreationListeners, shape);
        if (oldSelectedShape != null && oldSelectedShape.getId() == oldId) {
            select(newId);
        }
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
        System.out.println("### 현재 뷰모델");
        System.out.println("selected: " + selectedShape);
        shapes.forEach((i, s) -> System.out.println("\t"+ i + ": " + s.toString()));
        System.out.println("###");
    }

    private void add(Shape shape) {
        shapes.put(shape.getId(), shape);
    }

    private void modify(long id, Shape shape) {
        shapes.put(id, shape);
        if (selectedShape != null && selectedShape.getId() == id) {
            select(id);
        }
    }

    private Shape remove(long id) {
        Shape removed = shapes.remove(id);
        if (selectedShape != null && selectedShape.getId() == id) {
            select(id);
        }
        return removed;
    }

    public Map<Long, Shape> getShapes() {
        return shapes;
    }

    public Shape getSelectedShape() {
        return selectedShape;
    }
}
