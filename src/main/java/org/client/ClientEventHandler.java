package org.client;

import kr.ac.konkuk.ccslab.cm.event.CMDataEvent;
import kr.ac.konkuk.ccslab.cm.event.CMSessionEvent;
import org.common.BaseEventHandler;
import org.protocol.ServersideProtocol;

import java.util.function.Function;

public class ClientEventHandler extends BaseEventHandler {
    private final Function<Void, Void> onClientUpdated;
    private final Function<Void, Void> onLoginFailed;

    public ClientEventHandler(
            ClientStub clientStub,
            Function<Void, Void> onClientUpdated,
            Function<Void, Void> onLoginFailed
    ) {
        super(ServersideProtocol::parse, clientStub);
        this.onClientUpdated = onClientUpdated;
        this.onLoginFailed = onLoginFailed;
    }

    @Override
    protected void processLoginAckEvent(CMSessionEvent se) {
        if (se.isValidUser() == -1) {
            System.out.println("@ 로그인 실패!");
            onLoginFailed.apply(null);
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
        onClientUpdated.apply(null);
    }

    @Override
    protected void processRemoveUserEvent(CMDataEvent de) {
        System.out.println("@ 잘가라...");
        ((ClientStub) stub).removeClient(de.getUserName());
        onClientUpdated.apply(null);
    }
}
