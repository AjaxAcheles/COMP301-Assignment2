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
@PropertyNames({"X", "Y", "Width", "Height", "TopLine", "RightLine", "BottomLine", "LeftLine", "PropertyChangeListeners"})
@EditablePropertyNames({"X", "Y", "Width", "Height"})
public class StandingArea extends BoundedShape implements StandingAreaInterface {
    private static final double ZERO_ANGLE_RADIANS = 0;
    private static final double DOWN_DIRECTION_RADIANS = Math.PI / 2;
    private static final int CENTER_DIVISOR = 2;

    private final LineInterface topLine;
    private final LineInterface rightLine;
    private final LineInterface bottomLine;
    private final LineInterface leftLine;

    public StandingArea(int initialX, int initialY, int initialWidth, int initialHeight) {
        super(initialX, initialY, initialWidth, initialHeight);
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
        return this.getX() + this.getWidth() / CENTER_DIVISOR;
    }

    @Override
    @Visible(false)
    public int getCenterY() {
        return this.getY() + this.getHeight() / CENTER_DIVISOR;
    }

    @Override
    public boolean contains(AvatarInterface avatar) {
        int avatarX = avatar.getX();
        int avatarY = avatar.getY();
        return avatarX >= this.getX()
                && avatarX <= this.getX() + this.getWidth()
                && avatarY >= this.getY()
                && avatarY <= this.getY() + this.getHeight();
    }

    @Override
    protected void locationChanged(int changeInX, int changeInY) {
        this.topLine.move(changeInX, changeInY);
        this.rightLine.move(changeInX, changeInY);
        this.bottomLine.move(changeInX, changeInY);
        this.leftLine.move(changeInX, changeInY);
    }

    @Override
    protected void widthChanged(int oldWidth, int newWidth) {
        this.topLine.setWidth(newWidth);
        this.rightLine.setX(this.getX() + newWidth);
        this.bottomLine.setWidth(newWidth);
    }

    @Override
    protected void heightChanged(int oldHeight, int newHeight) {
        this.rightLine.setHeight(newHeight);
        this.bottomLine.setY(this.getY() + newHeight);
        this.leftLine.setHeight(newHeight);
    }
}
