package org.server;

import kr.ac.konkuk.ccslab.cm.event.CMDummyEvent;
import kr.ac.konkuk.ccslab.cm.stub.CMServerStub;

public class ServerStub extends CMServerStub {
    public static final String DEFAULT_SESSION = "session1";
    public static final String DEFAULT_GROUP = "g1";
    private long lastId = 0;

    ServerStub() {
        super();
    }

    public void castDummy(String message) {
        CMDummyEvent fromServer = new CMDummyEvent();
        fromServer.setHandlerSession(DEFAULT_SESSION);
        fromServer.setHandlerGroup(DEFAULT_GROUP);
        fromServer.setDummyInfo(message);

        cast(fromServer, DEFAULT_SESSION, DEFAULT_GROUP);
        System.out.println("@ server casted\n\t" + message);
    }

    public long getLastId() {
        return lastId;
    }

    public void setLastId(long lastId) {
        this.lastId = lastId;
    }
}
