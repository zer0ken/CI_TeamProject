package org.server;

import kr.ac.konkuk.ccslab.cm.event.CMDummyEvent;
import kr.ac.konkuk.ccslab.cm.event.CMSessionEvent;
import org.common.EventHandler;
import org.protocol.Action;
import org.protocol.Command;
import org.protocol.Protocol;

public class ServerEventHandler extends EventHandler {
    public ServerEventHandler(ServerStub serverStub) {
        super(serverStub);
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
    synchronized protected void processAddShapeEvent(CMDummyEvent de, Command cmd) {
        ((ServerStub) stub).getShapes().put(cmd.getId(), cmd.getShape());

        String message = Protocol.build(Action.ADD, cmd.getId(), cmd.getShape());
        ((ServerStub) stub).castDummy(message);
    }

    @Override
    synchronized protected void processEditShapeEvent(CMDummyEvent de, Command cmd) {
        if (((ServerStub) stub).getShapes().get(cmd.getId()) == null) {
            return;
        }

        ((ServerStub) stub).getShapes().put(cmd.getId(), cmd.getShape());

        String message = Protocol.build(Action.EDIT, cmd.getId(), cmd.getShape());
        ((ServerStub) stub).castDummy(message);
    }

    @Override
    synchronized protected void processRemoveShapeEvent(CMDummyEvent de, Command cmd) {
        if (((ServerStub) stub).getShapes().get(cmd.getId()) == null) {
            return;
        }

        ((ServerStub) stub).getShapes().remove(cmd.getId());

        String message = Protocol.build(Action.REMOVE, cmd.getId());
        ((ServerStub) stub).castDummy(message);
    }
}