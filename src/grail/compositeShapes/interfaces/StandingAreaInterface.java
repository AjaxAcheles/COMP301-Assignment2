package grail.compositeShapes.interfaces;

import grail.simpleShapes.interfaces.LineInterface;
import util.annotations.EditablePropertyNames;
import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;

@StructurePattern(StructurePatternNames.BEAN_PATTERN)
@PropertyNames({"TopLine", "RightLine", "BottomLine", "LeftLine"})
@EditablePropertyNames({})
public interface StandingAreaInterface {
    LineInterface getTopLine();
    LineInterface getRightLine();
    LineInterface getBottomLine();
    LineInterface getLeftLine();
}
