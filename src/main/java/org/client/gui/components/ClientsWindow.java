package org.client.gui.components;

import org.client.gui.AppViewModel;
import org.client.gui.Constants;

import javax.swing.*;
import java.awt.*;

public class ClientsWindow extends ComponentJPanel {
    JList<String> clientList;

    public ClientsWindow(AppViewModel appViewModel) {
        super(Constants.CLIENTS_WINDOW_SIZE, appViewModel);
        setBorder(BorderFactory.createTitledBorder(Constants.CLIENTS_WINDOW_TITLE));
        setLayout(new BorderLayout());

        clientList = new JList<>();
        clientList.setModel(appViewModel.getClientsModel());
        clientList.setSelectionModel(appViewModel.getClientsSelectionModel());

        JScrollPane scrollPane = new JScrollPane(
                clientList,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        scrollPane.setPreferredSize(new Dimension(200, 300));

        add(BorderLayout.CENTER, scrollPane);
    }
}
