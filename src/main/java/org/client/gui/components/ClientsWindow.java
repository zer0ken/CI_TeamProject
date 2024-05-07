package org.client.gui.components;

import org.client.gui.Theme;
import org.client.gui.Utils;
import org.client.gui.models.ClientListModel;
import org.client.gui.models.ClientListSelectionModel;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

import static org.client.gui.Constants.*;

public class ClientsWindow extends JPanel {
    JList<String> clientList;

    public ClientsWindow() {
        setLayout(new BorderLayout());
        setPreferredSize(CLIENTS_WINDOW_SIZE);

        TitlePanel titlePanel = new TitlePanel(CLIENTS_WINDOW_TITLE, CLIENTS_WINDOW_TOOLTIP);
        titlePanel.setBorder(new MatteBorder(0, 1, 0, 0, Theme.getBorderColor()));

        clientList = new JList<>();
        ClientListModel clientListModel;
        clientList.setModel(clientListModel = new ClientListModel());
        clientList.setSelectionModel(new ClientListSelectionModel(clientListModel));

        JScrollPane scrollPane = Utils.wrapWithScrollPane(clientList);

        add(titlePanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
}
