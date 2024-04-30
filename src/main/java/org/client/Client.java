package org.client;

import org.client.gui.App;
import org.client.gui.models.AppViewModel;

public class Client {
    Client() {
        AppViewModel appViewModel = new AppViewModel();
        ClientStub clientStub = new ClientStub(appViewModel);
        new App(appViewModel, unused -> clientStub.requestLeave());
        ClientEventHandler eventHandler = new ClientEventHandler(clientStub, appViewModel);
        clientStub.setAppEventHandler(eventHandler);
        clientStub.startCM();
        clientStub.requestLogin(false);
    }

    public static void main(String[] args) {
        new Client();
    }
}