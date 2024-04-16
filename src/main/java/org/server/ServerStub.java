package org.server;

import kr.ac.konkuk.ccslab.cm.event.CMDummyEvent;
import kr.ac.konkuk.ccslab.cm.stub.CMServerStub;
import org.common.CMStubWithShape;

import java.util.TreeMap;

public class ServerStub extends CMStubWithShape {
    public void castDummy(String message, String session, String group) {
        CMDummyEvent fromServer = new CMDummyEvent();
        fromServer.setHandlerSession(session);
        fromServer.setHandlerGroup(group);
        fromServer.setDummyInfo(message);

        cast(fromServer, session, group);
        System.out.println("# server casted\n\t" + message);
    }
}
