package org.FXChemiEngine.buffer;

import javafx.scene.image.PixelBuffer;
import javafx.scene.image.PixelFormat;

import java.nio.IntBuffer;

public class WritableImageBuffer {
    private int[] rawInts;

    private int width;
    private int height;

    private IntBuffer buffer;

    private PixelBuffer<IntBuffer> pixelBuffer;

    public int gpuIndex = 0;

    public WritableImageBuffer(int width, int height) {
        super();
        this.width = width;
        this.height = height;

        buffer = IntBuffer.allocate(width * height);
        rawInts = buffer.array();

        pixelBuffer = new PixelBuffer<>(width, height, buffer, PixelFormat.getIntArgbPreInstance());

        //setImage(new WritableImage(pixelBuffer));
    }

    public int[] getPixels() {
        return rawInts;
    }

    /**
     * Set all pixels from given buffer into this image's buffer.
     */
    public void setPixels(int[] rawPixels) {
        System.arraycopy(rawPixels, 0, rawInts, 0, rawPixels.length);
    }

    /**
     * Set a single pixel of this image's buffer at x, y to given ARGB color.
     */
    public void setArgb(int x, int y, int colorARGB) {
        rawInts[y * width + x] = colorARGB;
    }

    public void updateBuffer() {
        this.pixelBuffer.updateBuffer((b) -> {
            return null;
        });
    }

    public PixelBuffer<IntBuffer> getPixelBuffer() {
        return pixelBuffer;
    }
}