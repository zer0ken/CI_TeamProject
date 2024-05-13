package org.client.gui.models;

import java.util.function.Supplier;

class UndoableAction {

    public enum Bulk {
        NONE, RESIZE, MOVE
    }

    private Bulk bulk;
    private Runnable perform;
    private final Supplier<Boolean> undo;

    UndoableAction(Runnable perform, Supplier<Boolean> undo) {
        this.perform = perform;
        this.undo = undo;
    }

    UndoableAction(Bulk bulk, Runnable perform, Supplier<Boolean> undo) {
        this(perform, undo);
        this.bulk = bulk;
    }

    public Bulk getBulk() {
        return bulk;
    }

    public void extend(UndoableAction next) {
        perform = next.perform;
    }

    void perform() {
        perform.run();
    }

    boolean undo() {
        return undo.get();
    }
}
