package org.common;

import java.util.TreeMap;

public class ShapeMap {

    private TreeMap<Long, String> shapes = new TreeMap<>();

    public void setShapes(TreeMap<Long, String> newShapes) {
        shapes = newShapes;
    }

    public TreeMap<Long, String> getShapes() {
        return shapes;
    }

    public void putShape(long id, String shape) {
        shapes.put(id, shape);
    }

    public void removeShape(long id) {
        shapes.remove(id);
    }
}
