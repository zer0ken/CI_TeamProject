package org.protocol;

public class Command {
    private Action action = null;
    private String id = null;
    private String shape = null;

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
