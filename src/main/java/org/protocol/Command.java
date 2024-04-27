package org.protocol;

public class Command {
    private Actions action = null;
    private Long id = null;
    private String shape = null;

    public Actions getAction() {
        return action;
    }

    public void setAction(Actions action) {
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
