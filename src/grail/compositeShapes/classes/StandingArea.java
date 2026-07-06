package grail.compositeShapes.classes;

import grail.compositeShapes.interfaces.AvatarInterface;
import grail.compositeShapes.interfaces.StandingAreaInterface;
import grail.simpleShapes.classes.RotatingLine;
import grail.simpleShapes.interfaces.LineInterface;
import util.annotations.EditablePropertyNames;
import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;
import util.annotations.Visible;

@StructurePattern(StructurePatternNames.BEAN_PATTERN)
@PropertyNames({"TopLine", "RightLine", "BottomLine", "LeftLine"})
@EditablePropertyNames({})
public class StandingArea implements StandingAreaInterface {
    private static final double ZERO_ANGLE_RADIANS = 0;
    private static final double DOWN_DIRECTION_RADIANS = Math.PI / 2;
    private static final int CENTER_DIVISOR = 2;

    private int x;
    private int y;
    private int width;
    private int height;
    private LineInterface topLine;
    private LineInterface rightLine;
    private LineInterface bottomLine;
    private LineInterface leftLine;

    public StandingArea(int initialX, int initialY, int initialWidth, int initialHeight) {
        this.x = initialX;
        this.y = initialY;
        this.width = initialWidth;
        this.height = initialHeight;
        this.topLine = new RotatingLine(initialX, initialY, initialWidth, ZERO_ANGLE_RADIANS);
        this.rightLine = new RotatingLine(initialX + initialWidth, initialY, initialHeight, DOWN_DIRECTION_RADIANS);
        this.bottomLine = new RotatingLine(initialX, initialY + initialHeight, initialWidth, ZERO_ANGLE_RADIANS);
        this.leftLine = new RotatingLine(initialX, initialY, initialHeight, DOWN_DIRECTION_RADIANS);
    }

    @Override
    public LineInterface getTopLine() {
        return this.topLine;
    }

    @Override
    public LineInterface getRightLine() {
        return this.rightLine;
    }

    @Override
    public LineInterface getBottomLine() {
        return this.bottomLine;
    }

    @Override
    public LineInterface getLeftLine() {
        return this.leftLine;
    }

    @Override
    @Visible(false)
    public int getCenterX() {
        return this.x + this.width / CENTER_DIVISOR;
    }

    @Override
    @Visible(false)
    public int getCenterY() {
        return this.y + this.height / CENTER_DIVISOR;
    }

    @Override
    public boolean contains(AvatarInterface avatar) {
        int avatarX = avatar.getX();
        int avatarY = avatar.getY();
        return avatarX >= this.x
                && avatarX <= this.x + this.width
                && avatarY >= this.y
                && avatarY <= this.y + this.height;
    }
}
