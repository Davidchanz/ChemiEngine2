package com.FXChemiEngine.engine.shape;

import com.FXChemiEngine.engine.Shape;
import com.FXChemiEngine.math.UnityMath.Vector2;
import com.FXChemiEngine.util.Color;

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
