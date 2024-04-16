package org.common;

import kr.ac.konkuk.ccslab.cm.event.*;
import kr.ac.konkuk.ccslab.cm.event.handler.CMAppEventHandler;
import kr.ac.konkuk.ccslab.cm.info.CMInfo;
import kr.ac.konkuk.ccslab.cm.stub.CMStub;
import org.protocol.Command;

import java.util.Map;
import java.util.function.Function;

public abstract class BaseEventHandler implements CMAppEventHandler {
    Function<String, Command> parse;
    protected CMStub stub;

    public BaseEventHandler(Function<String, Command> parse, CMStub stub) {
        this.parse = parse;
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
                }
            }
            case CMInfo.CM_DATA_EVENT -> {
                CMDataEvent de = (CMDataEvent) cme;

                switch (de.getID()) {
                    case CMDataEvent.INHABITANT -> processInhabitantEvent(de);
                    case CMDataEvent.NEW_USER -> processNewUserEvent(de);
                }
            }
            case CMInfo.CM_DUMMY_EVENT -> {
                CMDummyEvent de = (CMDummyEvent) cme;
                Command cmd = parse.apply(de.getDummyInfo());

                System.out.println("@ incoming request\n\t" + cmd);

                switch (cmd.getAction()) {
                    case ADD -> processAddEvent(de, cmd);
                    case EDIT -> processEditEvent(de, cmd);
                    case REMOVE -> processRemoveEvent(de, cmd);
                }

                System.out.println("@ current shapes");
                for (Map.Entry<Long, String> pair: ((HasShapeMap) stub).getShapes().entrySet()) {
                    System.out.println("\t" + pair.getKey() + ": " + pair.getValue());
                }
            }
        }
    }

    protected void processJoinEvent(CMSessionEvent se) {}


    protected void processLogoutEvent(CMSessionEvent se) {}

    protected void processInhabitantEvent(CMDataEvent de) {}

    protected void processNewUserEvent(CMDataEvent de) {}

    protected void processAddEvent(CMDummyEvent de, Command cmd) {}

    protected void processEditEvent(CMDummyEvent de, Command cmd) {}

    protected void processRemoveEvent(CMDummyEvent de, Command cmd) {}
}
