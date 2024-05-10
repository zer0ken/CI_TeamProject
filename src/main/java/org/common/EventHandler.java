package org.common;

import kr.ac.konkuk.ccslab.cm.event.CMDataEvent;
import kr.ac.konkuk.ccslab.cm.event.CMDummyEvent;
import kr.ac.konkuk.ccslab.cm.event.CMEvent;
import kr.ac.konkuk.ccslab.cm.event.CMSessionEvent;
import kr.ac.konkuk.ccslab.cm.event.handler.CMAppEventHandler;
import kr.ac.konkuk.ccslab.cm.info.CMInfo;
import kr.ac.konkuk.ccslab.cm.stub.CMStub;
import org.protocol.Command;
import org.protocol.Protocol;

public abstract class EventHandler implements CMAppEventHandler {
    protected CMStub stub;

    public EventHandler(CMStub stub) {
        this.stub = stub;
    }

    @Override
    public void processEvent(CMEvent cme) {
        System.out.println("# incoming event");
        System.out.println("\ttype: " + cme.getType());
        System.out.println("\tid: " + cme.getID());
        System.out.println("\tsession: " + cme.getHandlerSession());
        System.out.println("\tgroup: " + cme.getHandlerGroup());
        switch (cme.getType()) {
            case CMInfo.CM_SESSION_EVENT -> {
                CMSessionEvent se = (CMSessionEvent) cme;

                switch (se.getID()) {
                    case CMSessionEvent.JOIN_SESSION -> processJoinEvent(se);
                    case CMSessionEvent.LOGOUT -> processLogoutEvent(se);
                    case CMSessionEvent.LOGIN_ACK -> processLoginAckEvent(se);
                }
            }
            case CMInfo.CM_DATA_EVENT -> {
                CMDataEvent de = (CMDataEvent) cme;

                switch (de.getID()) {
                    case CMDataEvent.INHABITANT -> processInhabitantEvent(de);
                    case CMDataEvent.NEW_USER -> processNewUserEvent(de);
                    case CMDataEvent.REMOVE_USER -> processRemoveUserEvent(de);
                }
            }
            case CMInfo.CM_DUMMY_EVENT -> {
                CMDummyEvent de = (CMDummyEvent) cme;
                Command cmd = Protocol.parse(de.getDummyInfo());

                if(cmd == null) {
                    System.err.println("@ invalid command: " + de.getDummyInfo());
                    return;
                }

                System.out.println("@ incoming request\n\t" + cmd);

                switch (cmd.getAction()) {
                    case ADD -> processAddShapeEvent(de, cmd);
                    case EDIT -> processEditShapeEvent(de, cmd);
                    case REMOVE -> processRemoveShapeEvent(de, cmd);
                }
            }
        }
    }

    protected void processLoginAckEvent(CMSessionEvent se) {}

    protected void processJoinEvent(CMSessionEvent se) {}

    protected void processLogoutEvent(CMSessionEvent se) {}

    protected void processInhabitantEvent(CMDataEvent de) {}

    protected void processNewUserEvent(CMDataEvent de) {}

    protected void processRemoveUserEvent(CMDataEvent de) {}

    protected void processAddShapeEvent(CMDummyEvent de, Command cmd) {}

    protected void processEditShapeEvent(CMDummyEvent de, Command cmd) {}

    protected void processRemoveShapeEvent(CMDummyEvent de, Command cmd) {}
}
