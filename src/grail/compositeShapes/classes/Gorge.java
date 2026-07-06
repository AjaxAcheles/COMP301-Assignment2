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

    private final LineInterface leftWall;
    private final LineInterface rightWall;
    private final LineInterface bridgeTop;
    private final LineInterface bridgeBottom;

    public Gorge(int initialX, int initialY, int width, int height, int bridgeY, int bridgeHeight) {
        this.leftWall = new RotatingLine(initialX, initialY, height, DOWN_DIRECTION_RADIANS);
        this.rightWall = new RotatingLine(initialX + width, initialY, height, DOWN_DIRECTION_RADIANS);
        this.bridgeTop = new RotatingLine(initialX, bridgeY, width, ZERO_ANGLE_RADIANS);
        this.bridgeBottom = new RotatingLine(initialX, bridgeY + bridgeHeight, width, ZERO_ANGLE_RADIANS);
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
