package org.server;

import kr.ac.konkuk.ccslab.cm.event.CMDataEvent;
import kr.ac.konkuk.ccslab.cm.event.CMDummyEvent;
import kr.ac.konkuk.ccslab.cm.event.CMEvent;
import kr.ac.konkuk.ccslab.cm.event.CMSessionEvent;
import kr.ac.konkuk.ccslab.cm.event.handler.CMAppEventHandler;
import kr.ac.konkuk.ccslab.cm.info.CMInfo;
import org.common.BaseEventHandler;
import org.common.HasShapeMap;
import org.protocol.Actions;
import org.protocol.ClientsideProtocol;
import org.protocol.Command;
import org.protocol.ServersideProtocol;

import java.util.Map;

public class ServerEventHandler extends BaseEventHandler {
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
        ((HasShapeMap) stub).putShape(id, cmd.getShape());

        String message = ServersideProtocol.build(Actions.ADD, id, cmd.getShape());
        ((ServerStub) stub).castDummy(message);
    }

    @Override
    protected void processEditShapeEvent(CMDummyEvent de, Command cmd) {
        ((HasShapeMap) stub).putShape(cmd.getId(), cmd.getShape());

        String message = ServersideProtocol.build(Actions.EDIT, cmd.getId(), cmd.getShape());
        ((ServerStub) stub).castDummy(message);
    }

    @Override
    protected void processRemoveShapeEvent(CMDummyEvent de, Command cmd){
        ((HasShapeMap) stub).removeShape(cmd.getId());

        String message = ServersideProtocol.build(Actions.REMOVE, cmd.getId());
        ((ServerStub) stub).castDummy(message);
    }
}