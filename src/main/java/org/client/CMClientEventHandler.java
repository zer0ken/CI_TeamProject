package org.client;

import kr.ac.konkuk.ccslab.cm.event.CMDummyEvent;
import kr.ac.konkuk.ccslab.cm.event.CMEvent;
import kr.ac.konkuk.ccslab.cm.event.CMSessionEvent;
import kr.ac.konkuk.ccslab.cm.event.handler.CMAppEventHandler;
import kr.ac.konkuk.ccslab.cm.info.CMInfo;
import kr.ac.konkuk.ccslab.cm.stub.CMClientStub;

public class CMClientEventHandler implements CMAppEventHandler {
    public CMClientEventHandler(CMClientStub mClientStub) {
    }

    @Override
    public void processEvent(CMEvent cme) {
        switch (cme.getType()) {
            case CMInfo.CM_SESSION_EVENT -> processSessionEvent(cme);
            case CMInfo.CM_DUMMY_EVENT -> processDummyEvent(cme);
        }

    }

    private void processDummyEvent(CMEvent cme) {
        CMDummyEvent e = (CMDummyEvent) cme;

        System.out.println("[" + e.getSender() + "] " + e.getDummyInfo());
    }

    private void processSessionEvent(CMEvent cme) {
    }
}
