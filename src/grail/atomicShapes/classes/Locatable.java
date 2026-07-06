package grail.atomicShapes.classes;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import grail.atomicShapes.interfaces.LocatableInterface;
import tags301.Comp301Tags;
import util.annotations.EditablePropertyNames;
import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;
import util.annotations.Tags;
import util.annotations.Visible;
import util.models.AListenableVector;

@Tags(Comp301Tags.LOCATABLE)
@StructurePattern(StructurePatternNames.BEAN_PATTERN)
@PropertyNames({"X", "Y", "PropertyChangeListeners"})
@EditablePropertyNames({"X", "Y"})
public class Locatable implements LocatableInterface {
    private int xCoordinate;
    private int yCoordinate;
    private final List<PropertyChangeListener> propertyChangeListeners;

    public Locatable(int initialX, int initialY) {
        this.xCoordinate = initialX;
        this.yCoordinate = initialY;
        this.propertyChangeListeners = new AListenableVector<PropertyChangeListener>();
    }

    @Override
    public int getX() {
        return this.xCoordinate;
    }

    @Override
    public void setX(int newX) {
        int oldX = this.xCoordinate;
        this.xCoordinate = newX;
        this.locationChanged(newX - oldX, 0);
        this.notifyAllListeners(new PropertyChangeEvent(this, "X", oldX, newX));
    }

    @Override
    public int getY() {
        return this.yCoordinate;
    }

    @Override
    public void setY(int newY) {
        int oldY = this.yCoordinate;
        this.yCoordinate = newY;
        this.locationChanged(0, newY - oldY);
        this.notifyAllListeners(new PropertyChangeEvent(this, "Y", oldY, newY));
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

    protected void notifyAllListeners(PropertyChangeEvent event) {
        int listenerIndex = 0;
        while (listenerIndex < this.propertyChangeListeners.size()) {
            PropertyChangeListener listener = this.propertyChangeListeners.get(listenerIndex);
            listener.propertyChange(event);
            listenerIndex++;
        }
    }

    protected void locationChanged(int changeInX, int changeInY) {
    }
}
