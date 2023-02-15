package org.FXChemiEngine.engine;

import org.FXChemiEngine.draw.DrawingObject;
import org.FXChemiEngine.math.UnityMath.Vector2;
import org.FXChemiEngine.math.UnityMath.Vector3;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Consumer;

/**Class for scene object which especially is a list of AbstractShape elements*/
public class ShapeObject extends EngineObject implements Iterable<Shape>{
    public final ArrayList<Shape> body;//set of Abstract shape
    public String name;//name
    protected DrawingObject<?> drawingObject;//drawObject from Scene

    /**ShapeObject constructor
     * ini all members default*/
    public ShapeObject(){
        this.id = 0;//ini id
        this.name = "Template";//ini name
        this.body = new ArrayList<>();//ini body
        this.rotation = new Vector3(0,0,0);
        this.center = new Vector2(0,0);//ini center
        this.position = new Vector2(0,0);//ini position
    }

    /**ShapeObject constructor
     * ini name and id*/
    public ShapeObject(String name, int id){
        this();//invoke default constructor
        this.id = id;//ini id
        this.name = name;//ini name
    }

    /**ShapeObject constructor
     * ini id name adn position*/
    public ShapeObject(String name, int id, Vector2 pos){
        this(name, id);//ini name id constructor
        this.position = new Vector2(pos);//ini position
    }

    /**Method move*/
    public void move(Vector2 dir){
        this.position.add(dir);//move position on dir
        this.center.add(dir);//move center on dir TODO
        for(var i: this.body){
            i.position.add(dir);//move all AbstractShapes
        }
    }

    @Deprecated
    public void setCenterVisible(boolean b){
        for(var shape: this.body) {
            //g.drawString(objects.indexOf(it) + it.body.indexOf(shape) + "", (int)shape.position.x+200, (int)shape.position.y+200);
            //shape.CENTER = b;
        }
    }

    /**Add new element on object*/
    public Shape add(Shape o){
        o.id = this.id;//ini new shape id
        o.rotation.x += this.rotation.x;//ini new shape angleX
        o.rotation.y += this.rotation.y;//ini new shape angleY
        o.rotation.z += this.rotation.z;//ini new shape angleZ
        float sumX = o.center.x + o.position.x;
        float sumY = o.center.y + o.position.y;
        for(var i: this.body.toArray(new Shape[0])){
            sumX+= i.center.x + i.position.x;//compute sum centers X
            sumY+= i.center.y + i.position.y;//compute sum centers Y
        }
        this.center = new Vector2(sumX/(this.body.size()+1), sumY/(this.body.size()+1));//ini center
        this.position.add(o.position);//ini position
        //for(var i: this.body){
        o.setParent(this);//ini shape parent
        //}
        this.body.add(o);//add new shape in body
        return o;
    }

    /**Add new element collection on object*/
    public void addAll(Collection<Shape> o){
        for(var i : o){
            add(i);//invoke add single add() for all new shapes in collections
        }
    }

    /**Clear object body, delete all shapes in object*/
    public void clear(){
        synchronized (this.body) {
            this.body.clear();
            this.rotation = new Vector3(0,0,0);
            this.center = new Vector2(0,0);//ini center
            this.position = new Vector2(0,0);//ini position
        }
    }

    @NotNull
    @Override
    public Iterator<Shape> iterator() {
        return this.body.iterator();
    }

    @Override
    public void forEach(Consumer<? super Shape> action) {
        this.body.forEach(action);
    }
}
