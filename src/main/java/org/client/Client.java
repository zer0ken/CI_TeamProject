package org.client;

import org.client.gui.App;
import org.client.gui.AppViewModel;

public class Client {
    public static void main(String[] args) {
        AppViewModel appViewModel = new AppViewModel();
        ClientStub clientStub = new ClientStub(appViewModel);
        App app = new App(appViewModel, unused -> clientStub.requestLeave());
        ClientEventHandler eventHandler = new ClientEventHandler(clientStub, app, appViewModel);
        clientStub.setAppEventHandler(eventHandler);
        clientStub.startCM();
        clientStub.requestLogin(false);
    }
}