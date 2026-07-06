package grail.atomicShapes.interfaces;

import java.beans.PropertyChangeListener;
import java.util.List;

import tags301.Comp301Tags;
import util.annotations.EditablePropertyNames;
import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;
import util.annotations.Tags;
import util.annotations.Visible;
import util.models.PropertyListenerRegisterer;

@Tags(Comp301Tags.LOCATABLE)
@StructurePattern(StructurePatternNames.BEAN_PATTERN)
@PropertyNames({"X", "Y", "PropertyChangeListeners"})
@EditablePropertyNames({"X", "Y"})
public interface LocatableInterface extends PropertyListenerRegisterer {
    int getX();
    void setX(int newX);
    int getY();
    void setY(int newY);
    @Visible(false)
    List<PropertyChangeListener> getPropertyChangeListeners();
}
