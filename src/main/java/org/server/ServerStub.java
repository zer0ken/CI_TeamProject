package org.server;

import kr.ac.konkuk.ccslab.cm.event.CMDummyEvent;
import kr.ac.konkuk.ccslab.cm.stub.CMServerStub;

import java.util.TreeMap;

public class ServerStub extends CMServerStub {
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

    public void castDummy(String message, String session, String group) {
        CMDummyEvent fromServer = new CMDummyEvent();
        fromServer.setHandlerSession(session);
        fromServer.setHandlerGroup(group);
        fromServer.setDummyInfo(message);

        cast(fromServer, session, group);
        System.out.println("# server casted\n\t" + message);
    }
}
