package org.client;

import kr.ac.konkuk.ccslab.cm.event.CMDummyEvent;
import kr.ac.konkuk.ccslab.cm.stub.CMClientStub;
import org.common.HasShapeMap;
import org.common.ShapeMap;
import org.example.components.Login;
import org.protocol.Actions;
import org.protocol.ClientsideProtocol;

import java.util.ArrayList;
import java.util.TreeMap;

public class ClientStub extends CMClientStub implements HasShapeMap {
    private ShapeMap shapes;

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

    @Override
    public void setShapes(TreeMap<Long, String> newShapes) {
        shapes.setShapes(newShapes);
    }

    @Override
    public TreeMap<Long, String> getShapes() {
        return shapes.getShapes();
    }

    @Override
    public void putShape(long id, String shape) {
        shapes.putShape(id, shape);
    }

    @Override
    public void removeShape(long id) {
        shapes.removeShape(id);
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
