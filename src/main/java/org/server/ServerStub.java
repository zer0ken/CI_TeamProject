package org.server;

import kr.ac.konkuk.ccslab.cm.event.CMDummyEvent;
import kr.ac.konkuk.ccslab.cm.stub.CMServerStub;
import org.common.HasShapeMap;
import org.common.ShapeMap;

import java.util.TreeMap;

public class ServerStub extends CMServerStub implements HasShapeMap {
    public static final String DEFAULT_SESSION = "session1";
    public static final String DEFAULT_GROUP = "g1";
    ShapeMap shapes;

    public void castDummy(String message) {
        CMDummyEvent fromServer = new CMDummyEvent();
        fromServer.setHandlerSession(DEFAULT_SESSION);
        fromServer.setHandlerGroup(DEFAULT_GROUP);
        fromServer.setDummyInfo(message);

        cast(fromServer, DEFAULT_SESSION, DEFAULT_GROUP);
        System.out.println("@ server casted\n\t" + message);
    }

    @Override
    public void setShapes(TreeMap<Long, String> newShapes) {
        shapes.setShapes(newShapes);
    }

    @Override
    public TreeMap<Long, String> getShapes() {
        return shapes.getShapes();
    }

    @Override
    public void putShape(long id, String shape) {
        shapes.putShape(id, shape);
    }

    @Override
    public void removeShape(long id) {
        shapes.removeShape(id);
    }
}
