package grail.simpleShapes.classes;

import java.beans.PropertyChangeEvent;

import grail.simpleShapes.interfaces.ImageInterface;
import grail.shapes.classes.BoundedShape;
import tags301.Comp301Tags;
import util.annotations.EditablePropertyNames;
import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;
import util.annotations.Tags;

@Tags(Comp301Tags.BOUNDED_SHAPE)
@StructurePattern(StructurePatternNames.IMAGE_PATTERN)
@PropertyNames({"X", "Y", "Width", "Height", "Text", "ImageFileName", "PropertyChangeListeners"})
@EditablePropertyNames({"X", "Y", "Width", "Height", "Text", "ImageFileName"})
public class Image extends BoundedShape implements ImageInterface {
    
    private String text;
    private String imageFileName;
    
    public Image(int x, int y, int width, int height, String text, String imageFileName) {
        super(x, y, width, height);
        this.text = text;
        this.imageFileName = imageFileName;
    }

    @Override
    public String getText() {
        return text;
    }
    
    @Override
    public void setText(String newVal) {
        String oldText = this.text;
        this.text = newVal;
        this.notifyAllListeners(new PropertyChangeEvent(this, "Text", oldText, newVal));
    }
    
    @Override
    public String getImageFileName() {
        return imageFileName;
    }
    
    @Override
    public void setImageFileName(String newVal) {
        String oldImageFileName = this.imageFileName;
        this.imageFileName = newVal;
        this.notifyAllListeners(new PropertyChangeEvent(this, "ImageFileName", oldImageFileName, newVal));
    }
}
