package grail.compositeShapes.classes;

import grail.compositeShapes.interfaces.AvatarInterface;
import grail.compositeShapes.interfaces.StandingAreaInterface;
import grail.simpleShapes.classes.RotatingLine;
import grail.simpleShapes.interfaces.LineInterface;
import util.annotations.EditablePropertyNames;
import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;

@StructurePattern(StructurePatternNames.BEAN_PATTERN)
@PropertyNames({"TopLine", "RightLine", "BottomLine", "LeftLine"})
@EditablePropertyNames({})
public class StandingArea implements StandingAreaInterface {
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
        this.topLine = new RotatingLine(x, y, width, 0);
        this.rightLine = new RotatingLine(x + width, y, height, Math.PI / 2);
        this.bottomLine = new RotatingLine(x, y + height, width, 0);
        this.leftLine = new RotatingLine(x, y, height, Math.PI / 2);
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

    public int getCenterX() {
        return this.x + this.width / 2;
    }

    public int getCenterY() {
        return this.y + this.height / 2;
    }

    public boolean contains(AvatarInterface avatar) {
        int avatarX = avatar.getX();
        int avatarY = avatar.getY();
        return avatarX >= this.x
                && avatarX <= this.x + this.width
                && avatarY >= this.y
                && avatarY <= this.y + this.height;
    }
}
