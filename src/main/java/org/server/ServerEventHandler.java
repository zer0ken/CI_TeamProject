package org.server;

import kr.ac.konkuk.ccslab.cm.event.CMDummyEvent;
import kr.ac.konkuk.ccslab.cm.event.CMSessionEvent;
import org.common.EventHandler;
import org.protocol.Action;
import org.protocol.ClientsideProtocol;
import org.protocol.Command;
import org.protocol.ServersideProtocol;

public class ServerEventHandler extends EventHandler {
    public ServerEventHandler(ServerStub serverStub) {
        super(ClientsideProtocol::parse, serverStub);
    }

    @Override
    protected void processJoinEvent(CMSessionEvent se) {
        System.out.println("@ user joining");
        System.out.println("\tsession: " + se.getSessionName());
        System.out.println("\tgroup: " + se.getCurrentGroupName());
        System.out.println("\tuser: " + se.getUserName());
    }

    @Override
    protected void processLogoutEvent(CMSessionEvent se) {
        System.out.println("@ user logout");
        System.out.println("\tsession: " + se.getSessionName());
        System.out.println("\tgroup: " + se.getCurrentGroupName());
        System.out.println("\tuser: " + se.getUserName());
    }

    @Override
    protected void processAddShapeEvent(CMDummyEvent de, Command cmd) {
        long id = System.currentTimeMillis();
        while (id == ((ServerStub) stub).getLastId()) {
            id++;   // id 중복을 회피
        }
        ((ServerStub) stub).setLastId(id);

        String message = ServersideProtocol.build(Action.ADD, id, cmd.getShape());
        ((ServerStub) stub).castDummy(message);
    }

    @Override
    protected void processEditShapeEvent(CMDummyEvent de, Command cmd) {
        String message = ServersideProtocol.build(Action.EDIT, cmd.getId(), cmd.getShape());
        ((ServerStub) stub).castDummy(message);
    }

    @Override
    protected void processRemoveShapeEvent(CMDummyEvent de, Command cmd) {
        String message = ServersideProtocol.build(Action.REMOVE, cmd.getId());
        ((ServerStub) stub).castDummy(message);
    }
}