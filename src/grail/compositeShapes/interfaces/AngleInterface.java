package grail.compositeShapes.interfaces;

import grail.simpleShapes.interfaces.LineInterface;
import tags301.Comp301Tags;
import util.annotations.EditablePropertyNames;
import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;
import util.annotations.Tags;

@Tags(Comp301Tags.ANGLE)
@StructurePattern(StructurePatternNames.BEAN_PATTERN)
@PropertyNames({"LeftLine", "RightLine"})
@EditablePropertyNames({})
public interface AngleInterface {
    LineInterface getLeftLine();
    LineInterface getRightLine();
    void move(int x, int y);
    void rotate(int angle);
}