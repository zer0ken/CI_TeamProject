package org.client;

import kr.ac.konkuk.ccslab.cm.event.CMDataEvent;
import kr.ac.konkuk.ccslab.cm.event.CMDummyEvent;
import kr.ac.konkuk.ccslab.cm.event.CMSessionEvent;
import org.common.EventHandler;
import org.gui.App;
import org.gui.ShapesViewModel;
import org.gui.shapes.Rectangle;
import org.gui.shapes.Shape;
import org.gui.shapes.Style;
import org.protocol.Command;
import org.protocol.ServersideProtocol;

import java.awt.*;

public class ClientEventHandler extends EventHandler {
    private App app;
    private ShapesViewModel shapesViewModel;

    public ClientEventHandler(ClientStub clientStub, App app, ShapesViewModel shapesViewModel) {
        super(ServersideProtocol::parse, clientStub);
        this.app = app;
        this.shapesViewModel = shapesViewModel;
    }

    @Override
    protected void processLoginAckEvent(CMSessionEvent se) {
        if (se.isValidUser() == -1) {
            System.out.println("@ 로그인 실패!");
            ((ClientStub) stub).requestLogin(true);
        }
    }

    @Override
    protected void processInhabitantEvent(CMDataEvent de) {
        if (stub.getMyself().getName().equals(de.getUserName())) {
            return;
        }

        System.out.println("@ 선임을 정확하게 알아야지요");
        System.out.println("\tuser: " + de.getUserName());

        ((ClientStub) stub).addClient(de.getUserName());
    }

    @Override
    protected void processNewUserEvent(CMDataEvent de) {
        System.out.println("@ 신병받아라");
        System.out.println("\tuser: " + de.getUserName());
        
        ((ClientStub) stub).addClient(de.getUserName());
        app.updateClientsWindow();
    }

    @Override
    protected void processRemoveUserEvent(CMDataEvent de) {
        System.out.println("@ 잘가라...");
        ((ClientStub) stub).removeClient(de.getUserName());
        app.updateClientsWindow();
    }

    @Override
    protected void processAddShapeEvent(CMDummyEvent de, Command cmd) {
//        shapesViewModel.createByServer(cmd.getId(), cmd.getShape());
        Shape serverShape = new Rectangle();
        serverShape.setId(cmd.getId());
        serverShape.setStyle(new Style(4, Color.black, Color.green, 4, Color.black, "server-client-test"));
        serverShape.setLocation(100, 100, 200, 200);
        shapesViewModel.createByServer(cmd.getId(), serverShape);
    }

    @Override
    protected void processEditShapeEvent(CMDummyEvent de, Command cmd) {
//        shapesViewModel.modifyByServer(cmd.getId(), cmd.getShape());
        Shape serverShape = new Rectangle();
        serverShape.setId(cmd.getId());
        serverShape.setStyle(new Style(4, Color.black, Color.green, 4, Color.black, "server-client-test"));
        serverShape.setLocation(100, 100, 200, 200);
        shapesViewModel.modifyByServer(cmd.getId(), serverShape);
    }

    @Override
    protected void processRemoveShapeEvent(CMDummyEvent de, Command cmd) {
        shapesViewModel.removeByServer(cmd.getId());
    }
}
