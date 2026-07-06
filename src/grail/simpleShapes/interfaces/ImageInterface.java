package grail.simpleShapes.interfaces;

import grail.atomicShapes.interfaces.BoundedShapeInterface;
import util.annotations.EditablePropertyNames;
import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;

@StructurePattern(StructurePatternNames.IMAGE_PATTERN)
@PropertyNames({"X", "Y", "Width", "Height", "Text", "ImageFileName", "PropertyChangeListeners"})
@EditablePropertyNames({"X", "Y", "Width", "Height", "Text", "ImageFileName"})
public interface ImageInterface extends BoundedShapeInterface {
    String getText();
    void setText(String newVal);
    String getImageFileName();
    void setImageFileName(String newVal);
}
