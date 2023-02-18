package com.FXChemiEngine.draw;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import com.FXChemiEngine.buffer.WritableImageBuffer;
import com.FXChemiEngine.util.Color;
import com.FXChemiEngine.util.Functional;
import com.FXChemiEngine.util.Utils;

import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BufferImageDrawingObject implements DrawingObject<Image> {
    private static int[] BACKGROUND_COLOR_ARRAY;
    private static final int BUFFER_SIZE = 3;
    private final BlockingQueue<WritableImageBuffer> emptyBuffers = new ArrayBlockingQueue<>(BUFFER_SIZE);
    private final BlockingQueue<WritableImageBuffer> fullBuffers = new ArrayBlockingQueue<>(BUFFER_SIZE);
    private WritableImageBuffer currentBuffer;
    private WritableImageBuffer drawBuffer;
    private Color backgroundColor;
    @Override
    public Image get() {
        return new WritableImage(currentBuffer.getPixelBuffer());
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
        this.backgroundColor = backgroundColor;
        BACKGROUND_COLOR_ARRAY = new int[width * height];

        Arrays.fill(BACKGROUND_COLOR_ARRAY, Utils.toARGB(this.backgroundColor));

        for (int i = 0; i < BUFFER_SIZE; i++) {
            emptyBuffers.add(new WritableImageBuffer(width, height));
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
