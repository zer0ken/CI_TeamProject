package org.client;

import org.client.gui.App;
import org.client.gui.AppViewModel;

public class Client {
    private final ClientStub clientStub;
    private final ClientEventHandler eventHandler;
    private final App app;
    private final AppViewModel appViewModel;

    public Client() {
        appViewModel = new AppViewModel();
        clientStub = new ClientStub(appViewModel);
        app = new App(appViewModel, clientStub);
        eventHandler = new ClientEventHandler(clientStub, app, appViewModel);
        clientStub.setAppEventHandler(eventHandler);
        clientStub.startCM();
        clientStub.requestLogin(false);
    }

    public static void main(String[] args) {
        new Client();
    }
}