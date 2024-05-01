package org.client.gui.components;

import org.client.gui.Constants;
import org.client.gui.models.ClientListModel;
import org.client.gui.models.ClientListSelectionModel;

import javax.swing.*;
import java.awt.*;

public class ClientsWindow extends ComponentJPanel {
    JList<String> clientList;

    public ClientsWindow() {
        super(Constants.CLIENTS_WINDOW_SIZE);
        setBorder(BorderFactory.createTitledBorder(Constants.CLIENTS_WINDOW_TITLE));
        setLayout(new BorderLayout());

        clientList = new JList<>();
        ClientListModel clientListModel;
        clientList.setModel(clientListModel = new ClientListModel());
        clientList.setSelectionModel(new ClientListSelectionModel(clientListModel));

        JScrollPane scrollPane = new JScrollPane(
                clientList,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        scrollPane.setPreferredSize(new Dimension(200, 300));

        add(BorderLayout.CENTER, scrollPane);
    }
}
