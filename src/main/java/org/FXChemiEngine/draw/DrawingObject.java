package org.FXChemiEngine.draw;

import javafx.scene.paint.Color;
import org.FXChemiEngine.util.Functional;

import java.util.function.Consumer;

public interface DrawingObject<R> {
    R get();
    void draw(int x, int y, Color color);
    void clear();
    void ini(int width, int height, Color backgroundColor);
    void resize(int width, int height);
    void paint(Functional function);
}
