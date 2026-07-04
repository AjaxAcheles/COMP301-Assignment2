package grail.atomicShapes.interfaces;

import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;

@StructurePattern(StructurePatternNames.STRING_PATTERN)
public interface TextInterface {
    int getX();
    void setX(int newX);
    int getY();
    void setY(int newY);
    String getText();
    void setText(String newVal);
}