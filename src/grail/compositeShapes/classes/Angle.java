package grail.compositeShapes.classes;

import grail.compositeShapes.interfaces.AngleInterface;
import grail.simpleShapes.classes.RotatingLine;
import grail.simpleShapes.interfaces.LineInterface;
import tags301.Comp301Tags;
import util.annotations.EditablePropertyNames;
import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;
import util.annotations.Tags;

@Tags(Comp301Tags.ANGLE)
@StructurePattern(StructurePatternNames.BEAN_PATTERN)
@PropertyNames({"LeftLine", "RightLine"})
@EditablePropertyNames({})
public class Angle implements AngleInterface {
    private static final int DEFAULT_X = 0;
    private static final int DEFAULT_Y = 0;
    private static final double DEFAULT_RADIUS = 50;
    private static final double DEFAULT_SPLIT_ANGLE_RADIANS = Math.PI / 2;
    private static final double DEFAULT_DOWN_DIRECTION_RADIANS = Math.PI / 2;

    private LineInterface leftLine;
    private LineInterface rightLine;
    private double radius;
    private double splitAngleRadians;
    private double downDirectionRadians;

    public Angle() {
        this(DEFAULT_X, DEFAULT_Y, DEFAULT_RADIUS, DEFAULT_SPLIT_ANGLE_RADIANS, DEFAULT_DOWN_DIRECTION_RADIANS);
    }

    public Angle(int x, int y, double radius, double splitAngleRadians, double downDirectionRadians) {
        this.radius = radius;
        this.splitAngleRadians = splitAngleRadians;
        this.downDirectionRadians = downDirectionRadians;

        // downDirection determines what is "down" and the left and right lines are positioned splitAngle/2 to the left and right of the downDirection
        this.leftLine = new RotatingLine(x, y, radius, downDirectionRadians - splitAngleRadians / 2);
        this.rightLine = new RotatingLine(x, y, radius, downDirectionRadians + splitAngleRadians / 2);
    }

    @Override
    public LineInterface getLeftLine() {
        return this.leftLine;
    }

    @Override
    public LineInterface getRightLine() {
        return this.rightLine;
    }

    @Override
    public void move(int x, int y) {
        this.leftLine.move(x, y);
        this.rightLine.move(x, y);
    }

    @Override
    public void rotate(int angle) {
        this.leftLine.rotate(angle);
        this.rightLine.rotate(angle);
    }
}