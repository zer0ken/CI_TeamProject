package org.client;

import kr.ac.konkuk.ccslab.cm.event.CMDummyEvent;
import kr.ac.konkuk.ccslab.cm.stub.CMClientStub;
import org.client.gui.AppViewModel;
import org.client.gui.components.Login;
import org.common.Base64;
import org.protocol.Action;
import org.protocol.ClientsideProtocol;

public class ClientStub extends CMClientStub {
    public ClientStub(AppViewModel appViewModel) {
        super();

        appViewModel.addListener(AppViewModel.Listener.USER_CREATION,
                shape -> requestAdd(Base64.encode(shape)));
        appViewModel.addListener(AppViewModel.Listener.USER_MODIFICATION,
                shape -> requestEdit(shape.getId(), Base64.encode(shape)));
        appViewModel.addListener(AppViewModel.Listener.USER_REMOVAL,
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

    public void requestLogin(boolean invalid) {
        loginCM(Login.login(invalid), "");
    }

    public Void requestAdd(String shape) {
        String message = ClientsideProtocol.build(Action.ADD, shape);
        sendDummy(message);
        return null;
    }

    public Void requestEdit(long id, String shape) {
        String message = ClientsideProtocol.build(Action.EDIT, id, shape);
        sendDummy(message);
        return null;
    }

    public Void requestRemove(long id) {
        String message = ClientsideProtocol.build(Action.REMOVE, id);
        sendDummy(message);
        return null;
    }

    public Void requestLeave() {
        terminateCM();
        return null;
    }
}
