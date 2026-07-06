package grail.atomicShapes.interfaces;

import tags301.Comp301Tags;
import util.annotations.EditablePropertyNames;
import util.annotations.Explanation;
import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;
import util.annotations.Tags;

@Tags(Comp301Tags.LOCATABLE)
@StructurePattern(StructurePatternNames.POINT_PATTERN)
@Explanation("Location in Java coordinate System.")
@PropertyNames({"X", "Y", "Angle", "Radius", "PropertyChangeListeners"})
@EditablePropertyNames({"X", "Y", "Angle", "Radius"})
public interface PointInterface extends LocatableInterface {
	double getAngle();
	double getRadius();
	void setRadius(double radius);
	void setAngle(double angle);
}
