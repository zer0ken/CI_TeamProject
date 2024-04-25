package org.server;

import kr.ac.konkuk.ccslab.cm.event.CMDummyEvent;
import kr.ac.konkuk.ccslab.cm.stub.CMServerStub;
import org.common.ShapeMap;

import java.util.Map;

public class ServerStub extends CMServerStub {
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

    public void setShapes(Map<Long, String> newShapes) {
        shapes.setShapes(newShapes);
    }

    public Map<Long, String> getShapes() {
        return shapes.getShapes();
    }

    public void putShape(long id, String shape) {
        shapes.putShape(id, shape);
    }

    public String getShape(long id) { return shapes.getShape(id); }

    public void removeShape(long id) {
        shapes.removeShape(id);
    }
}
