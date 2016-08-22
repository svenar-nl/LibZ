package me.winspeednl.libz.screen;

import me.winspeednl.libz.core.GameCore;

public class Render {
    private int[] pixels;

    private GameCore gc;

    public Render(GameCore gc) {
        this.gc = gc;
        pixels = new int[gc.getWidth() * gc.getHeight()];
    }

    public void setPixel(int id, int color) {
        if (id < pixels.length)
            pixels[id] = color;
    }

    public void setPixel(int x, int y, int color) {
        if ((x < 0 || x >= gc.getWidth() || y < 0 || y >= gc.getHeight()) || color == 0x00000000)
            return;

        pixels[x + y * gc.getWidth()] = color;
    }

    public int[] getPixels() {
        return pixels;
    }
}
