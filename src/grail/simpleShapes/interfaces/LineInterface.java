package grail.simpleShapes.interfaces;
import grail.atomicShapes.interfaces.BoundedShapeInterface;
import grail.atomicShapes.interfaces.PointInterface;
import tags301.Comp301Tags;
import util.annotations.EditablePropertyNames;
import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;
import util.annotations.Tags;

@Tags(Comp301Tags.ROTATING_LINE)
@StructurePattern(StructurePatternNames.LINE_PATTERN)
@PropertyNames({"Radius", "Angle", "End", "PropertyChangeListeners"})
@EditablePropertyNames({"Radius", "Angle"})
public interface LineInterface extends BoundedShapeInterface {
    double getRadius();
    double getAngle();
    void setRadius(double radius);
    void setAngle(double angle);

    PointInterface getEnd();
    
    void rotate(int units);
    void move(int moveX, int moveY);
    void scale(double scaleMultiplier);
}