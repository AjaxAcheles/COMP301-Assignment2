package grail.compositeShapes.interfaces;

import grail.atomicShapes.interfaces.LocatableInterface;
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
@PropertyNames({"X", "Y", "SpeechBubble", "StringShape", "Head", "Arms", "Body", "Legs", "PropertyChangeListeners"})
@EditablePropertyNames({"X", "Y"})
public interface AvatarInterface extends LocatableInterface {
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
