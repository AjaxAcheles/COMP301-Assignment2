package grail.shapes.classes;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import grail.shapes.interfaces.LocatableInterface;
import util.annotations.EditablePropertyNames;
import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;
import util.annotations.Visible;

@StructurePattern(StructurePatternNames.BEAN_PATTERN)
@PropertyNames({"X", "Y"})
@EditablePropertyNames({"X", "Y"})
public class Locatable implements LocatableInterface {
    private int x;
    private int y;
    private List<PropertyChangeListener> propertyChangeListeners;

    public Locatable(int x, int y) {
        this.x = x;
        this.y = y;
        this.propertyChangeListeners = new ArrayList<PropertyChangeListener>();
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public void setX(int newX) {
        int oldX = this.x;
        this.x = newX;
        this.notifyAllListeners(new PropertyChangeEvent(this, "X", oldX, newX));
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public void setY(int newY) {
        int oldY = this.y;
        this.y = newY;
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
        for (PropertyChangeListener listener : this.propertyChangeListeners) {
            listener.propertyChange(event);
        }
    }
}
