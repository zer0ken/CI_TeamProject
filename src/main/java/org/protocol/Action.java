package org.protocol;

public enum Action {
    ADD("add"),
    EDIT("edit"),
    REMOVE("remove"),
    CLEAR("clear");

    final String value;

    Action(String value) {
        this.value = value;
    }
}