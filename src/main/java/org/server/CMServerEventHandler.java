package org.server;

import kr.ac.konkuk.ccslab.cm.event.CMDummyEvent;
import kr.ac.konkuk.ccslab.cm.event.CMEvent;
import kr.ac.konkuk.ccslab.cm.event.CMSessionEvent;
import kr.ac.konkuk.ccslab.cm.event.handler.CMAppEventHandler;
import kr.ac.konkuk.ccslab.cm.info.CMInfo;
import org.protocol.Actions;
import org.protocol.ClientsideProtocol;
import org.protocol.Command;
import org.protocol.ServersideProtocol;

public class CMServerEventHandler implements CMAppEventHandler {
    private final CustomServerStub serverStub;

    public CMServerEventHandler(CustomServerStub serverStub) {
        this.serverStub = serverStub;
    }

    @Override
    public void processEvent(CMEvent cme) {
        System.out.println("event: " + cme.getType() + ", " + cme.getID());
        switch (cme.getType()) {
            case CMInfo.CM_SESSION_EVENT -> {
                CMSessionEvent se = (CMSessionEvent) cme;

                switch (se.getID()) {
                    case CMSessionEvent.JOIN_SESSION -> processJoinEvent(se);
                    case CMSessionEvent.LEAVE_SESSION -> processLeaveEvent(se);
                }
            }
            case CMInfo.CM_DUMMY_EVENT -> {
                CMDummyEvent de = (CMDummyEvent) cme;
                Command cmd = ClientsideProtocol.parse(de.getDummyInfo());

                System.out.println("client requests " + cmd);

                switch (cmd.getAction()) {
                    case ADD -> processAddEvent(de, cmd);
                    case EDIT -> processEditEvent(de, cmd);
                    case REMOVE -> processRemoveEvent(de, cmd);
                }
            }
        }
    }

    private void processJoinEvent(CMSessionEvent se) {
    }

    private void processLeaveEvent(CMSessionEvent se) {
    }

    private void processAddEvent(CMDummyEvent de, Command cmd) {
        long id = System.currentTimeMillis();
        serverStub.putShape(id, cmd.getShape());

        String message = ServersideProtocol.build(Actions.ADD, id, cmd.getShape());
        cast(de, message);
    }

    private void processEditEvent(CMDummyEvent de, Command cmd) {
        serverStub.putShape(cmd.getId(), cmd.getShape());

        String message = ServersideProtocol.build(Actions.EDIT, cmd.getId(), cmd.getShape());
        cast(de, message);
    }

    private void processRemoveEvent(CMDummyEvent de, Command cmd){
        serverStub.removeShape(cmd.getId());

        String message = ServersideProtocol.build(Actions.REMOVE, cmd.getId());
        cast(de, message);
    }

    private void cast(CMDummyEvent triggerEvent, String message) {
        String session = triggerEvent.getHandlerSession();
        String group = triggerEvent.getHandlerGroup();

        CMDummyEvent fromServer = new CMDummyEvent();
        fromServer.setHandlerSession(session);
        fromServer.setHandlerGroup(group);
        fromServer.setDummyInfo(message);

        serverStub.cast(fromServer, session, group);
        System.out.println("server: " + message);
    }
}