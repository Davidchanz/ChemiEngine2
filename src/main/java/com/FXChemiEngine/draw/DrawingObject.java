package com.FXChemiEngine.draw;

import com.FXChemiEngine.util.Color;
import com.FXChemiEngine.util.Functional;

public interface DrawingObject<R> {
    R get();
    void draw(int x, int y, Color color);
    void clear();
    void ini(int width, int height, Color backgroundColor);
    void resize(int width, int height);
    void paint(Functional function);
}
