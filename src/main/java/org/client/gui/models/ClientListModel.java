package org.client.gui.models;

import javax.swing.*;

public class ClientListModel extends DefaultListModel<String> {
    public ClientListModel() {
        AppModel appModel = AppModel.getInstance();
        appModel.addStringListener(AppModel.Listener.JOIN, this::add);
        appModel.addStringListener(AppModel.Listener.LEAVE, this::remove);
    }

    private Void add(String name) {
        add(0, name);
        return null;
    }

    private Void remove(String name) {
        removeElement(name);
        return null;
    }
}
