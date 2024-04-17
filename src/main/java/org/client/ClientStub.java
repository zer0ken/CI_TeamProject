package org.client;

import kr.ac.konkuk.ccslab.cm.event.CMDummyEvent;
import kr.ac.konkuk.ccslab.cm.stub.CMClientStub;
import org.example.components.Login;
import org.protocol.Actions;
import org.protocol.ClientsideProtocol;

import java.util.ArrayList;

public class ClientStub extends CMClientStub {
    private final ArrayList<String> clients = new ArrayList<>();

    public void sendDummy(String message) {
        CMDummyEvent fromClient = new CMDummyEvent();
        fromClient.setHandlerSession(getMyself().getCurrentSession());
        fromClient.setHandlerGroup(getMyself().getCurrentGroup());
        fromClient.setDummyInfo(message);

        send(fromClient, getDefaultServerName());
        System.out.println("@ sent\n\t" + message);
    }

    public ArrayList<String> getClients() {
        return clients;
    }

    public void addClient(String client) {
        clients.add(client);
    }

    public void removeClient(String client) {
        clients.remove(client);
    }

    public void requestLogin(boolean invalid) {
        loginCM(Login.login(invalid), "");
    }

    public void requestAdd(String shape) {
        String message = ClientsideProtocol.build(Actions.ADD, shape);
        sendDummy(message);
    }

    public void requestEdit(long id, String shape) {
        String message = ClientsideProtocol.build(Actions.EDIT, id, shape);
        sendDummy(message);
    }

    public void requestRemove(long id) {
        String message = ClientsideProtocol.build(Actions.REMOVE, id);
        sendDummy(message);
    }

    public void requestLeave() {
        terminateCM();
    }
}
