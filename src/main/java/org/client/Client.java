package org.client;

import kr.ac.konkuk.ccslab.cm.stub.CMClientStub;
import org.example.components.App;
import org.example.components.Login;
import org.protocol.Actions;
import org.protocol.ClientsideProtocol;

public class Client {
    private final ClientStub clientStub;
    private final ClientEventHandler eventHandler;
    private final App app;

    public Client() {
        clientStub = new ClientStub();
        app = new App(clientStub);
        eventHandler = new ClientEventHandler(clientStub, app);
        clientStub.setAppEventHandler(eventHandler);
        clientStub.startCM();
        clientStub.requestLogin(false);
    }

    public static void main(String[] args) {
        new Client();
    }
}