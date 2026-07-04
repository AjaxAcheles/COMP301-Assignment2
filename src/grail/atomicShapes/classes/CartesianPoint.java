package grail.atomicShapes.classes;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import grail.atomicShapes.interfaces.PointInterface;
import util.annotations.Visible;

public class CartesianPoint implements PointInterface {
    private int x;
    private int y;
    private List<PropertyChangeListener> propertyChangeListeners;

    public CartesianPoint(int x, int y) {
        this.x = x;
        this.y = y;
        this.propertyChangeListeners = new ArrayList<PropertyChangeListener>();
    }

    @Override
    public int getX() { 
        return x; 
    }
    @Override
    public int getY() { 
        return y; 
    }
    
    @Override
    public double getAngle() {
        return Math.atan2(y, x);
    }
    @Override
    public double getRadius() { 
        return Math.sqrt(x * x + y * y); 
    }
    
    @Override
    public void setX(int x) {
        int oldX = this.x;
        this.x = x;
        this.notifyAllListeners(new PropertyChangeEvent(this, "X", oldX, x));
    }
    
    @Override
    public void setY(int y) {
        int oldY = this.y;
        this.y = y;
        this.notifyAllListeners(new PropertyChangeEvent(this, "Y", oldY, y));
    }

    @Override
    public void setRadius(double radius) {
        double oldRadius = this.getRadius();
        double angleRadians = Math.atan2(y, x);
        this.x = (int) Math.round(radius * Math.cos(angleRadians));
        this.y = (int) Math.round(radius * Math.sin(angleRadians));
        this.notifyAllListeners(new PropertyChangeEvent(this, "Radius", oldRadius, radius));
    }

    @Override
    public void setAngle(double angleRadians) {
        double oldAngle = this.getAngle();
        double radius = getRadius();
        this.x = (int) Math.round(radius * Math.cos(angleRadians));
        this.y = (int) Math.round(radius * Math.sin(angleRadians));
        this.notifyAllListeners(new PropertyChangeEvent(this, "Angle", oldAngle, angleRadians));
    }

    @Override
    @Visible(false)
    public List<PropertyChangeListener> getPropertyChangeListeners() {
        return this.propertyChangeListeners;
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.propertyChangeListeners.add(listener);
    }

    private void notifyAllListeners(PropertyChangeEvent event) {
        for (PropertyChangeListener listener : this.propertyChangeListeners) {
            listener.propertyChange(event);
        }
    }
}
