package grail.atomicShapes.interfaces;
import java.beans.PropertyChangeListener;
import java.util.List;

import util.annotations.EditablePropertyNames;
import util.annotations.Explanation;
import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;
import util.annotations.Visible;
import util.models.PropertyListenerRegisterer;
@StructurePattern(StructurePatternNames.POINT_PATTERN)
@Explanation("Location in Java coordinate System.")
@PropertyNames({"X", "Y", "Angle", "Radius", "PropertyChangeListeners"})
@EditablePropertyNames({"X", "Y", "Angle", "Radius"})
public interface PointInterface extends PropertyListenerRegisterer {
	public int getX(); 
	public int getY(); 	
	public double getAngle(); 
	public double getRadius(); 
	void setX(int x);
	void setY(int y);
	void setRadius(double radius);
	void setAngle(double angle);
	@Visible(false)
	List<PropertyChangeListener> getPropertyChangeListeners();

}
