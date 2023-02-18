package com.FXChemiEngine.engine;

import com.FXChemiEngine.draw.DrawingObject;
import com.FXChemiEngine.util.Color;

/**Interface for print Shapes on scene*/
public interface Painting {
    /**pint object on scene*/
    void paint();

    default void draw(DrawingObject<?> drawingObject, int i, int j, Color color){
        drawingObject.draw(i, j, color);
    }
}
