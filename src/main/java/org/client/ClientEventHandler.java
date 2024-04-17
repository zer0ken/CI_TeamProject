package org.client;

import kr.ac.konkuk.ccslab.cm.event.CMDataEvent;
import kr.ac.konkuk.ccslab.cm.event.CMDummyEvent;
import kr.ac.konkuk.ccslab.cm.event.CMSessionEvent;
import org.common.BaseEventHandler;
import org.example.components.App;
import org.protocol.Command;
import org.protocol.ServersideProtocol;

public class ClientEventHandler extends BaseEventHandler {
    private App app;

    public ClientEventHandler(ClientStub clientStub, App app) {
        super(ServersideProtocol::parse, clientStub);
        this.app = app;
    }

    @Override
    protected void processLoginAckEvent(CMSessionEvent se) {
        if (se.isValidUser() == -1) {
            System.out.println("@ 로그인 실패!");
            ((ClientStub) stub).requestLogin(true);
        }
    }

    @Override
    protected void processInhabitantEvent(CMDataEvent de) {
        if (stub.getMyself().getName().equals(de.getUserName())) {
            return;
        }

        System.out.println("@ 선임을 정확하게 알아야지요");
        System.out.println("\tuser: " + de.getUserName());

        ((ClientStub) stub).addClient(de.getUserName());
    }

    @Override
    protected void processNewUserEvent(CMDataEvent de) {
        System.out.println("@ 신병받아라");
        System.out.println("\tuser: " + de.getUserName());
        
        ((ClientStub) stub).addClient(de.getUserName());
        app.updateClientsWindow();
    }

    @Override
    protected void processRemoveUserEvent(CMDataEvent de) {
        System.out.println("@ 잘가라...");
        ((ClientStub) stub).removeClient(de.getUserName());
        app.updateClientsWindow();
    }

    @Override
    protected void processAddShapeEvent(CMDummyEvent de, Command cmd) {
        app.addShape(cmd.getId(), cmd.getShape());
    }

    @Override
    protected void processEditShapeEvent(CMDummyEvent de, Command cmd) {
        app.editShape(cmd.getId(), cmd.getShape());
    }

    @Override
    protected void processRemoveShapeEvent(CMDummyEvent de, Command cmd) {
        app.removeShape(cmd.getId());
    }
}
