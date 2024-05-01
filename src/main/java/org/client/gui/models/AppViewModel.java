package org.client.gui.models;

import org.client.gui.shapes.Shape;

import javax.swing.*;
import java.util.*;
import java.util.function.Function;

import static org.client.gui.models.AppViewModel.Listener.*;


public class AppViewModel {
    private static AppViewModel instance = null;

    private final Map<Long, Shape> shapes;
    private Shape selectedShape;
    private final DefaultListModel<String> clientListModel;
    private final DefaultListSelectionModel clientListSelectionModel;
    private final  DefaultListModel<Shape> shapeListModel;
    private final DefaultListSelectionModel shapeListSelectionModel;

    public ListSelectionModel getClientListSelectionModel() {
        return clientListSelectionModel;
    }

    public enum Listener {
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
            for (;  o != null;  o = o.parent) {
                if (o == this) {
                    return true;
                }
            }
            return false;
        }
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

    private AppViewModel() {
        selectionListeners = new ArrayList<>();
        userCreationListeners = new ArrayList<>();
        userModificationListeners = new ArrayList<>();
        userRemovalListeners = new ArrayList<>();
        serverCreationListeners = new ArrayList<>();
        serverModificationListeners = new ArrayList<>();
        serverRemovalListeners = new ArrayList<>();

        shapes = Collections.synchronizedMap(new TreeMap<>());

        clientListModel = new DefaultListModel<>();
        clientListSelectionModel = new DefaultListSelectionModel();
        clientListSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        shapeListModel = new DefaultListModel<>();
        shapeListSelectionModel = new DefaultListSelectionModel();
        shapeListSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        shapeListSelectionModel.addListSelectionListener((e) -> {
            if (e.getValueIsAdjusting()){
                return;
            }
            int[] selectedIndices = shapeListSelectionModel.getSelectedIndices();
            if (selectedIndices.length == 0) {
                return;
            }
            Shape newSelectedShape = shapeListModel.get(selectedIndices[0]);
            if (selectedShape == null
                    || selectedShape.getId() != newSelectedShape.getId()) {
                select(newSelectedShape.getId());
            }
        });
        instance = this;
    }

    public static AppViewModel getInstance() {
        if (instance == null) {
            return new AppViewModel();
        }
        return instance;
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
        System.out.println("clients: " + clientListModel.size());
        System.out.println("###");
    }

    public void join(String name) {
        SwingUtilities.invokeLater(() -> clientListModel.add(0, name));
    }

    public void leave(String name) {
        SwingUtilities.invokeLater(() -> clientListModel.removeElement(name));
    }

    public void setMyself(String name) {
        SwingUtilities.invokeLater(() -> {
            clientListSelectionModel.addListSelectionListener((e) -> {
                int index = clientListModel.indexOf(name);
                List<Integer> indices = Arrays.stream(clientListSelectionModel.getSelectedIndices()).boxed().toList();
                if (indices.contains(index)) {
                    return;
                }
                clientListSelectionModel.setSelectionInterval(index, index);
            });
            clientListSelectionModel.setSelectionInterval(0, 0);
        });
    }

    public void select(long id) {
        selectedShape = shapes.get(id);

        SwingUtilities.invokeLater(() -> {
            if (selectedShape == null) {
                shapeListSelectionModel.clearSelection();
            }else {
                int index = shapeListModel.indexOf(selectedShape);
                shapeListSelectionModel.setSelectionInterval(index, index);
            }
        });

        propagate(selectionListeners, selectedShape);
    }

    private void add(Shape shape) {
        shapes.put(shape.getId(), shape);

        SwingUtilities.invokeLater(() -> {
            shapeListModel.add(0, shape);
        });
    }

    private void modify(long id, Shape newShape) {
        Shape oldShape = shapes.get(id);
        shapes.put(id, newShape);
        if (selectedShape != null && selectedShape.getId() == id) {
            select(id);
        }

        SwingUtilities.invokeLater(() -> {
            shapeListModel.set(shapeListModel.indexOf(oldShape), newShape);
        });
    }

    private Shape remove(long id) {
        Shape removedShape = shapes.remove(id);
        if (selectedShape != null && selectedShape.getId() == id) {
            select(id);
        }

        SwingUtilities.invokeLater(() -> {
            shapeListModel.removeElement(removedShape);
        });

        return removedShape;
    }

    public Map<Long, Shape> getShapes() {
        return Collections.unmodifiableMap(shapes);
    }

    public Shape getSelectedShape() {
        return selectedShape;
    }

    public DefaultListModel<String> getClientListModel() {
        return clientListModel;
    }

    public DefaultListModel<Shape> getShapeListModel() {
        return shapeListModel;
    }

    public DefaultListSelectionModel getShapeListSelectionModel() {
        return shapeListSelectionModel;
    }
}
