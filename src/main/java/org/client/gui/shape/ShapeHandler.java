package org.client.gui.shape;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class ShapeHandler implements Drawable, Interactable {
    private Shape target;
    private float aspect;
    private Map<Handle.Location, Handle> handles = new LinkedHashMap<>();

    private boolean dragging = false;
    private Handle selectedHandle = null;
    private Point lastP = null;

    public ShapeHandler() {
        for (Handle.Location location : Handle.Location.values()) {
            handles.put(location, new Handle(location));
        }
    }

    public void setTarget(Shape target) {
        this.target = target;
        for (Handle handle : handles.values()) {
            handle.setTarget(target);
        }
    }

    public void startDrag(Point p) {
        for (Handle handle : handles.values()) {
            if (handle.contains(p)) {
                selectedHandle = handle;
            }
        }
        lastP = p;
        dragging = true;
        aspect = (float) this.target.getHeight() / target.getWidth();
    }

    public void startDrag(Shape target){
        setTarget(target);
        selectedHandle = handles.get(Handle.Location.SE);
        dragging = true;
        aspect = 1.0f;
    }

    public Shape drag(Point p) {
        int dx = p.x - lastP.x;
        int dy = p.y - lastP.y;
        lastP = p;

        if (selectedHandle != null) {
            setTarget(moveSelectedHandle(dx, dy));
        } else {
            setTarget(target.derive(
                    target.x1 + dx, target.y1 + dy,
                    target.x2 + dx, target.y2 + dy));
        }
        return target;
    }

    private Shape moveSelectedHandle(int dx, int dy) {
        int x1 = target.x1;
        int y1 = target.y1;
        int x2 = target.x2;
        int y2 = target.y2;

        if (target.getType() == Shape.Type.LINE) {
            switch (selectedHandle.getLocation()) {
                case NW -> {
                    x1 = target.x1 + dx;
                    y1 = target.y1 + dy;
                }
                case SE -> {
                    x2 = target.x2 + dx;
                    y2 = target.y2 + dy;
                }
                default -> throw new IllegalStateException("Unexpected value: " + selectedHandle.getLocation());
            }
            setTarget(target.derive(x1, y1, x2, y2));
            return target;
        }

        switch (selectedHandle.getLocation()) {
            case W -> x1 += dx;
            case E -> x2 += dx;
            case N -> y1 += dy;
            case S -> y2 += dy;
            case NW -> {
                x1 += dx;
                y1 += (int) (dx * aspect);
            }
            case NE -> {
                x2 += dx;
                y1 -= (int) (dx * aspect);
            }
            case SE -> {
                x2 += dx;
                y2 += (int) (dx * aspect);
            }
            case SW -> {
                x1 += dx;
                y2 -= (int) (dx * aspect);
            }
        }

        setTarget(target.derive(x1, y1, x2, y2));

        switch (selectedHandle.getLocation()) {
            case N, E, S, W -> aspect = (float) target.getHeight() / target.getWidth();
        }

        return target;
    }

    public void finishDrag() {
        dragging = false;
        selectedHandle = null;
    }

    public boolean isDragging() {
        return dragging;
    }

    public boolean isMoving() {
        return selectedHandle == null;
    }

    public Shape getTarget() {
        return target;
    }

    @Override
    public void draw(Graphics g) {
        if (target != null) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(1));
            g.setColor(Color.BLUE);
            g.drawRect(target.getX1() - 1, target.getY1() - 1, target.getWidth() + 2, target.getHeight() + 2);

            for (Handle handle : handles.values()) {
                handle.draw(g);
            }
        }
    }

    @Override
    public boolean contains(Point point) {
        if (target != null) {
            if (target.contains(point)) {
                return true;
            }
            for (Handle handle : handles.values()) {
                if (handle.contains(point)) {
                    return true;
                }
            }
        }
        return false;
    }
}
