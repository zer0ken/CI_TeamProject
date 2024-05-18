package org.client;

import kr.ac.konkuk.ccslab.cm.event.CMDummyEvent;
import kr.ac.konkuk.ccslab.cm.stub.CMClientStub;
import org.client.gui.components.Login;
import org.client.gui.models.AppModel;
import org.client.gui.models.AppModel.Listener;
import org.protocol.Base64;
import org.protocol.Action;
import org.protocol.Protocol;

public class ClientStub extends CMClientStub {
    public ClientStub() {
        super();

        AppModel appModel = AppModel.getInstance();

        appModel.addListener(Listener.USER_CREATION, shape -> requestAdd(shape.getId(), Base64.encode(shape)));
        appModel.addListener(Listener.USER_MODIFICATION, shape -> requestEdit(shape.getId(), Base64.encode(shape)));
        appModel.addListener(Listener.USER_REMOVAL, shape -> requestRemove(shape.getId()));

    }

    public void sendDummy(String message) {
        CMDummyEvent fromClient = new CMDummyEvent();
        fromClient.setHandlerSession(getMyself().getCurrentSession());
        fromClient.setHandlerGroup(getMyself().getCurrentGroup());
        fromClient.setDummyInfo(message);

        send(fromClient, getDefaultServerName());
        System.out.println("@ sent\n\t" + message);
    }

    public void requestLogin(boolean invalid) {
        loginCM(Login.login(invalid), "");
    }

    public Void requestAdd(String id, String shape) {
        String message = Protocol.build(Action.ADD, id, shape);
        sendDummy(message);
        return null;
    }

    public Void requestEdit(String id, String shape) {
        String message = Protocol.build(Action.EDIT, id, shape);
        sendDummy(message);
        return null;
    }

    public Void requestRemove(String id) {
        String message = Protocol.build(Action.REMOVE, id);
        sendDummy(message);
        return null;
    }

    public Void requestLeave() {
        terminateCM();
        return null;
    }
}
