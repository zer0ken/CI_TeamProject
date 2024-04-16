package org.client;

import kr.ac.konkuk.ccslab.cm.event.CMDummyEvent;
import kr.ac.konkuk.ccslab.cm.event.CMEvent;
import kr.ac.konkuk.ccslab.cm.event.CMSessionEvent;
import kr.ac.konkuk.ccslab.cm.info.CMInfo;
import org.common.BaseEventHandler;
import org.protocol.Command;
import org.protocol.ServersideProtocol;

import java.util.Map;

public class ClientEventHandler extends BaseEventHandler {
    public ClientEventHandler(ClientStub clientStub) {
        super(ServersideProtocol::parse, clientStub);
    }

    @Override
    protected void processJoinEvent(CMSessionEvent se) {

    }

    @Override
    protected void processLeaveEvent(CMSessionEvent se) {

    }

    @Override
    protected void processAddEvent(CMDummyEvent de, Command cmd) {

    }

    @Override
    protected void processEditEvent(CMDummyEvent de, Command cmd) {

    }

    @Override
    protected void processRemoveEvent(CMDummyEvent de, Command cmd) {

    }
}
