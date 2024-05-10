package org.client;

import kr.ac.konkuk.ccslab.cm.event.CMDataEvent;
import kr.ac.konkuk.ccslab.cm.event.CMDummyEvent;
import kr.ac.konkuk.ccslab.cm.event.CMSessionEvent;
import org.client.gui.models.AppModel;
import org.client.gui.shapes.Shape;
import org.common.Base64;
import org.common.EventHandler;
import org.protocol.Command;

public class ClientEventHandler extends EventHandler {
    private final AppModel appModel = AppModel.getInstance();

    public ClientEventHandler(ClientStub clientStub) {
        super(clientStub);
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
        System.out.println("@ 접속 중인 클라이언트");
        System.out.println("\tuser: " + de.getUserName());
        appModel.join(de.getUserName());
    }

    @Override
    protected void processNewUserEvent(CMDataEvent de) {
        if (stub.getMyself().getName().equals(de.getUserName())) {
            appModel.setMyself(de.getUserName());
            return;
        }
        System.out.println("@ 새로 참여한 클라이언트");
        System.out.println("\tuser: " + de.getUserName());
        appModel.join(de.getUserName());
    }

    @Override
    protected void processRemoveUserEvent(CMDataEvent de) {
        System.out.println("@ 잘가라...");
        appModel.leave(de.getUserName());
    }

    @Override
    protected void processAddShapeEvent(CMDummyEvent de, Command cmd) {
        Shape decoded = (Shape) Base64.decode(cmd.getShape());
        if (decoded == null) {
            System.out.println("@ Decode Failed!!!");
            return;
        }
        appModel.createByServer(decoded);
    }

    @Override
    protected void processEditShapeEvent(CMDummyEvent de, Command cmd) {
        Shape decoded = (Shape) Base64.decode(cmd.getShape());
        if (decoded == null) {
            System.out.println("@ Decode Failed!!!");
            return;
        }
        appModel.modifyByServer(decoded);
    }

    @Override
    protected void processRemoveShapeEvent(CMDummyEvent de, Command cmd) {
        appModel.removeByServer(cmd.getId());
    }
}
