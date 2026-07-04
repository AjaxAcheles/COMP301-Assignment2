package grail.atomicShapes.classes;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import grail.atomicShapes.interfaces.PointInterface;
import tags301.Comp301Tags;
import util.annotations.EditablePropertyNames;
import util.annotations.Explanation;
import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;
import util.annotations.Tags;
import util.annotations.Visible;
import util.models.AListenableVector;

@Tags(Comp301Tags.LOCATABLE)
@StructurePattern(StructurePatternNames.POINT_PATTERN)
@Explanation("Location in Java coordinate System.")
@PropertyNames({"X", "Y", "Angle", "Radius", "PropertyChangeListeners"})
@EditablePropertyNames({"X", "Y", "Angle", "Radius"})
public class CartesianPoint implements PointInterface {
    private int x;
    private int y;
    private List<PropertyChangeListener> propertyChangeListeners;

    public CartesianPoint(int x, int y) {
        this.x = x;
        this.y = y;
        this.propertyChangeListeners = new AListenableVector<PropertyChangeListener>();
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
        int listenerIndex = 0;
        while (listenerIndex < this.propertyChangeListeners.size()) {
            PropertyChangeListener listener = this.propertyChangeListeners.get(listenerIndex);
            listener.propertyChange(event);
            listenerIndex++;
        }
    }
}
