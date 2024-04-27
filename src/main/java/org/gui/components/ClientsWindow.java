package org.gui.components;

import org.gui.ShapesViewModel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static org.gui.components._Constants.CLIENTS_WINDOW_SIZE;
import static org.gui.components._Constants.CLIENTS_WINDOW_TITLE;

public class ClientsWindow extends _ComponentJPanel {
    DefaultListModel<String> clientListModel;
    JList<String> clientList;

    ClientsWindow(ShapesViewModel shapesViewModel) {
        super(CLIENTS_WINDOW_SIZE, shapesViewModel);
        setBorder(BorderFactory.createTitledBorder(CLIENTS_WINDOW_TITLE));
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
