package com.FXChemiEngine.buffer;

import com.FXChemiEngine.util.Color;
import com.FXChemiEngine.util.Utils;

public class IntMatrixImageBuffer {
    private int[] buffer;
    private int width, height;
    private final String brightness = "$@B%8&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/|()1{}[]?-_+~<>i!lI;:,^`'.";

    public IntMatrixImageBuffer(int width, int height) {
        this.buffer = new int[width*height];
        this.width = width;
        this.height = height;
    }

    public int[] getBuffer() {
        return buffer;
    }

    public int[][] getBlackWhite(){
        int[][] pixelsBW = new int[width][height];
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++){
                var C = new java.awt.Color(buffer[j * width + i]);
                double grayscale = 0.3 * C.getRed() + 0.59 * C.getGreen() + 0.11 * C.getBlue();
                pixelsBW[i][j] = Utils.toARGB(new Color(grayscale, grayscale, grayscale));
        }
        return pixelsBW;
    }

    public int[][] getPixels(){
        int[][] pixels = new int[width][height];
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++)
                pixels[i][j] = buffer[j * width + i];
        return pixels;
    }

    public void setARGB(int x, int y, Color color) {
        buffer[y * width + x] = Utils.toARGB(color);
    }

    @Override
    public String toString() {
        String frame = "";
        int[][] pixels = this.getBlackWhite();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                var C = new java.awt.Color(pixels[j][i]);
                double BW = C.getRed();
                frame += brightness.charAt((int) (BW / (double) brightness.length()));
            }
            frame += "\n";
        }
        return frame;
    }
}
