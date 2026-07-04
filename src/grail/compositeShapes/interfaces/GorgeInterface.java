package grail.compositeShapes.interfaces;

import grail.simpleShapes.interfaces.LineInterface;
import util.annotations.EditablePropertyNames;
import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;

@StructurePattern(StructurePatternNames.BEAN_PATTERN)
@PropertyNames({"LeftWall", "RightWall", "BridgeTop", "BridgeBottom"})
@EditablePropertyNames({})
public interface GorgeInterface {
    LineInterface getLeftWall();
    LineInterface getRightWall();
    LineInterface getBridgeTop();
    LineInterface getBridgeBottom();
}
