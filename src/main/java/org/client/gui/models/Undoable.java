package org.client.gui.models;

import java.util.function.Supplier;

public interface Undoable {
    void perform();
    boolean undo();

    static Undoable of(Runnable perform, Supplier<Boolean> undo) {
        return new Undoable() {
            @Override
            public void perform() {
                perform.run();
            }

            @Override
            public boolean undo() {
                return undo.get();
            }
        };
    }
}
