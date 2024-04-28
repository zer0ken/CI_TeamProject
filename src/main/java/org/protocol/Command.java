package org.protocol;

public class Command {
    private Action action = null;
    private Long id = null;
    private String shape = null;

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    @Override
    public String toString() {
        return "Command { action = " + action.value + ", id = " + id + ", shape = " + shape + " }";
    }
}
