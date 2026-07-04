package grail.compositeShapes.classes;

import grail.atomicShapes.classes.Text;
import grail.atomicShapes.interfaces.TextInterface;
import grail.compositeShapes.interfaces.AngleInterface;
import grail.compositeShapes.interfaces.AvatarInterface;
import grail.simpleShapes.classes.Image;
import grail.simpleShapes.classes.RotatingLine;
import grail.simpleShapes.interfaces.ImageInterface;
import grail.simpleShapes.interfaces.LineInterface;
import tags301.Comp301Tags;
import util.annotations.EditablePropertyNames;
import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;
import util.annotations.Tags;

@Tags(Comp301Tags.AVATAR)
@StructurePattern(StructurePatternNames.BEAN_PATTERN)
@PropertyNames({"X", "Y", "SpeechBubble", "StringShape", "Head", "Arms", "Body", "Legs"})
@EditablePropertyNames({"X", "Y"})
public class Avatar implements AvatarInterface {
    
    private static final int HEAD_WIDTH = 30;
    private static final int HEAD_HEIGHT = 30;
    private static final int BODY_LENGTH = 68;
    private static final int LEG_RADIUS = 75;
    private static final int ARM_RADIUS = 60;
    private static final double LEG_SPLIT_ANGLE_RADIANS = Math.PI / 3;
    private static final double ARM_SPLIT_ANGLE_RADIANS = Math.PI / 2;
    private static final double DOWN_DIRECTION_RADIANS = Math.PI / 2;
    
    private int x;
    private int y;
    private TextInterface speechBubble;
    private ImageInterface head;
    private AngleInterface arms;
    private LineInterface body;
    private AngleInterface legs;

    public Avatar(int x, int y, String speech, String headImage) {
        this.x = x;
        this.y = y;

        // Legs spawn at (x, y) pointing downward
        this.legs = new Angle(x, y, LEG_RADIUS, LEG_SPLIT_ANGLE_RADIANS, DOWN_DIRECTION_RADIANS);

        // Body spawns at (x, y) and points upward (opposite of down direction)
        double bodyAngleRadians = -DOWN_DIRECTION_RADIANS;
        this.body = new RotatingLine(x, y, BODY_LENGTH, bodyAngleRadians);

        // Neck position is body's endpoint (absolute = start + offset)
        int neckX = this.body.getX() + this.body.getEnd().getX();
        int neckY = this.body.getY() + this.body.getEnd().getY();

        // Arms attach at the neck, dangling downward
        this.arms = new Angle(neckX, neckY, ARM_RADIUS, ARM_SPLIT_ANGLE_RADIANS, DOWN_DIRECTION_RADIANS);

        // Head sits directly above the body endpoint
        this.head = new Image(neckX - HEAD_WIDTH / 2, neckY - HEAD_HEIGHT,
                              HEAD_WIDTH, HEAD_HEIGHT, "", headImage);

        // Speech bubble positioned above the head
        this.speechBubble = new Text(speech, this.x + HEAD_WIDTH, neckY - HEAD_HEIGHT);
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public void setX(int newX) {
        this.x = newX;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public void setY(int newY) {
        this.y = newY;
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
        // Scale the body which represents the torso length
        this.body.scale(scaleMultiplier);

        // Scale legs and arms by updating their line radii
        this.legs.getLeftLine().setRadius(this.legs.getLeftLine().getRadius() * scaleMultiplier);
        this.legs.getRightLine().setRadius(this.legs.getRightLine().getRadius() * scaleMultiplier);
        this.arms.getLeftLine().setRadius(this.arms.getLeftLine().getRadius() * scaleMultiplier);
        this.arms.getRightLine().setRadius(this.arms.getRightLine().getRadius() * scaleMultiplier);

        // Scale head dimensions
        this.head.setWidth((int) Math.round(this.head.getWidth() * scaleMultiplier));
        this.head.setHeight((int) Math.round(this.head.getHeight() * scaleMultiplier));
    }

    @Override
    public void move(int moveX, int moveY) {
        this.x += moveX;
        this.y += moveY;

        this.legs.move(moveX, moveY);
        this.body.move(moveX, moveY);
        this.arms.move(moveX, moveY);
        this.head.setX(this.head.getX() + moveX);
        this.head.setY(this.head.getY() + moveY);
        this.speechBubble.setX(this.speechBubble.getX() + moveX);
        this.speechBubble.setY(this.speechBubble.getY() + moveY);
    }

    @Override
    public void rotate(double degrees) {
        int rotationUnits = (int) Math.round(degrees);

        this.legs.rotate(rotationUnits);
        this.body.rotate(rotationUnits);

        // Neck remains the body endpoint after body rotation
        int neckX = this.body.getX() + this.body.getEnd().getX();
        int neckY = this.body.getY() + this.body.getEnd().getY();

        // Move arm pivot to neck before rotating arms
        int armMoveX = neckX - this.arms.getLeftLine().getX();
        int armMoveY = neckY - this.arms.getLeftLine().getY();
        this.arms.move(armMoveX, armMoveY);
        this.arms.rotate(rotationUnits);

        // Position head along the current body direction so it turns with the avatar
        double bodyAngleRadians = this.body.getAngle();
        int headCenterX = neckX + (int) Math.round((this.head.getHeight() / 2.0) * Math.cos(bodyAngleRadians));
        int headCenterY = neckY + (int) Math.round((this.head.getHeight() / 2.0) * Math.sin(bodyAngleRadians));
        this.head.setX(headCenterX - this.head.getWidth() / 2);
        this.head.setY(headCenterY - this.head.getHeight() / 2);

        // Keep speech bubble tied to the head region
        this.speechBubble.setX(this.head.getX() + this.head.getWidth());
        this.speechBubble.setY(this.head.getY());
    }
}