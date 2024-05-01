package org.client.gui.models;

import javax.swing.*;

public class ClientListSelectionModel extends DefaultListSelectionModel {
    private final ClientListModel clientListModel;

    public ClientListSelectionModel(ClientListModel clientListModel) {
        this.clientListModel = clientListModel;
        setSelectionMode(SINGLE_SELECTION);
        AppModel.getInstance().addStringListener(AppModel.Listener.SET_NAME, this::selectNameAndFix);
    }

    public Void selectNameAndFix(String name) {
        setSelectionInterval(0, clientListModel.indexOf(name));
        addListSelectionListener((e) -> setSelectionInterval(0, clientListModel.indexOf(name)));
        return null;
    }
}
