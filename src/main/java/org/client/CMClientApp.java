package org.client;

import kr.ac.konkuk.ccslab.cm.entity.CMUser;
import kr.ac.konkuk.ccslab.cm.event.CMDummyEvent;
import kr.ac.konkuk.ccslab.cm.info.CMInteractionInfo;
import kr.ac.konkuk.ccslab.cm.stub.CMClientStub;

public class CMClientApp {
    private CMClientStub clientStub;
    private CMClientEventHandler eventHandler;

    public CMClientApp() {
        clientStub = new CMClientStub();
        eventHandler = new CMClientEventHandler(clientStub);
    }

    public CMClientStub getClientStub() {
        return clientStub;
    }

    public CMClientEventHandler getClientEventHandler() {
        return eventHandler;
    }

    public static void main(String[] args) {
        CMClientApp client = new CMClientApp();
        CMClientStub cmStub = client.getClientStub();
        cmStub.setAppEventHandler(client.getClientEventHandler());
        cmStub.loginCM("testUserName", "test");
        cmStub.joinSession("session1");
        cmStub.changeGroup("g1");
        cmStub.startCM();

        CMInteractionInfo interactionInfo = cmStub.getCMInfo().getInteractionInfo();
        CMUser myself = interactionInfo.getMyself();

        CMDummyEvent testEvent = new CMDummyEvent();

        testEvent.setHandlerSession(myself.getCurrentSession());
        testEvent.setHandlerGroup(myself.getCurrentGroup());
        testEvent.setDummyInfo("dummy text to test event casting functionality");

        cmStub.cast(testEvent, myself.getCurrentSession(), myself.getCurrentGroup());

        System.out.println("<cast> " + testEvent.getDummyInfo());
    }
}