package me.winspeednl.libz.graphics;

import android.graphics.Canvas;

import me.winspeednl.libz.core.GameCore;

/**
 * Created by sven on 27-1-17.
 */

public class Render {
    private GameCore gc;
    private Canvas canvas;
    private Graphics graphics;

    public Render(GameCore gc) {
        this.gc = gc;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
        graphics = new Graphics(canvas);
    }

    public Canvas getCanvas() {
        return canvas;
    }
    public Graphics getGraphics() {
        return graphics;
    }

    public void renderLight(int x, int y, int radius) {
        int[] pixels = new int[getCanvas().getWidth() * getCanvas().getHeight()];
        x -= getGraphics().getOffsetX();
        y -= getGraphics().getOffsetY();
        int x0 = x - radius;
        int x1 = x + radius;
        int y0 = y - radius;
        int y1 = y + radius;

        if (x0 < 0) x0 = 0;
        if (y0 < 0) y0 = 0;
        if (x1 > getCanvas().getWidth()) x1 = getCanvas().getWidth();
        if (y1 > getCanvas().getHeight()) y1 = getCanvas().getHeight();
        // System.out.println(x0 + ", " + x1 + " -> " + y0 + ", " + y1);
        for (int yy = y0; yy < y1; yy++) {
            int yd = yy - y;
            yd = yd * yd;
            for (int xx = x0; xx < x1; xx++) {
                int xd = xx - x;
                int dist = xd * xd + yd;
                // System.out.println(dist);
                if (dist <= radius * radius) {
                    int br = 255 - dist * 255 / (radius * radius);
                    if (pixels[xx + yy * getCanvas().getWidth()] < br) pixels[xx + yy * getCanvas().getWidth()] = br;
                }
            }
        }
    }
}