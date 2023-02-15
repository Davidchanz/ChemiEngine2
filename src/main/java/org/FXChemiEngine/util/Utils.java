package org.FXChemiEngine.util;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Dimension2D;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class Utils {
    public static int toARGB(Color color) {
        if(color == null)
            color = Color.TRANSPARENT;
        return (int) (color.getOpacity() * 255) << 24
                | (int) (color.getRed() * 255) << 16
                | (int) (color.getGreen() * 255) <<  8
                | (int) (color.getBlue() * 255);
    }

    /**Interpolete to points*///todo explore this function
    public static ArrayList<Integer> Interpolate (float i0, float d0, float i1, float d1) {
        var values = new ArrayList<Integer>();
        if (i0 == i1) {
            values.add((int)d0);
            return values;
        }
        float a = (d1 - d0) / (i1 - i0);
        float d = d0;
        for (int i = (int)i0; i <= (int)i1; ++i) {
            values.add((int)d);
            d = d + a;
        }
        return values;
    }

    public static ChangeListener<Number> resizeSceneAction(Stage stage, Dimension2D offset, Function<Dimension2D, Void> action){
        return (observable, oldValue, newValue) -> {
            //System.out.println("Height: " + stage.getHeight() + " Width: " + stage.getWidth());
            double w = stage.getWidth();
            double h = stage.getHeight();
            if(!Double.isNaN(w) && !Double.isNaN(h))
                action.apply(new Dimension2D(w + offset.getWidth(), h + offset.getHeight()));//controller.resize(w, h-100);
        };
    }
}
