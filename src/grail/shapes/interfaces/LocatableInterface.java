package grail.shapes.interfaces;

import java.beans.PropertyChangeListener;
import java.util.List;

import util.annotations.Visible;
import util.annotations.EditablePropertyNames;
import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;
import util.models.PropertyListenerRegisterer;

@StructurePattern(StructurePatternNames.BEAN_PATTERN)
@PropertyNames({"X", "Y"})
@EditablePropertyNames({"X", "Y"})
public interface LocatableInterface extends PropertyListenerRegisterer {
    int getX();
    void setX(int newX);
    int getY();
    void setY(int newY);
    @Visible(false)
    List<PropertyChangeListener> getPropertyChangeListeners();
}
