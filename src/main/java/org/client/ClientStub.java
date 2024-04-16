package org.client;

import kr.ac.konkuk.ccslab.cm.event.CMDummyEvent;
import kr.ac.konkuk.ccslab.cm.stub.CMClientStub;
import org.common.HasShapeMap;
import org.common.ShapeMap;

import java.util.TreeMap;

public class ClientStub extends CMClientStub implements HasShapeMap {
    private ShapeMap shapes;

    public void sendDummy(String message) {
        CMDummyEvent fromClient = new CMDummyEvent();
        fromClient.setHandlerSession(getMyself().getCurrentSession());
        fromClient.setHandlerGroup(getMyself().getCurrentGroup());
        fromClient.setDummyInfo(message);

        send(fromClient, "SERVER");
        System.out.println("# server casted\n\t" + message);
    }

    @Override
    public void setShapes(TreeMap<Long, String> newShapes) {
        shapes.setShapes(newShapes);
    }

    @Override
    public TreeMap<Long, String> getShapes() {
        return shapes.getShapes();
    }

    @Override
    public void putShape(long id, String shape) {
        shapes.putShape(id, shape);
    }

    @Override
    public void removeShape(long id) {
        shapes.removeShape(id);
    }
}
