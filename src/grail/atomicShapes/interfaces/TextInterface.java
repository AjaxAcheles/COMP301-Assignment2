package grail.atomicShapes.interfaces;

import grail.shapes.interfaces.LocatableInterface;
import util.annotations.EditablePropertyNames;
import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;

@StructurePattern(StructurePatternNames.STRING_PATTERN)
@PropertyNames({"X", "Y", "Text", "PropertyChangeListeners"})
@EditablePropertyNames({"X", "Y", "Text"})
public interface TextInterface extends LocatableInterface {
    String getText();
    void setText(String newVal);
}
