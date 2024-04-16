package org.server;

import kr.ac.konkuk.ccslab.cm.event.CMDummyEvent;
import kr.ac.konkuk.ccslab.cm.stub.CMServerStub;
import org.common.HasShapeMap;
import org.common.ShapeMap;

import java.util.TreeMap;

public class ServerStub extends CMServerStub implements HasShapeMap {
    ShapeMap shapes;

    public void castDummy(String message, String session, String group) {
        CMDummyEvent fromServer = new CMDummyEvent();
        fromServer.setHandlerSession(session);
        fromServer.setHandlerGroup(group);
        fromServer.setDummyInfo(message);

        cast(fromServer, session, group);
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
