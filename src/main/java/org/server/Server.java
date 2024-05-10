package org.server;

public class Server {
    private final ServerStub serverStub;
    private final ServerEventHandler eventHandler;

    public Server() {
        serverStub = new ServerStub();
        eventHandler = new ServerEventHandler(serverStub);
        serverStub.setAppEventHandler(eventHandler);
        serverStub.startCM();
    }

    public static void main(String[] args) {
        new Server();
    }
}