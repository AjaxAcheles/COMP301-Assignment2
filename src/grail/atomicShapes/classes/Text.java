package grail.atomicShapes.classes;

import java.beans.PropertyChangeEvent;

import grail.atomicShapes.interfaces.TextInterface;
import grail.shapes.classes.Locatable;
import util.annotations.EditablePropertyNames;
import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;

@StructurePattern(StructurePatternNames.STRING_PATTERN)
@PropertyNames({"X", "Y", "Text", "PropertyChangeListeners"})
@EditablePropertyNames({"X", "Y", "Text"})
public class Text extends Locatable implements TextInterface {
    
    private String text;

    public Text(String initText, int initX, int initY) {
        super(initX, initY);
        this.text = initText;
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
}
