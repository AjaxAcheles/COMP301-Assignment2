package grail.atomicShapes.interfaces;

import grail.shapes.interfaces.LocatableInterface;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;

@StructurePattern(StructurePatternNames.STRING_PATTERN)
public interface TextInterface extends LocatableInterface {
    String getText();
    void setText(String newVal);
}
