package grail.shapes.interfaces;

import util.annotations.EditablePropertyNames;
import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;

@StructurePattern(StructurePatternNames.BEAN_PATTERN)
@PropertyNames({"X", "Y", "Width", "Height"})
@EditablePropertyNames({"X", "Y", "Width", "Height"})
public interface BoundedShapeInterface extends LocatableInterface {
    int getWidth();
    void setWidth(int newWidth);
    int getHeight();
    void setHeight(int newHeight);
}
