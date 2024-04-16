package org.common;

import java.util.TreeMap;

public interface HasShapeMap {
    void setShapes(TreeMap<Long, String> newShapes);
    TreeMap<Long, String> getShapes();
    void putShape(long id, String shape);
    void removeShape(long id);
}
