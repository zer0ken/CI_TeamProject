package org.client;

import kr.ac.konkuk.ccslab.cm.stub.CMClientStub;
import org.example.components.App;
import org.example.components.Login;
import org.protocol.Actions;
import org.protocol.ClientsideProtocol;

public class Client {
    private final ClientStub clientStub;
    private final ClientEventHandler eventHandler;
    private App app;

    public Client() {
        clientStub = new ClientStub();
        eventHandler = new ClientEventHandler(clientStub);
    }

    public CMClientStub getClientStub() {
        return clientStub;
    }

    public ClientEventHandler getClientEventHandler() {
        return eventHandler;
    }

    public Void requestAdd(String shape) {
        String message = ClientsideProtocol.build(Actions.ADD, shape);
        clientStub.sendDummy(message);
        return null;
    }

    public Void requestEdit(long id, String shape) {
        String message = ClientsideProtocol.build(Actions.EDIT, id, shape);
        clientStub.sendDummy(message);
        return null;
    }

    public Void requestRemove(long id) {
        String message = ClientsideProtocol.build(Actions.REMOVE, id);
        clientStub.sendDummy(message);
        return null;
    }

    public static void main(String[] args) {
        Client client = new Client();
        CMClientStub cmStub = client.getClientStub();
        cmStub.setAppEventHandler(client.getClientEventHandler());

        cmStub.startCM();

        cmStub.syncLoginCM(Login.login(), "");

        client.app = new App(
                client::requestAdd,
                client::requestEdit,
                client::requestRemove
        );
    }
}