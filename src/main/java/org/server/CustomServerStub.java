package org.server;

import kr.ac.konkuk.ccslab.cm.stub.CMServerStub;

import java.util.ArrayList;

public class CustomServerStub extends CMServerStub {
    private final ArrayList<String> clients = new ArrayList<>();

    public ArrayList<String> getClients() {
        return clients;
    }

    public String addClient(String name) {
        String nameToAdd = name;
        int duplacatedCount = 0;
        while (clients.contains(nameToAdd)) {
            nameToAdd = name + " " + (++duplacatedCount);
        }
        clients.add(nameToAdd);

        return nameToAdd;
    }

    public void removeClient(String name) {
        clients.remove(name);
    }
}
