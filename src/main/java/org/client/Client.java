package org.client;

import org.client.gui.App;
import org.client.gui.ShapesViewModel;

public class Client {
    private final ClientStub clientStub;
    private final ClientEventHandler eventHandler;
    private final App app;
    private final ShapesViewModel shapesViewModel;

    public Client() {
        shapesViewModel = new ShapesViewModel();
        clientStub = new ClientStub(shapesViewModel);
        app = new App(shapesViewModel, clientStub);
        eventHandler = new ClientEventHandler(clientStub, app, shapesViewModel);
        clientStub.setAppEventHandler(eventHandler);
        clientStub.startCM();
        clientStub.requestLogin(false);
    }

    public static void main(String[] args) {
        new Client();
    }
}