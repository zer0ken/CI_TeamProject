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

import java.util.Map;

public class ServerEventHandler implements CMAppEventHandler {
    private final ServerStub serverStub;

    public ServerEventHandler(ServerStub serverStub) {
        this.serverStub = serverStub;
    }

    @Override
    public void processEvent(CMEvent cme) {
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

                System.out.println("# client requested\n\t" + cmd);

                switch (cmd.getAction()) {
                    case ADD -> processAddEvent(de, cmd);
                    case EDIT -> processEditEvent(de, cmd);
                    case REMOVE -> processRemoveEvent(de, cmd);
                }

                System.out.println("# current shapes");
                for (Map.Entry<Long, String> pair: serverStub.getShapes().entrySet()) {
                    System.out.println("\t" + pair.getKey() + ": " + pair.getValue());
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
        serverStub.castDummy(message, de.getHandlerSession(), de.getHandlerGroup());
    }

    private void processEditEvent(CMDummyEvent de, Command cmd) {
        serverStub.putShape(cmd.getId(), cmd.getShape());

        String message = ServersideProtocol.build(Actions.EDIT, cmd.getId(), cmd.getShape());
        serverStub.castDummy(message, de.getHandlerSession(), de.getHandlerGroup());
    }

    private void processRemoveEvent(CMDummyEvent de, Command cmd){
        serverStub.removeShape(cmd.getId());

        String message = ServersideProtocol.build(Actions.REMOVE, cmd.getId());
        serverStub.castDummy(message, de.getHandlerSession(), de.getHandlerGroup());
    }
}