package org.server;

import kr.ac.konkuk.ccslab.cm.stub.CMServerStub;

public class Server {
    private final ServerStub serverStub;
    private final ServerEventHandler eventHandler;

    public Server() {
        serverStub = new ServerStub();
        eventHandler = new ServerEventHandler(serverStub);
    }

    public CMServerStub getServerStub() {
        return serverStub;
    }

    public ServerEventHandler getServerEventHandler() {
        return eventHandler;
    }

    public static void main(String[] args) {
        Server server = new Server();
        CMServerStub cmStub = server.getServerStub();
        cmStub.setAppEventHandler(server.getServerEventHandler());
        cmStub.startCM();
    }
}