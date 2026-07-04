package grail.atomicShapes.interfaces;
import util.annotations.Explanation;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;
@StructurePattern(StructurePatternNames.POINT_PATTERN)
@Explanation("Location in Java coordinate System.")
public interface PointInterface {
	public int getX(); 
	public int getY(); 	
	public double getAngle(); 
	public double getRadius(); 
	void setX(int x);
	void setY(int y);
	void setRadius(double radius);
	void setAngle(double angle);

}
