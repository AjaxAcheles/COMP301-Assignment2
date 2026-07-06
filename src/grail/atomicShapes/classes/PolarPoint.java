package grail.atomicShapes.classes;

import util.annotations.EditablePropertyNames;
import util.annotations.Explanation;
import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;

@StructurePattern(StructurePatternNames.POINT_PATTERN)
@Explanation("Location in Java coordinate System.")
@PropertyNames({"X", "Y", "Angle", "Radius", "PropertyChangeListeners"})
@EditablePropertyNames({"X", "Y", "Angle", "Radius"})
public class PolarPoint extends CartesianPoint {

	public PolarPoint(double initialRadius, double initialAngleRadians) {
		super((int) Math.round(initialRadius * Math.cos(initialAngleRadians)),
		        (int) Math.round(initialRadius * Math.sin(initialAngleRadians)));
	}

	public PolarPoint(int initialX, int initialY) {
		super(initialX, initialY);
	}
}
