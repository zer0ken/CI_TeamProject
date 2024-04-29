package org.client.gui.components;

import org.client.gui.Constants;
import org.client.gui.AppViewModel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ClientsWindow extends ComponentJPanel {
    DefaultListModel<String> clientListModel;
    JList<String> clientList;

    public ClientsWindow(AppViewModel appViewModel) {
        super(Constants.CLIENTS_WINDOW_SIZE, appViewModel);
        setBorder(BorderFactory.createTitledBorder(Constants.CLIENTS_WINDOW_TITLE));
        setLayout(new BorderLayout());

        clientListModel = new DefaultListModel<>();

        clientList = new JList<>(clientListModel);

        JScrollPane scrollPane = new JScrollPane(
                clientList,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        scrollPane.setPreferredSize(new Dimension(200, 300));

        add(BorderLayout.CENTER, scrollPane);
    }

    public void setClients(List<String> clients) {
        clientListModel.removeAllElements();
        clientListModel.addAll(clients);
    }
}
