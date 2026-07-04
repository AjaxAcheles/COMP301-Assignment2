package grail.shapes.classes;

import java.beans.PropertyChangeEvent;

import grail.shapes.interfaces.BoundedShapeInterface;
import tags301.Comp301Tags;
import util.annotations.EditablePropertyNames;
import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;
import util.annotations.Tags;

@Tags(Comp301Tags.BOUNDED_SHAPE)
@StructurePattern(StructurePatternNames.BEAN_PATTERN)
@PropertyNames({"X", "Y", "Width", "Height", "PropertyChangeListeners"})
@EditablePropertyNames({"X", "Y", "Width", "Height"})
public class BoundedShape extends Locatable implements BoundedShapeInterface {
    private int width;
    private int height;

    public BoundedShape(int x, int y, int width, int height) {
        super(x, y);
        this.width = width;
        this.height = height;
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public void setWidth(int newWidth) {
        int oldWidth = this.width;
        this.width = newWidth;
        this.notifyAllListeners(new PropertyChangeEvent(this, "Width", oldWidth, newWidth));
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public void setHeight(int newHeight) {
        int oldHeight = this.height;
        this.height = newHeight;
        this.notifyAllListeners(new PropertyChangeEvent(this, "Height", oldHeight, newHeight));
    }
}
