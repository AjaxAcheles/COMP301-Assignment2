package grail.compositeShapes.interfaces;

import grail.atomicShapes.interfaces.TextInterface;
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
@PropertyNames({"X", "Y", "SpeechBubble", "Head", "Arms", "Body", "Legs"})
@EditablePropertyNames({"X", "Y"})
public interface AvatarInterface {
    int getX();
    void setX(int newX);
    int getY();
    void setY(int newY);
    TextInterface getSpeechBubble();
    TextInterface getStringShape();
    ImageInterface getHead();
    AngleInterface getArms();
    LineInterface getBody();
    AngleInterface getLegs();
    void move(int moveX, int moveY);
    void rotate(double degrees);
    void scale(double scaleMultiplier);
}