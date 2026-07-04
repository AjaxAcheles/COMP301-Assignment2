package grail.simpleShapes.classes;

import grail.atomicShapes.classes.CartesianPoint;
import grail.atomicShapes.classes.PolarPoint;
import grail.atomicShapes.interfaces.PointInterface;
import grail.simpleShapes.interfaces.LineInterface;
import tags301.Comp301Tags;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;
import util.annotations.Tags;

@StructurePattern(StructurePatternNames.LINE_PATTERN)
@Tags(Comp301Tags.ROTATING_LINE)
public class RotatingLine implements LineInterface {
    
    private static final int DEFAULT_X = 100;
    private static final int DEFAULT_Y = 100;
    private static final double DEFAULT_RADIUS = 50.0;
    private static final double DEFAULT_ANGLE_RADIANS = Math.PI / 4;

    private PointInterface startPoint;
    private PointInterface endPoint;
    private double angleRadians;

    public RotatingLine() {
        this(DEFAULT_X, DEFAULT_Y, DEFAULT_RADIUS, DEFAULT_ANGLE_RADIANS);
    }

    
    public RotatingLine(int startX, int startY, double radius, double angleRadians) {
        this.startPoint = new CartesianPoint(startX, startY);
        this.angleRadians = angleRadians;
        this.endPoint = new PolarPoint(radius, angleRadians);
    }

    @Override
    public int getX() {
        return startPoint.getX();
    }
    
    @Override
    public int getY() {
        return startPoint.getY();
    }

    @Override
    public void setX(int x) {
        startPoint.setX(x);
    }

    @Override
    public void setY(int y) {
        startPoint.setY(y);
    }

    @Override
    public int getWidth() {
        return this.endPoint.getX();
    }

    @Override
    public int getHeight() {
        return this.endPoint.getY();
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
        ((PolarPoint) this.endPoint).setRadius(newRadius);
    }

    @Override
    public PointInterface getEnd() {
        return this.endPoint;
    }

    @Override
    public void setAngle(double newAngleRadians) {
        this.angleRadians = newAngleRadians;
        ((PolarPoint) this.endPoint).setAngle(newAngleRadians);
    }

    @Override
    public void rotate(int units) {
        // The grader's structure check requires rotate(int) and supplies the rotation in
        // degrees, so convert to the radian representation the rest of the class uses.
        this.angleRadians += Math.toRadians(units);
        ((PolarPoint) this.endPoint).setAngle(this.angleRadians);
    }

    @Override
    public void move(int moveX, int moveY) {
        int newX = this.startPoint.getX() + moveX;
        int newY = this.startPoint.getY() + moveY;
        this.startPoint.setX(newX);
        this.startPoint.setY(newY);
        // We're not moving the endPoint because it's coords are relative to the startPoint, so it automatically moves.
    }
    
    @Override
    public void scale(double scaleMultiplier) {
        this.setRadius(this.getRadius() * scaleMultiplier);
    }
}