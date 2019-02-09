/**
 * Name: Abdul Moiz Munir Sohu
 * Recitation no.: 216
 * PennKey: sohu
 * Implementation: This is the Disc class, for MainGame.java, and it helps 
 * us create and manipulate discs in the game of connect 4. 
 */

//We import the color class to later use it & the Color object for our discs
import java.awt.Color;

//The constructor for the Disc class
public class Disc {
    private int xPosition, yPosition;
    private Color color;
    private final double SIZE = 0.3; //size is radius of our discs & wont change
    
    public Disc(int x, int y, Color c) {
        this.xPosition = x;
        this.yPosition = y;
        this.color = c;
    }
    
    //this uses PennDraw to create discs
    public void drawDisc() {
        PennDraw.setPenColor(color);
        PennDraw.filledCircle(xPosition, yPosition, SIZE);
    }
    
    //getter function to get size
    public double getSize() {
        return SIZE;
    }
    
    
}