package org.server;

import kr.ac.konkuk.ccslab.cm.stub.CMServerStub;

public class CMServerApp {
    private CMServerStub serverStub;
    private CMServerEventHandler eventHandler;

    public CMServerApp() {
        serverStub = new CMServerStub();
        eventHandler = new CMServerEventHandler(serverStub);
    }

    public CMServerStub getServerStub() {
        return serverStub;
    }

    public CMServerEventHandler getServerEventHandler() {
        return eventHandler;
    }

    public static void main(String[] args) {
        CMServerApp server = new CMServerApp();
        CMServerStub cmStub = server.getServerStub();
        cmStub.setAppEventHandler(server.getServerEventHandler());
        cmStub.startCM();
    }
}