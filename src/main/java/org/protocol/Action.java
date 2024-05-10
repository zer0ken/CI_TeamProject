package org.protocol;

public enum Action {
    ADD("add"),
    READD("reAdd"),
    EDIT("edit"),
    REMOVE("remove");

    final String value;

    Action(String value) {
        this.value = value;
    }
}