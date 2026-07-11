package grail.simpleShapes.classes;

import java.beans.PropertyChangeEvent;

import grail.atomicShapes.classes.PolarPoint;
import grail.atomicShapes.interfaces.PointInterface;
import grail.compositeShapes.classes.BoundedShape;
import grail.simpleShapes.interfaces.LineInterface;
import tags301.Comp301Tags;
import util.annotations.EditablePropertyNames;
import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;
import util.annotations.Tags;

@StructurePattern(StructurePatternNames.LINE_PATTERN)
@Tags(Comp301Tags.ROTATING_LINE)
@PropertyNames({"X", "Y", "Width", "Height", "Radius", "Angle", "End", "PropertyChangeListeners"})
@EditablePropertyNames({"X", "Y", "Width", "Height", "Radius", "Angle"})
public class RotatingLine extends BoundedShape implements LineInterface {
    
    private static final int DEFAULT_X = 100;
    private static final int DEFAULT_Y = 100;
    private static final double DEFAULT_RADIUS = 50.0;
    private static final double DEFAULT_ANGLE_RADIANS = Math.PI / 4;

    private final PointInterface endPoint;
    private double angleRadians;

    public RotatingLine() {
        this(DEFAULT_X, DEFAULT_Y, DEFAULT_RADIUS, DEFAULT_ANGLE_RADIANS);
    }

    
    public RotatingLine(int startX, int startY, double radius, double initialAngleRadians) {
        super(startX, startY,
                (int) Math.round(radius * Math.cos(initialAngleRadians)),
                (int) Math.round(radius * Math.sin(initialAngleRadians)));
        this.angleRadians = initialAngleRadians;
        this.endPoint = new PolarPoint(radius, initialAngleRadians);
    }

    @Override
    public double getRadius() {
        return endPoint.getRadius();
    }
    
    @Override
    public double getAngle() {
        return this.angleRadians;
    }

    @Override
    public void setRadius(double newRadius) {
        double oldRadius = this.getRadius();
        int oldWidth = this.getWidth();
        int oldHeight = this.getHeight();
        this.endPoint.setRadius(newRadius);
        this.setWidthWithoutNotification(this.endPoint.getX());
        this.setHeightWithoutNotification(this.endPoint.getY());
        this.notifyAllListeners(new PropertyChangeEvent(this, "Radius", oldRadius, newRadius));
        this.notifyAllListeners(new PropertyChangeEvent(this, "Width", oldWidth, this.getWidth()));
        this.notifyAllListeners(new PropertyChangeEvent(this, "Height", oldHeight, this.getHeight()));
    }

    @Override
    public PointInterface getEnd() {
        return this.endPoint;
    }

    @Override
    public void setAngle(double newAngleRadians) {
        double oldAngle = this.angleRadians;
        int oldWidth = this.getWidth();
        int oldHeight = this.getHeight();
        this.angleRadians = newAngleRadians;
        this.endPoint.setAngle(newAngleRadians);
        this.setWidthWithoutNotification(this.endPoint.getX());
        this.setHeightWithoutNotification(this.endPoint.getY());
        this.notifyAllListeners(new PropertyChangeEvent(this, "Angle", oldAngle, newAngleRadians));
        this.notifyAllListeners(new PropertyChangeEvent(this, "Width", oldWidth, this.getWidth()));
        this.notifyAllListeners(new PropertyChangeEvent(this, "Height", oldHeight, this.getHeight()));
    }

    @Override
    public void rotate(int units) {
        this.setAngle(this.angleRadians + Math.toRadians(units));
    }

    @Override
    public void move(int moveX, int moveY) {
        this.setX(this.getX() + moveX);
        this.setY(this.getY() + moveY);
    }
    
    @Override
    public void scale(double scaleMultiplier) {
        this.setRadius(this.getRadius() * scaleMultiplier);
    }

    @Override
    protected void widthChanged(int oldWidth, int newWidth) {
        double oldRadius = this.getRadius();
        double oldAngle = this.getAngle();
        this.endPoint.setX(newWidth);
        this.angleRadians = this.endPoint.getAngle();
        this.notifyAllListeners(new PropertyChangeEvent(this, "Radius", oldRadius, this.getRadius()));
        this.notifyAllListeners(new PropertyChangeEvent(this, "Angle", oldAngle, this.angleRadians));
    }

    @Override
    protected void heightChanged(int oldHeight, int newHeight) {
        double oldRadius = this.getRadius();
        double oldAngle = this.getAngle();
        this.endPoint.setY(newHeight);
        this.angleRadians = this.endPoint.getAngle();
        this.notifyAllListeners(new PropertyChangeEvent(this, "Radius", oldRadius, this.getRadius()));
        this.notifyAllListeners(new PropertyChangeEvent(this, "Angle", oldAngle, this.angleRadians));
    }
}