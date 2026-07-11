package grail.simpleShapes.interfaces;
import grail.atomicShapes.interfaces.PointInterface;
import grail.compositeShapes.interfaces.BoundedShapeInterface;
import tags301.Comp301Tags;
import util.annotations.EditablePropertyNames;
import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;
import util.annotations.Tags;

@Tags(Comp301Tags.ROTATING_LINE)
@StructurePattern(StructurePatternNames.LINE_PATTERN)
@PropertyNames({"X", "Y", "Width", "Height", "Radius", "Angle", "End", "PropertyChangeListeners"})
@EditablePropertyNames({"X", "Y", "Width", "Height", "Radius", "Angle"})
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