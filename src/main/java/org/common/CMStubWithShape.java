package org.common;

import kr.ac.konkuk.ccslab.cm.stub.CMStub;

import java.util.TreeMap;

public class CMStubWithShape extends CMStub {

    private final TreeMap<Long, String> shapes = new TreeMap<>();

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
