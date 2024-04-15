package org.server;

import kr.ac.konkuk.ccslab.cm.event.CMDummyEvent;
import kr.ac.konkuk.ccslab.cm.event.CMEvent;
import kr.ac.konkuk.ccslab.cm.event.CMSessionEvent;
import kr.ac.konkuk.ccslab.cm.event.handler.CMAppEventHandler;
import kr.ac.konkuk.ccslab.cm.info.CMInfo;

import java.util.StringTokenizer;

public class CMServerEventHandler implements CMAppEventHandler {
    private final CustomServerStub serverStub;

    public CMServerEventHandler(CustomServerStub serverStub) {
        this.serverStub = serverStub;
    }

    @Override
    public void processEvent(CMEvent cme) {
        switch (cme.getType()) {
            case CMInfo.CM_SESSION_EVENT -> processSessionEvent(cme);
            case CMInfo.CM_DUMMY_EVENT -> processDummyEvent(cme);
        }
    }

    private void processSessionEvent(CMEvent cme) {
        CMSessionEvent se = (CMSessionEvent) cme;

        switch (se.getID()) {
            case CMSessionEvent.JOIN_SESSION -> {
                System.out.println("[" + se.getUserName() + "] joined to session.");
            }
            case CMSessionEvent.LEAVE_SESSION -> {
                System.out.println("[" + se.getUserName() + "] leaved session.");
            }
        }
    }

    private void processDummyEvent(CMEvent cme) {
        CMDummyEvent e = (CMDummyEvent) cme;
        System.out.println("[" + e.getSender() + "] " + e.getDummyInfo());

        StringTokenizer tokenizer = new StringTokenizer(e.getDummyInfo(), "$");
        String action = tokenizer.nextToken();
        System.out.println("action: " + action);
        switch (action) {
            case "add" -> processAddEvent(e, tokenizer);
            case "edit" -> processEditEvent(e, tokenizer);
            case "remove" -> processRemoveEvent(e, tokenizer);
        }
    }

    private void processAddEvent(CMDummyEvent de, StringTokenizer tokenizer) {
        try {
            long id = System.currentTimeMillis();
            String shape = tokenizer.nextToken();
            System.out.println("@ added new shape with id " + id + ": " + shape);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void processEditEvent(CMDummyEvent de, StringTokenizer tokenizer) {
        try {
            long id = Long.parseLong(tokenizer.nextToken());
            String shape = tokenizer.nextToken();
            System.out.println("@ edited shape with id " + id +": " + shape);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void processRemoveEvent(CMDummyEvent de, StringTokenizer tokenizer){
        try {
            long id = Long.parseLong(tokenizer.nextToken());
            System.out.println("@ removed shape with id " + id);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}