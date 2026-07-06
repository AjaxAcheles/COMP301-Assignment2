package grail.atomicShapes.classes;

import java.beans.PropertyChangeEvent;

import grail.atomicShapes.interfaces.TextInterface;
import util.annotations.EditablePropertyNames;
import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;

@StructurePattern(StructurePatternNames.STRING_PATTERN)
@PropertyNames({"X", "Y", "Text", "PropertyChangeListeners"})
@EditablePropertyNames({"X", "Y", "Text"})
public class Text extends Locatable implements TextInterface {
    
    private String text;

    public Text(String initialText, int initialX, int initialY) {
        super(initialX, initialY);
        this.text = initialText;
    }
    
    @Override
    public String getText() {
        return text;
    }
    
    @Override
    public void setText(String newText) {
        String oldText = this.text;
        this.text = newText;
        this.notifyAllListeners(new PropertyChangeEvent(this, "Text", oldText, newText));
    }
}