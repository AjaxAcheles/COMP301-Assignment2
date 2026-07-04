package grail.compositeShapes.classes;

import grail.compositeShapes.interfaces.GorgeInterface;
import grail.simpleShapes.classes.RotatingLine;
import grail.simpleShapes.interfaces.LineInterface;
import util.annotations.EditablePropertyNames;
import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;

@StructurePattern(StructurePatternNames.BEAN_PATTERN)
@PropertyNames({"LeftWall", "RightWall", "BridgeTop", "BridgeBottom"})
@EditablePropertyNames({})
public class Gorge implements GorgeInterface {
    private static final double ZERO_ANGLE_RADIANS = 0;
    private static final double DOWN_DIRECTION_RADIANS = Math.PI / 2;

    private LineInterface leftWall;
    private LineInterface rightWall;
    private LineInterface bridgeTop;
    private LineInterface bridgeBottom;

    public Gorge(int x, int y, int width, int height, int bridgeY, int bridgeHeight) {
        this.leftWall = new RotatingLine(x, y, height, DOWN_DIRECTION_RADIANS);
        this.rightWall = new RotatingLine(x + width, y, height, DOWN_DIRECTION_RADIANS);
        this.bridgeTop = new RotatingLine(x, bridgeY, width, ZERO_ANGLE_RADIANS);
        this.bridgeBottom = new RotatingLine(x, bridgeY + bridgeHeight, width, ZERO_ANGLE_RADIANS);
    }

    @Override
    public LineInterface getLeftWall() {
        return this.leftWall;
    }

    @Override
    public LineInterface getRightWall() {
        return this.rightWall;
    }

    @Override
    public LineInterface getBridgeTop() {
        return this.bridgeTop;
    }

    @Override
    public LineInterface getBridgeBottom() {
        return this.bridgeBottom;
    }
}
