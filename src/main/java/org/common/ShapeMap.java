package org.common;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class ShapeMap {

    private Map<Long, String> shapes = Collections.synchronizedMap(new TreeMap<>());

    public void setShapes(Map<Long, String> newShapes) {
        shapes = newShapes;
    }

    public Map<Long, String> getShapes() {
        return shapes;
    }

    public void putShape(long id, String shape) {
        shapes.put(id, shape);
    }

    public void removeShape(long id) {
        shapes.remove(id);
    }
}
