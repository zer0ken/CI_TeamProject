package org.client;

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

        cmStub.startCM();

        cmStub.syncLoginCM("testUserName", "test");
        cmStub.changeGroup("g1");
        System.out.println("### login done");
    }
}