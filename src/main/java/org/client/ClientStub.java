package org.client;

import kr.ac.konkuk.ccslab.cm.event.CMDummyEvent;
import kr.ac.konkuk.ccslab.cm.stub.CMClientStub;
import org.common.Base64;
import org.gui.ShapesViewModel;
import org.gui.components.Login;
import org.protocol.Actions;
import org.protocol.ClientsideProtocol;

import java.util.ArrayList;

public class ClientStub extends CMClientStub {
    private final ArrayList<String> clients = new ArrayList<>();
    private ShapesViewModel shapesViewModel;

    public ClientStub(ShapesViewModel shapesViewModel) {
        super();
        this.shapesViewModel = shapesViewModel;

        shapesViewModel.addListener(ShapesViewModel.Listener.USER_CREATION,
            shape -> requestAdd(Base64.encode(shape)));
        shapesViewModel.addListener(ShapesViewModel.Listener.USER_MODIFICATION,
            shape -> requestEdit(shape.getId(), Base64.encode(shape)));
        shapesViewModel.addListener(ShapesViewModel.Listener.USER_REMOVAL,
            shape -> requestRemove(shape.getId()));

    }

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

    public Void requestAdd(String shape) {
        String message = ClientsideProtocol.build(Actions.ADD, shape);
        sendDummy(message);
        return null;
    }

    public Void requestEdit(long id, String shape) {
        String message = ClientsideProtocol.build(Actions.EDIT, id, shape);
        sendDummy(message);
        return null;
    }

    public Void requestRemove(long id) {
        String message = ClientsideProtocol.build(Actions.REMOVE, id);
        sendDummy(message);
        return null;
    }

    public Void requestLeave() {
        terminateCM();
        return null;
    }
}
