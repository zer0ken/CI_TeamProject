package org.server;

import kr.ac.konkuk.ccslab.cm.event.CMDummyEvent;
import kr.ac.konkuk.ccslab.cm.stub.CMServerStub;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class ServerStub extends CMServerStub {
    public static final String DEFAULT_SESSION = "session1";
    public static final String DEFAULT_GROUP = "g1";
    Map<String, String> shapes;

    ServerStub() {
        super();
        shapes = Collections.synchronizedMap(new TreeMap<>());
    }

    public void castDummy(String message) {
        CMDummyEvent fromServer = new CMDummyEvent();
        fromServer.setHandlerSession(DEFAULT_SESSION);
        fromServer.setHandlerGroup(DEFAULT_GROUP);
        fromServer.setDummyInfo(message);

        cast(fromServer, DEFAULT_SESSION, DEFAULT_GROUP);
        System.out.println("@ server casted\n\t" + message);
    }

    public Map<String, String> getShapes() {
        return shapes;
    }
}
