package grail.atomicShapes.classes;

import grail.atomicShapes.interfaces.TextInterface;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;

@StructurePattern(StructurePatternNames.STRING_PATTERN)
public class Text implements TextInterface {
    
    private String text;
    private int x;
    private int y;

    public Text(String initText, int initX, int initY) {
        this.text = initText;
        this.x = initX;
        this.y = initY;
    }
    
    @Override
    public int getX() {
        return x;
    }
    
    @Override
    public void setX(int newX) {
        this.x = newX;
    }
    
    @Override
    public int getY() {
        return y;
    }
    
    @Override
    public void setY(int newY) {
        this.y = newY;
    }
    
    @Override
    public String getText() {
        return text;
    }
    
    @Override
    public void setText(String newVal) {
        this.text = newVal;
    }
}
