package org.server;

import kr.ac.konkuk.ccslab.cm.stub.CMServerStub;

import java.util.ArrayList;
import java.util.TreeMap;

public class CustomServerStub extends CMServerStub {
    private final ArrayList<String> clients = new ArrayList<>();
    private final TreeMap<Integer, Object> shapes = new TreeMap<>();

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

    public TreeMap<Integer, Object> getShapes() {
        return shapes;
    }

    public void putShape(int id, Object shape) {
        shapes.put(id, shape);
    }

    public void removeShape(int id) {
        shapes.remove(id);
    }
}
