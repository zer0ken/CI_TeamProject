package org.server;

public class Server {
    public Server() {
        ServerStub serverStub = new ServerStub();
        ServerEventHandler eventHandler = new ServerEventHandler(serverStub);
        serverStub.setAppEventHandler(eventHandler);
        serverStub.startCM();
    }

    public static void main(String[] args) {
        new Server();
    }
}