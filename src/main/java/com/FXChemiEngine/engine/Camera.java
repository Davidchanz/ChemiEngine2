package com.FXChemiEngine.engine;

import com.FXChemiEngine.math.UnityMath.Vector2;

/**Class for 2D camera*/
public class Camera {
    public Vector2 position;//camera position
    /**Camera constructor
     * ini camera position.*/
    Camera(){
        this.position = new Vector2(0,0);
    }
    /**Fake camera: move world coords instead camera projection*/
    public Vector2 projection(Vector2 v){
        return new Vector2(v.x  - this.position.x,v.y - this.position.y);//move point in opposite direction from camera position
    }
}
