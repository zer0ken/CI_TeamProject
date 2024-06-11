package org.client.gui.shape;

import java.awt.*;

public class Handle implements Drawable, Interactable {
    private static final int HANDLE_SIZE = 6;

    private Shape target;
    private final Location location;

    public Handle(Location location) {
        this.location = location;
    }

    public void setTarget(Shape target) {
        this.target = target;
    }

    private int getX() {
        if (target.getType() == Shape.Type.LINE) {
            return switch (location) {
                case NW -> target.x1;
                case SE -> target.x2;
                default -> throw new IllegalStateException("Unexpected value: " + location);
            };
        }
        return switch (location) {
            case NW, W, SW -> target.getX1();
            case N, S -> target.getX1() + target.getWidth() / 2;
            case NE, E, SE -> target.getX2();
        };
    }

    private int getY() {
        if (target.getType() == Shape.Type.LINE) {
            return switch (location) {
                case NW -> target.y1;
                case SE -> target.y2;
                default -> throw new IllegalStateException("Unexpected value: " + location);
            };
        }
        return switch (location) {
            case NW, N, NE -> target.getY1();
            case W, E -> target.getY1() + target.getHeight() / 2;
            case SW, S, SE -> target.getY2();
        };
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public void draw(Graphics g) {
        if (target == null) {
            return;
        }
        if (target.getType() == Shape.Type.LINE
                && location != Location.NW && location != Location.SE) {
            return;
        }
        g.setColor(Color.BLUE);
        g.drawRect(getX() - HANDLE_SIZE / 2, getY() - HANDLE_SIZE / 2, HANDLE_SIZE, HANDLE_SIZE);
    }

    @Override
    public boolean contains(Point point) {
        if (target == null) {
            return false;
        }
        if (target.getType() == Shape.Type.LINE
                && location != Location.NW && location != Location.SE) {
            return false;
        }

        return getX() - HANDLE_SIZE / 2 <= point.x && point.x <= getX() + HANDLE_SIZE / 2
                && getY() - HANDLE_SIZE / 2 <= point.y && point.y <= getY() + HANDLE_SIZE / 2;
    }

    public enum Location {
        NW, N, NE, E, SE, S, SW, W
    }
}
