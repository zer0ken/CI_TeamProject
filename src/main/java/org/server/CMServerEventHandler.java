package org.server;

import kr.ac.konkuk.ccslab.cm.entity.CMUser;
import kr.ac.konkuk.ccslab.cm.event.CMDummyEvent;
import kr.ac.konkuk.ccslab.cm.event.CMEvent;
import kr.ac.konkuk.ccslab.cm.event.CMSessionEvent;
import kr.ac.konkuk.ccslab.cm.event.handler.CMAppEventHandler;
import kr.ac.konkuk.ccslab.cm.info.CMInfo;
import kr.ac.konkuk.ccslab.cm.info.CMInteractionInfo;
import kr.ac.konkuk.ccslab.cm.stub.CMServerStub;

public class CMServerEventHandler implements CMAppEventHandler {
    private CMServerStub serverStub;

    public CMServerEventHandler(CMServerStub serverStub) {
        this.serverStub = serverStub;
    }

    @Override
    public void processEvent(CMEvent cme) {
        switch (cme.getType()) {
            case CMInfo.CM_SESSION_EVENT -> processSessionEvent(cme);
            case CMInfo.CM_DUMMY_EVENT -> processDummyEvent(cme);
        }
    }

    private void processSessionEvent(CMEvent cme) {
        CMSessionEvent se = (CMSessionEvent) cme;

        switch (se.getID()) {
            case CMSessionEvent.JOIN_SESSION -> {
                System.out.println("[" + se.getUserName() + "] joined to session.");
            }
            case CMSessionEvent.LEAVE_SESSION -> {
                System.out.println("[" + se.getUserName() + "] leaved session.");
            }
        }
    }

    private void processDummyEvent(CMEvent cme) {
        CMDummyEvent e = (CMDummyEvent) cme;

        System.out.println("[" + e.getSender() + "] " + e.getDummyInfo());
        serverStub.cast(e, e.getHandlerSession(), e.getHandlerGroup());
        System.out.println("<cast> " + e.getDummyInfo());
    }

    private void processSessionEvent(CMEvent cme) {
        CMSessionEvent se = (CMSessionEvent) cme;
        if (se.getID() == CMSessionEvent.LOGIN) {
            System.out.println("[" + se.getUserName() + "] requests login.");
        }
    }
}