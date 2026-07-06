package grail.compositeShapes.classes;

import grail.atomicShapes.classes.Locatable;
import grail.atomicShapes.classes.Text;
import grail.atomicShapes.interfaces.TextInterface;
import grail.compositeShapes.interfaces.AngleInterface;
import grail.compositeShapes.interfaces.AvatarInterface;
import grail.simpleShapes.classes.Image;
import grail.simpleShapes.classes.RotatingLine;
import grail.simpleShapes.interfaces.ImageInterface;
import grail.simpleShapes.interfaces.LineInterface;
import main.Factory;
import tags301.Comp301Tags;
import util.annotations.EditablePropertyNames;
import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;
import util.annotations.Tags;

@Tags(Comp301Tags.AVATAR)
@StructurePattern(StructurePatternNames.BEAN_PATTERN)
@PropertyNames({"X", "Y", "SpeechBubble", "StringShape", "Head", "Arms", "Body", "Legs", "PropertyChangeListeners"})
@EditablePropertyNames({"X", "Y"})
public class Avatar extends Locatable implements AvatarInterface {
    
    private static final int HEAD_WIDTH = 30;
    private static final int HEAD_HEIGHT = 30;
    private static final int BODY_LENGTH = 68;
    private static final int LEG_RADIUS = 75;
    private static final int ARM_RADIUS = 60;
    private static final double LEG_SPLIT_ANGLE_RADIANS = Math.PI / 3;
    private static final double ARM_SPLIT_ANGLE_RADIANS = Math.PI / 2;
    private static final double DOWN_DIRECTION_RADIANS = Math.PI / 2;
    private static final double SIZE_DIVISOR = 2.0;
    
    private final TextInterface speechBubble;
    private final ImageInterface head;
    private final AngleInterface arms;
    private final LineInterface body;
    private final AngleInterface legs;

    public Avatar(int initialX, int initialY, String speech, String headImage) {
        super(initialX, initialY);

        this.legs = Factory.legsFactoryMethod(initialX, initialY, LEG_RADIUS, LEG_SPLIT_ANGLE_RADIANS,
                DOWN_DIRECTION_RADIANS);

        double bodyAngleRadians = -DOWN_DIRECTION_RADIANS;
        this.body = new RotatingLine(initialX, initialY, BODY_LENGTH, bodyAngleRadians);

        int neckX = this.body.getX() + this.body.getEnd().getX();
        int neckY = this.body.getY() + this.body.getEnd().getY();

        this.arms = new Angle(neckX, neckY, ARM_RADIUS, ARM_SPLIT_ANGLE_RADIANS, DOWN_DIRECTION_RADIANS);

        this.head = new Image(neckX - (int) (HEAD_WIDTH / SIZE_DIVISOR), neckY - HEAD_HEIGHT,
                              HEAD_WIDTH, HEAD_HEIGHT, "", headImage);

        this.speechBubble = new Text(speech, this.getX() + HEAD_WIDTH, neckY - HEAD_HEIGHT);
    }
    
    @Override
    public TextInterface getSpeechBubble() {
        return this.speechBubble;
    }

    @Override
    public ImageInterface getHead() {
        return this.head;
    }

    @Override
    public AngleInterface getArms() {
        return this.arms;
    }

    @Override
    public LineInterface getBody() {
        return this.body;
    }

    @Override
    public AngleInterface getLegs() {
        return this.legs;
    }

    @Override
    public TextInterface getStringShape() {
        return this.speechBubble;
    }

    @Override
    public void scale(double scaleMultiplier) {
        this.body.scale(scaleMultiplier);

        this.legs.getLeftLine().setRadius(this.legs.getLeftLine().getRadius() * scaleMultiplier);
        this.legs.getRightLine().setRadius(this.legs.getRightLine().getRadius() * scaleMultiplier);
        this.arms.getLeftLine().setRadius(this.arms.getLeftLine().getRadius() * scaleMultiplier);
        this.arms.getRightLine().setRadius(this.arms.getRightLine().getRadius() * scaleMultiplier);

        this.head.setWidth((int) Math.round(this.head.getWidth() * scaleMultiplier));
        this.head.setHeight((int) Math.round(this.head.getHeight() * scaleMultiplier));
    }

    @Override
    public void move(int moveX, int moveY) {
        this.setX(this.getX() + moveX);
        this.setY(this.getY() + moveY);
    }

    @Override
    protected void locationChanged(int changeInX, int changeInY) {
        this.legs.move(changeInX, changeInY);
        this.body.move(changeInX, changeInY);
        this.arms.move(changeInX, changeInY);
        this.head.setX(this.head.getX() + changeInX);
        this.head.setY(this.head.getY() + changeInY);
        this.speechBubble.setX(this.speechBubble.getX() + changeInX);
        this.speechBubble.setY(this.speechBubble.getY() + changeInY);
    }

    @Override
    public void rotate(double degrees) {
        int rotationUnits = (int) Math.round(degrees);

        this.legs.rotate(rotationUnits);
        this.body.rotate(rotationUnits);

        int neckX = this.body.getX() + this.body.getEnd().getX();
        int neckY = this.body.getY() + this.body.getEnd().getY();

        int armMoveX = neckX - this.arms.getLeftLine().getX();
        int armMoveY = neckY - this.arms.getLeftLine().getY();
        this.arms.move(armMoveX, armMoveY);
        this.arms.rotate(rotationUnits);

        double bodyAngleRadians = this.body.getAngle();
        int headCenterX = neckX + (int) Math.round((this.head.getHeight() / SIZE_DIVISOR) * Math.cos(bodyAngleRadians));
        int headCenterY = neckY + (int) Math.round((this.head.getHeight() / SIZE_DIVISOR) * Math.sin(bodyAngleRadians));
        this.head.setX(headCenterX - (int) (this.head.getWidth() / SIZE_DIVISOR));
        this.head.setY(headCenterY - (int) (this.head.getHeight() / SIZE_DIVISOR));

        this.speechBubble.setX(this.head.getX() + this.head.getWidth());
        this.speechBubble.setY(this.head.getY());
    }
}
