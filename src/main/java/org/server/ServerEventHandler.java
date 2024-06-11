package org.server;

import kr.ac.konkuk.ccslab.cm.event.CMDummyEvent;
import kr.ac.konkuk.ccslab.cm.event.CMSessionEvent;
import org.protocol.Action;
import org.protocol.Command;
import org.protocol.EventHandler;
import org.protocol.Protocol;

import java.util.concurrent.locks.ReentrantLock;

public class ServerEventHandler extends EventHandler {
    private static ReentrantLock lock = new ReentrantLock();

    public ServerEventHandler(ServerStub serverStub) {
        super(serverStub);
    }

    @Override
    protected void processJoinEvent(CMSessionEvent se) {
        System.out.println("@ user joining");
        System.out.println("\tsession: " + se.getSessionName());
        System.out.println("\tgroup: " + se.getCurrentGroupName());
        System.out.println("\tuser: " + se.getUserName());
        ((ServerStub) stub).sendAll(se.getUserName());
    }

    @Override
    protected void processLogoutEvent(CMSessionEvent se) {
        System.out.println("@ user logout");
        System.out.println("\tsession: " + se.getSessionName());
        System.out.println("\tgroup: " + se.getCurrentGroupName());
        System.out.println("\tuser: " + se.getUserName());
    }

    @Override
    protected void processAddShapeEvent(CMDummyEvent de, Command cmd) {
        ((ServerStub) stub).getShapes().put(cmd.getId(), cmd.getShape());

        String message = Protocol.build(Action.ADD, cmd.getId(), cmd.getShape());
        ((ServerStub) stub).castDummy(message);
    }

    @Override
    protected void processEditShapeEvent(CMDummyEvent de, Command cmd) {
        if (((ServerStub) stub).getShapes().get(cmd.getId()) == null) {
            return;
        }
        lock.lock();
        ((ServerStub) stub).getShapes().put(cmd.getId(), cmd.getShape());
        String message = Protocol.build(Action.EDIT, cmd.getId(), cmd.getShape());
        ((ServerStub) stub).castDummy(message);
        lock.unlock();
    }

    @Override
    protected void processRemoveShapeEvent(CMDummyEvent de, Command cmd) {
        if (((ServerStub) stub).getShapes().get(cmd.getId()) == null) {
            return;
        }
        lock.lock();
        ((ServerStub) stub).getShapes().remove(cmd.getId());
        String message = Protocol.build(Action.REMOVE, cmd.getId());
        ((ServerStub) stub).castDummy(message);
        lock.unlock();
    }

    @Override
    protected void processClearShapeEvent(CMDummyEvent de, Command cmd) {
        lock.lock();
        ((ServerStub) stub).getShapes().clear();
        String message = Protocol.build(Action.CLEAR);
        ((ServerStub) stub).castDummy(message);
        lock.unlock();
    }
}