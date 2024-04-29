package org.client.gui;

import org.client.gui.shapes.Shape;

import javax.swing.*;
import java.util.*;
import java.util.function.Function;


public class AppViewModel {
    private final Map<Long, Shape> shapes;
    private Shape selectedShape;
    private final DefaultListModel<String> clientsModel;
    private final DefaultListSelectionModel clientsSelectionModel;

    public ListSelectionModel getClientsSelectionModel() {
        return clientsSelectionModel;
    }

    public enum Listener {
        JOIN_OR_LEAVE,
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

    public AppViewModel() {
        selectionListeners = new ArrayList<>();
        userCreationListeners = new ArrayList<>();
        userModificationListeners = new ArrayList<>();
        userRemovalListeners = new ArrayList<>();
        serverCreationListeners = new ArrayList<>();
        serverModificationListeners = new ArrayList<>();
        serverRemovalListeners = new ArrayList<>();

        shapes = Collections.synchronizedMap(new TreeMap<>());
        clientsModel = new DefaultListModel<>();
        clientsSelectionModel = new DefaultListSelectionModel();
        clientsSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
        if (newId != oldId) {
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
        listeners.forEach(f -> SwingUtilities.invokeLater(() -> f.apply(shape)));
        printDebugInfo();
    }

    private void printDebugInfo() {
        System.out.println("### 현재 뷰모델");
        System.out.println("selected: " + selectedShape);
        shapes.forEach((i, s) -> System.out.println("\t" + i + ": " + s.toString()));
        System.out.println("clients: " + clientsModel.size());
        System.out.println("###");
    }

    public void join(String name) {
        SwingUtilities.invokeLater(() -> clientsModel.add(0, name));
    }

    public void leave(String name) {
        SwingUtilities.invokeLater(() -> clientsModel.removeElement(name));
    }

    public void setMyself(String name) {
        SwingUtilities.invokeLater(() -> {
            clientsSelectionModel.addListSelectionListener((e) -> {
                int index = clientsModel.indexOf(name);
                List<Integer> indices = Arrays.stream(clientsSelectionModel.getSelectedIndices()).boxed().toList();
                if (indices.contains(index)) {
                    return;
                }
                clientsSelectionModel.setSelectionInterval(index, index);
            });
            clientsSelectionModel.setSelectionInterval(0, 0);
        });
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
        return Collections.unmodifiableMap(shapes);
    }

    public Shape getSelectedShape() {
        return selectedShape;
    }

    public DefaultListModel<String> getClientsModel() {
        return clientsModel;
    }
}
