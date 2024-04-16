package org.common;

import kr.ac.konkuk.ccslab.cm.event.CMDummyEvent;
import kr.ac.konkuk.ccslab.cm.event.CMEvent;
import kr.ac.konkuk.ccslab.cm.event.CMSessionEvent;
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
                Command cmd = parse.apply(de.getDummyInfo());

                System.out.println("# incoming request\n\t" + cmd);

                switch (cmd.getAction()) {
                    case ADD -> processAddEvent(de, cmd);
                    case EDIT -> processEditEvent(de, cmd);
                    case REMOVE -> processRemoveEvent(de, cmd);
                }

                System.out.println("# current shapes");
                for (Map.Entry<Long, String> pair: ((HasShapeMap) stub).getShapes().entrySet()) {
                    System.out.println("\t" + pair.getKey() + ": " + pair.getValue());
                }
            }
        }
    }

    protected abstract void processJoinEvent(CMSessionEvent se);

    protected abstract void processLeaveEvent(CMSessionEvent se);

    protected abstract void processAddEvent(CMDummyEvent de, Command cmd);

    protected abstract void processEditEvent(CMDummyEvent de, Command cmd);

    protected abstract void processRemoveEvent(CMDummyEvent de, Command cmd);
}
