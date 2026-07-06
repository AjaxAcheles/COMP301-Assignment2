package grail.atomicShapes.interfaces;

import util.annotations.EditablePropertyNames;
import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;

@StructurePattern(StructurePatternNames.STRING_PATTERN)
@PropertyNames({"Text", "PropertyChangeListeners"})
@EditablePropertyNames({"Text"})
public interface TextInterface extends LocatableInterface {
    String getText();
    void setText(String newVal);
}