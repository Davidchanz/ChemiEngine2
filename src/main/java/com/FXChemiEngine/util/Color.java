package com.FXChemiEngine.util;

public class Color {
    public static final Color RED = new Color(1.0, 0.0, 0.0);
    public static final Color GREEN = new Color(0.0, 1.0, 0.0);
    public static final Color BLUE = new Color(0.0F, 0.0F, 1.0F);
    public static final Color MAGENTA = new Color(1.0F, 0.0F, 1.0F);
    public static final Color WHITE = new Color(1.0, 1.0, 1.0);
    public static final Color BLACK = new Color(0.0F, 0.0F, 0.0F);
    public static final Color TRANSPARENT = new Color(0.0, 0.0, 0.0, 0.0);
    public static final Color ALICEBLUE = new Color(0.9411765F, 0.972549F, 1.0F);
    public static final Color ANTIQUEWHITE = new Color(0.98039216F, 0.92156863F, 0.84313726F);
    public static final Color AQUA = new Color(0.0F, 1.0F, 1.0F);
    public static final Color AQUAMARINE = new Color(0.49803922F, 1.0F, 0.83137256F);
    public static final Color AZURE = new Color(0.9411765F, 1.0F, 1.0F);
    public static final Color BEIGE = new Color(0.9607843F, 0.9607843F, 0.8627451F);
    public static final Color BISQUE = new Color(1.0F, 0.89411765F, 0.76862746F);
    public static final Color BLANCHEDALMOND = new Color(1.0F, 0.92156863F, 0.8039216F);
    public static final Color BLUEVIOLET = new Color(0.5411765F, 0.16862746F, 0.8862745F);
    public static final Color BROWN = new Color(0.64705884F, 0.16470589F, 0.16470589F);
    public static final Color BURLYWOOD = new Color(0.87058824F, 0.72156864F, 0.5294118F);
    public static final Color CADETBLUE = new Color(0.37254903F, 0.61960787F, 0.627451F);
    public static final Color CHARTREUSE = new Color(0.49803922F, 1.0F, 0.0F);
    public static final Color CHOCOLATE = new Color(0.8235294F, 0.4117647F, 0.11764706F);
    public static final Color CORAL = new Color(1.0F, 0.49803922F, 0.3137255F);
    public static final Color CORNFLOWERBLUE = new Color(0.39215687F, 0.58431375F, 0.92941177F);
    public static final Color CORNSILK = new Color(1.0F, 0.972549F, 0.8627451F);
    public static final Color CRIMSON = new Color(0.8627451F, 0.078431375F, 0.23529412F);
    public static final Color CYAN = new Color(0.0F, 1.0F, 1.0F);
    public static final Color DARKBLUE = new Color(0.0F, 0.0F, 0.54509807F);
    public static final Color DARKCYAN = new Color(0.0F, 0.54509807F, 0.54509807F);
    public static final Color DARKGOLDENROD = new Color(0.72156864F, 0.5254902F, 0.043137256F);
    public static final Color DARKGRAY = new Color(0.6627451F, 0.6627451F, 0.6627451F);
    public static final Color DARKGREEN = new Color(0.0F, 0.39215687F, 0.0F);
    private double Red, Green, Blue, Alpha;

    public Color(double red, double green, double blue, double alpha) {
        Red = red;
        Green = green;
        Blue = blue;
        Alpha = alpha;
    }
    public Color(float red, float green, float blue, float alpha) {
        Red = red;
        Green = green;
        Blue = blue;
        Alpha = alpha;
    }

    public Color(double red, double green, double blue) {
        Red = red;
        Green = green;
        Blue = blue;
        Alpha = 1.0;
    }
    public Color(float red, float green, float blue) {
        Red = red;
        Green = green;
        Blue = blue;
        Alpha = 1.0F;
    }

    public double getRed() {
        return Red;
    }

    public void setRed(double red) {
        Red = red;
    }

    public double getGreen() {
        return Green;
    }

    public void setGreen(double green) {
        Green = green;
    }

    public double getBlue() {
        return Blue;
    }

    public void setBlue(double blue) {
        Blue = blue;
    }

    public double getAlpha() {
        return Alpha;
    }

    public void setAlpha(double alpha) {
        Alpha = alpha;
    }

    public Color setColor(javafx.scene.paint.Color color){
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getOpacity());
    }

    public Color setColor(java.awt.Color color){
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }
}
