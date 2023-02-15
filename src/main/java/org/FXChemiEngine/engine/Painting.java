package org.FXChemiEngine.engine;

import javafx.scene.paint.Color;
import org.FXChemiEngine.draw.DrawingObject;

/**Interface for print Shapes on scene*/
public interface Painting {
    /**pint object on scene*/
    void paint();

    default void draw(DrawingObject<?> drawingObject, int i, int j, Color color){
        drawingObject.draw(i, j, color);
    }
}
