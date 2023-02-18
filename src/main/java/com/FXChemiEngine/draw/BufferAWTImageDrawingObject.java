package com.FXChemiEngine.draw;

import com.FXChemiEngine.buffer.WritableAWTImageBuffer;
import com.FXChemiEngine.util.Color;
import com.FXChemiEngine.util.Functional;
import com.FXChemiEngine.util.Utils;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BufferAWTImageDrawingObject implements DrawingObject<BufferedImage> {
    private static int[] BACKGROUND_COLOR_ARRAY;
    private static final int BUFFER_SIZE = 3;
    private final BlockingQueue<WritableAWTImageBuffer> emptyBuffers = new ArrayBlockingQueue<>(BUFFER_SIZE);
    private final BlockingQueue<WritableAWTImageBuffer> fullBuffers = new ArrayBlockingQueue<>(BUFFER_SIZE);
    private WritableAWTImageBuffer currentBuffer;
    private WritableAWTImageBuffer drawBuffer;
    private Color backgroundColor;
    private int width, height;

    @Override
    public BufferedImage get() {
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        img.setRGB(0, 0, width, height, currentBuffer.getBuffer(), 0, width);
        return img;
    }

    @Override
    public void draw(int x, int y, Color color) {
        drawBuffer.setArgb(x, y, Utils.toARGB(color));
    }

    @Override
    public void clear() {
        if(drawBuffer != null)
            drawBuffer.setPixels(BACKGROUND_COLOR_ARRAY);
    }

    @Override
    public void ini(int width, int height, Color backgroundColor) {
        this.width = width;
        this.height = height;
        this.backgroundColor = backgroundColor;
        BACKGROUND_COLOR_ARRAY = new int[width * height];

        Arrays.fill(BACKGROUND_COLOR_ARRAY, Utils.toARGB(this.backgroundColor));

        for (int i = 0; i < BUFFER_SIZE; i++) {
            emptyBuffers.add(new WritableAWTImageBuffer(width, height));
        }
    }

    @Override
    public void resize(int width, int height) {
        emptyBuffers.clear();
        fullBuffers.clear();
        ini(width, height, backgroundColor);
    }

    @Override
    public void paint(Functional function) {
        try {
            var buffer = emptyBuffers.take();
            if(buffer.getPixels().length != BACKGROUND_COLOR_ARRAY.length) {
                //System.out.println("Error");
                return;//TODO
            }
            drawBuffer = buffer;

            function.action();//invoke draw method

            fullBuffers.add(buffer);


        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            var buffer = fullBuffers.take();

            if (currentBuffer != null) {
                emptyBuffers.add(currentBuffer);
            }

            buffer.updateBuffer();

            currentBuffer = buffer;
            drawBuffer = buffer;

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
