package grail.simpleShapes.interfaces;
import grail.atomicShapes.interfaces.PointInterface;
import tags301.Comp301Tags;
import util.annotations.EditablePropertyNames;
import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;
import util.annotations.Tags;

@Tags(Comp301Tags.ROTATING_LINE)
@StructurePattern(StructurePatternNames.LINE_PATTERN)
@PropertyNames({"X", "Y", "Width", "Height", "Radius", "Angle", "End"})
@EditablePropertyNames({"X", "Y", "Radius", "Angle"})
public interface LineInterface {
    int getWidth();
    int getHeight();
    
    int getX();
    int getY();
    void setX(int newX);
    void setY(int newY);
    
    double getRadius();
    double getAngle();
    void setRadius(double radius);
    void setAngle(double angle);

    PointInterface getEnd();
    
    void rotate(int units);
    void move(int moveX, int moveY);
    void scale(double scaleMultiplier);
}