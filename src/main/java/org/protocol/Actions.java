package org.protocol;

public enum Actions {
    ADD("add"),
    EDIT("edit"),
    REMOVE("remove");

    final String value;

    Actions(String value) {
        this.value = value;
    }
}