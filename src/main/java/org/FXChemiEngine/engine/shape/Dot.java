package org.FXChemiEngine.engine.shape;

import javafx.scene.paint.Color;
import org.FXChemiEngine.engine.Shape;
import org.FXChemiEngine.math.UnityMath.Vector2;

/**Class Dot*/
public class Dot extends Shape {
    /**Dot constructor
     * ini vertices, ini center, ini position, ini size*/
    public Dot(Vector2 pos, Color c){
        super(c);//AbstractShape constructor
        this.vertices.add(new Vector2(0, 0));//ini vertices
        this.center = new Vector2(0, 0);//ini center
        this.position = new Vector2(pos);//ini position 0,0
    }
    /**Method for paint*/
    @Override
    public void paint() {
        Vector2 tmp = getVertices(this.vertices.get(0));//get vertices point in screen dimension
        if(tmp != null)
             fillDot((int)tmp.x, (int)tmp.y);//paint dot
    }
}
