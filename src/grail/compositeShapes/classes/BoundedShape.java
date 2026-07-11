package grail.compositeShapes.classes;

import java.beans.PropertyChangeEvent;

import grail.atomicShapes.classes.Locatable;
import grail.compositeShapes.interfaces.BoundedShapeInterface;
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

    public BoundedShape(int initialX, int initialY, int initialWidth, int initialHeight) {
        super(initialX, initialY);
        this.width = initialWidth;
        this.height = initialHeight;
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public void setWidth(int newWidth) {
        int oldWidth = this.width;
        this.width = newWidth;
        this.widthChanged(oldWidth, newWidth);
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
        this.heightChanged(oldHeight, newHeight);
        this.notifyAllListeners(new PropertyChangeEvent(this, "Height", oldHeight, newHeight));
    }

    protected void widthChanged(int oldWidth, int newWidth) {
    }

    protected void heightChanged(int oldHeight, int newHeight) {
    }

    protected void setWidthWithoutNotification(int newWidth) {
        this.width = newWidth;
    }

    protected void setHeightWithoutNotification(int newHeight) {
        this.height = newHeight;
    }
}
