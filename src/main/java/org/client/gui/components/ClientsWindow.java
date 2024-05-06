package org.client.gui.components;

import org.client.gui.models.ClientListModel;
import org.client.gui.models.ClientListSelectionModel;

import javax.swing.*;
import java.awt.*;

import static org.client.gui.Constants.CLIENTS_WINDOW_SIZE;
import static org.client.gui.Constants.CLIENTS_WINDOW_TITLE;

public class ClientsWindow extends TitledBorderJPanel {
    JList<String> clientList;

    public ClientsWindow() {
        super(CLIENTS_WINDOW_TITLE);
        setPreferredSize(CLIENTS_WINDOW_SIZE);

        clientList = new JList<>();
        ClientListModel clientListModel;
        clientList.setModel(clientListModel = new ClientListModel());
        clientList.setSelectionModel(new ClientListSelectionModel(clientListModel));

        JScrollPane scrollPane = new JScrollPane(
                clientList,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        add(scrollPane, BorderLayout.CENTER);
    }
}
