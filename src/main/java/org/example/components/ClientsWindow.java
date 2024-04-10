package org.example.components;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static org.example.components._Constants.CLIENTS_WINDOW_SIZE;
import static org.example.components._Constants.CLIENTS_WINDOW_TITLE;

public class ClientsWindow extends _ComponentJPanel {
    DefaultListModel<String> clientListModel;
    JList<String> clientList;

    ClientsWindow() {
        super(CLIENTS_WINDOW_SIZE);
        setBorder(BorderFactory.createTitledBorder(CLIENTS_WINDOW_TITLE));
        setLayout(new BorderLayout());

        clientListModel = new DefaultListModel<>();

        for (int i = 0; i < 100; i++) {
            clientListModel.addElement("tester "+i);
        }

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
