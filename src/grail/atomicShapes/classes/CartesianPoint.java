package grail.atomicShapes.classes;

import java.beans.PropertyChangeEvent;

import grail.atomicShapes.interfaces.PointInterface;
import util.annotations.EditablePropertyNames;
import util.annotations.Explanation;
import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;

@StructurePattern(StructurePatternNames.POINT_PATTERN)
@Explanation("Location in Java coordinate System.")
@PropertyNames({"X", "Y", "Angle", "Radius", "PropertyChangeListeners"})
@EditablePropertyNames({"X", "Y", "Angle", "Radius"})
public class CartesianPoint extends Locatable implements PointInterface {

    public CartesianPoint(int initialX, int initialY) {
        super(initialX, initialY);
    }
    
    @Override
    public double getAngle() {
        return Math.atan2(this.getY(), this.getX());
    }
    @Override
    public double getRadius() { 
        return Math.sqrt(this.getX() * this.getX() + this.getY() * this.getY()); 
    }

    @Override
    public void setRadius(double newRadius) {
        double oldRadius = this.getRadius();
        double angleRadians = this.getAngle();
        this.setX((int) Math.round(newRadius * Math.cos(angleRadians)));
        this.setY((int) Math.round(newRadius * Math.sin(angleRadians)));
        this.notifyAllListeners(new PropertyChangeEvent(this, "Radius", oldRadius, newRadius));
    }

    @Override
    public void setAngle(double angleRadians) {
        double oldAngle = this.getAngle();
        double radius = this.getRadius();
        this.setX((int) Math.round(radius * Math.cos(angleRadians)));
        this.setY((int) Math.round(radius * Math.sin(angleRadians)));
        this.notifyAllListeners(new PropertyChangeEvent(this, "Angle", oldAngle, angleRadians));
    }
}
