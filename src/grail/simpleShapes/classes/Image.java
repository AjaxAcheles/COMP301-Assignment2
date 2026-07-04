package grail.simpleShapes.classes;

import grail.simpleShapes.interfaces.ImageInterface;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;

@StructurePattern(StructurePatternNames.IMAGE_PATTERN)
public class Image implements ImageInterface {
    
    private int x;
    private int y;
    private int width;
    private int height;
    private String text;
    private String imageFileName;
    
    public Image(int x, int y, int width, int height, String text, String imageFileName) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
        this.imageFileName = imageFileName;
    }
    
    @Override
    public int getX() {
        return x;
    }
    
    @Override
    public void setX(int newX) {
        x = newX;
    }
    
    @Override
    public int getY() {
        return y;
    }
    
    @Override
    public void setY(int newY) {
        y = newY;
    }
    
    @Override
    public int getWidth() {
        return width;
    }
    
    @Override
    public void setWidth(int newVal) {
        width = newVal;
    }
    
    @Override
    public int getHeight() {
        return height;
    }
    
    @Override
    public void setHeight(int newVal) {
        height = newVal;
    }
    
    @Override
    public String getText() {
        return text;
    }
    
    @Override
    public void setText(String newVal) {
        this.text = newVal;
    }
    
    @Override
    public String getImageFileName() {
        return imageFileName;
    }
    
    @Override
    public void setImageFileName(String newVal) {
        this.imageFileName = newVal;
    }
}
