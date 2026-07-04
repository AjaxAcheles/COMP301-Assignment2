package grail.simpleShapes.interfaces;

import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;

@StructurePattern(StructurePatternNames.IMAGE_PATTERN)
public interface ImageInterface {
    int getX();
    void setX(int newX);
    int getY();
    void setY(int newY);
    int getWidth();
    void setWidth(int newVal);
    int getHeight();
    void setHeight(int newVal);
    String getText();
    void setText(String newVal);
    String getImageFileName();
    void setImageFileName(String newVal);
}