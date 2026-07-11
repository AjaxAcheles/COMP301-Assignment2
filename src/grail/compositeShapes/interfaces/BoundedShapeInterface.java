package grail.compositeShapes.interfaces;

import grail.atomicShapes.interfaces.LocatableInterface;
import tags301.Comp301Tags;
import util.annotations.EditablePropertyNames;
import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;
import util.annotations.Tags;

@Tags(Comp301Tags.BOUNDED_SHAPE)
@StructurePattern(StructurePatternNames.BEAN_PATTERN)
@PropertyNames({"X", "Y", "Width", "Height", "PropertyChangeListeners"})
@EditablePropertyNames({"X", "Y", "Width", "Height"})
public interface BoundedShapeInterface extends LocatableInterface {
    int getWidth();
    void setWidth(int newWidth);
    int getHeight();
    void setHeight(int newHeight);
}
