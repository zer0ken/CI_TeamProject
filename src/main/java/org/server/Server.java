package org.server;

public class Server {
    private final ServerStub serverStub;
    private final ServerEventHandler eventHandler;

    public Server() {
        serverStub = new ServerStub();
        eventHandler = new ServerEventHandler(serverStub);
    }

    public ServerStub getServerStub() {
        return serverStub;
    }

    public ServerEventHandler getServerEventHandler() {
        return eventHandler;
    }

    public static void main(String[] args) {
        Server server = new Server();
        ServerStub cmStub = server.getServerStub();
        cmStub.setAppEventHandler(server.getServerEventHandler());
        cmStub.startCM();
    }
}