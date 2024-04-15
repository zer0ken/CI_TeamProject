package org.client;

import kr.ac.konkuk.ccslab.cm.entity.CMUser;
import kr.ac.konkuk.ccslab.cm.event.CMDataEvent;
import kr.ac.konkuk.ccslab.cm.event.CMDummyEvent;
import kr.ac.konkuk.ccslab.cm.event.CMEvent;
import kr.ac.konkuk.ccslab.cm.event.CMSessionEvent;
import kr.ac.konkuk.ccslab.cm.event.handler.CMAppEventHandler;
import kr.ac.konkuk.ccslab.cm.info.CMInfo;
import kr.ac.konkuk.ccslab.cm.info.CMInteractionInfo;
import kr.ac.konkuk.ccslab.cm.stub.CMClientStub;

public class CMClientEventHandler implements CMAppEventHandler {

    private final CMClientStub clientStub;

    public CMClientEventHandler(CMClientStub clientStub) {
        this.clientStub = clientStub;
    }

    @Override
    public void processEvent(CMEvent cme) {
        switch (cme.getType()) {
            case CMInfo.CM_SESSION_EVENT -> processSessionEvent(cme);
            case CMInfo.CM_DATA_EVENT -> processDataEvent(cme);
            case CMInfo.CM_DUMMY_EVENT -> processDummyEvent(cme);
        }
    }

    private void processDataEvent(CMEvent cme) {
        CMDataEvent e = (CMDataEvent) cme;
        System.out.println("@@@ "+e.getID());
        if (e.getID() == CMDataEvent.NEW_USER) {
            sendDummyEvent("add$shape$TEST_SHAPE1");
            sendDummyEvent("edit$1234$TEST_SHAPE2");
            sendDummyEvent("remove$1234");
        }
    }

    private void processDummyEvent(CMEvent cme) {
        CMDummyEvent e = (CMDummyEvent) cme;

        System.out.println("[" + e.getSender() + "] " + e.getDummyInfo());
    }

    private void processSessionEvent(CMEvent cme) {
    }

    private void sendDummyEvent(String text) {
        CMInteractionInfo interactionInfo = clientStub.getCMInfo().getInteractionInfo();
        CMUser myself = interactionInfo.getMyself();
        CMDummyEvent due = new CMDummyEvent();


        due.setHandlerSession(myself.getCurrentSession());
        due.setHandlerGroup(myself.getCurrentGroup());
        due.setDummyInfo(text);

        System.out.println("### SENDING DUMMY EVENT " );
        System.out.println("### session: " + myself.getCurrentSession());
        System.out.println("### group: " + myself.getCurrentGroup());
        System.out.println("### text: " + text);

        clientStub.send(due,"SERVER");
    }
}
