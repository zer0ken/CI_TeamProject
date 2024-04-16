package org.server;

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
    }

    @Override

    protected void processLeaveEvent(CMSessionEvent se) {
    }

    @Override

    protected void processAddEvent(CMDummyEvent de, Command cmd) {
        long id = System.currentTimeMillis();
        ((HasShapeMap) stub).putShape(id, cmd.getShape());

        String message = ServersideProtocol.build(Actions.ADD, id, cmd.getShape());
        ((ServerStub) stub).castDummy(message, de.getHandlerSession(), de.getHandlerGroup());
    }

    @Override
    protected void processEditEvent(CMDummyEvent de, Command cmd) {
        ((HasShapeMap) stub).putShape(cmd.getId(), cmd.getShape());

        String message = ServersideProtocol.build(Actions.EDIT, cmd.getId(), cmd.getShape());
        ((ServerStub) stub).castDummy(message, de.getHandlerSession(), de.getHandlerGroup());
    }

    @Override
    protected void processRemoveEvent(CMDummyEvent de, Command cmd){
        ((HasShapeMap) stub).removeShape(cmd.getId());

        String message = ServersideProtocol.build(Actions.REMOVE, cmd.getId());
        ((ServerStub) stub).castDummy(message, de.getHandlerSession(), de.getHandlerGroup());
    }
}