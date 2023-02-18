package com.FXChemiEngine.engine.shape;

import com.FXChemiEngine.engine.Resizing;
import com.FXChemiEngine.engine.Shape;
import com.FXChemiEngine.math.UnityMath.Vector2;
import com.FXChemiEngine.util.Color;

import java.util.ArrayList;

/**Class Line*/
public class Line extends Shape implements Resizing {//TODO works bad
    public float size;
    public enum TYPE{//Line type
        HORIZONTAL,
        VERTICAL,
        DIAGONAL,
        ADIAGONAL
    }
    public Vector2 start;//line start
    public Vector2 end;//line end
    /**Line constructor
     * for HOR, VERT, DIAG, ADIAG lines*/
    public Line(TYPE HV, int size, Vector2 pos, Color c){
        super(c);//AbstractShape constructor
        switch (HV){//Switch type of line
            case VERTICAL ->{
                this.start = new Vector2(0, -size);
                this.end = new Vector2(0, size);
            }
            case HORIZONTAL ->{
                this.start = new Vector2(-size, 0);
                this.end = new Vector2(size, 0);
            }
            case DIAGONAL ->{
                this.start = new Vector2(-size/2, -size/2);
                this.end = new Vector2(size/2, size/2);
            }
            case ADIAGONAL ->{
                this.start = new Vector2(-size/2, size/2);
                this.end = new Vector2(size/2, -size/2);
            }
            default -> {
                System.out.println("todo exeption and see how java build in class like Color behave when HV != 0 || 1");
                return;
            }
        }
        this.height = (int)Math.abs(this.end.y - this.start.y)/2;//ini height
        this.width = (int)Math.abs(this.end.x - this.start.x)/2;//ini width
        this.vertices.add(new Vector2(this.start));//add vertices start
        this.vertices.add(new Vector2(this.end));//add vertices end
        this.center = new Vector2((this.start.x + this.end.x)/2,(this.start.y + this.end.y)/2);//ini center
        this.position = new Vector2(pos);//ini position
    }
    /**Line constructor
     * ini start throught height and width*/
    public Line(int height, int width, Vector2 pos, Color c){
        super(c);//AbstractShape constructor
        this.height = height;//ini height
        this.width = width;//ini width
        this.start = new Vector2(-width, height);//compute start
        this.end = new Vector2(width, -height);//compute end
        this.vertices.add(new Vector2(this.start));//add vertices start
        this.vertices.add(new Vector2(this.end));//add vertices end
        this.center = new Vector2((this.start.x + this.end.x)/2,(this.start.y + this.end.y)/2);//ini center
        this.position = new Vector2(pos);//ini position
        this.size = (int)Math.sqrt(((start.x - end.x) * (start.x - end.x)) + ((start.y - end.y) * (start.y - end.y)));
    }
    /**Line constructor
     * throught start, end points*/
    public Line(Vector2 start, Vector2 end, Vector2 pos, Color c){
        super(c);//AbstractShape constructor
        this.start = new Vector2(start);//ini start
        this.end = new Vector2(end);//ini end
        this.center = new Vector2((this.start.x + this.end.x)/2,(this.start.y + this.end.y)/2);//ini center
        this.vertices.add(new Vector2(this.start));//add vertices start
        this.vertices.add(new Vector2(this.end));//add vertices end
        this.position = new Vector2(pos);//ini position
        this.size = (int)Math.sqrt(((start.x - end.x) * (start.x - end.x)) + ((start.y - end.y) * (start.y - end.y)));
    }
    /**Method paint*/
    @Override
    public void paint() {
        ArrayList<Vector2> dots = getVertices(this.vertices);//get vertices point in screen dimension
        if(dots != null) { //if no points for paint return
            fillShape(new STriangle(dots.get(0), dots.get(1), dots.get(1)));//paint line
        }
    }
    /**Method for resize object*/
    @Override
    public void resize() {
        this.vertices.clear();//clear old vertices
        if(width == 0 && height == 0){
            this.start = this.start.mul(size);//ini new start
            this.end = this.end.mul(size);//ini new end
        }else {
            this.start = new Vector2(-width, height);//ini new start
            this.end = new Vector2(width, -height);//ini new end
        }
        this.vertices.add(new Vector2(this.start));//add new start
        this.vertices.add(new Vector2(this.end));//add new end
        this.center = new Vector2((this.start.x + this.end.x)/2,(this.start.y + this.end.y)/2);//ini new center
    }
}
