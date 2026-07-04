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

    private int x;
    private int y;
    private int width;
    private int height;
    private LineInterface topLine;
    private LineInterface rightLine;
    private LineInterface bottomLine;
    private LineInterface leftLine;

    public StandingArea(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.topLine = new RotatingLine(x, y, width, ZERO_ANGLE_RADIANS);
        this.rightLine = new RotatingLine(x + width, y, height, DOWN_DIRECTION_RADIANS);
        this.bottomLine = new RotatingLine(x, y + height, width, ZERO_ANGLE_RADIANS);
        this.leftLine = new RotatingLine(x, y, height, DOWN_DIRECTION_RADIANS);
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
        return this.x + this.width / 2;
    }

    @Override
    @Visible(false)
    public int getCenterY() {
        return this.y + this.height / 2;
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
