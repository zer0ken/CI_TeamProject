package org.client.gui.models;

import javax.swing.*;

public class ClientListModel extends DefaultListModel<String> {
    public ClientListModel() {
        AppViewModel appViewModel = AppViewModel.getInstance();
        appViewModel.addStringListener(AppViewModel.Listener.JOIN, this::add);
        appViewModel.addStringListener(AppViewModel.Listener.LEAVE, this::remove);
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
