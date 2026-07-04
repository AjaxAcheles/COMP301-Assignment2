package grail.simpleShapes.interfaces;

import grail.shapes.interfaces.BoundedShapeInterface;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;

@StructurePattern(StructurePatternNames.IMAGE_PATTERN)
public interface ImageInterface extends BoundedShapeInterface {
    String getText();
    void setText(String newVal);
    String getImageFileName();
    void setImageFileName(String newVal);
}
