package org.client;

import org.client.gui.App;

public class Client {
    Client() {
        ClientStub clientStub = new ClientStub();
        App.start(unused -> clientStub.requestLeave());
        ClientEventHandler eventHandler = new ClientEventHandler(clientStub);
        clientStub.setAppEventHandler(eventHandler);
        clientStub.startCM();
        clientStub.requestLogin(false);
    }

    public static void main(String[] args) {
        new Client();
    }
}