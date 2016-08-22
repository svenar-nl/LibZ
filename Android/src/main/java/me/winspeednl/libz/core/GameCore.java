package me.winspeednl.libz.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import me.winspeednl.libz.screen.Render;

public class GameCore extends SurfaceView implements Runnable {

    private SurfaceHolder surfaceHolder;
    private Canvas canvas;
    private Thread thread;
    private LibZ game;
    private Render renderer;
    private Bitmap image;

    private boolean isRunning;
    private double frameCap = 1D / 60D;
    private int fps;

    public GameCore(Context context) {
        super(context);

        surfaceHolder = getHolder();
    }

    public void run() {
        while (true) {
            if (getWidth() > 0 && getHeight() > 0) {
                renderer = new Render(this);
                image = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
                break;
            }
        }

        double currTime;
        double lastTime = System.nanoTime() / 1000000000D;
        double passedTime;
        double unprocessedTime = 0;
        double frameTime = 0;
        int frames = 0;

        while(isRunning) {
            if (!surfaceHolder.getSurface().isValid())
                continue;

            boolean render = false;

            currTime = System.nanoTime() / 1000000000D;
            passedTime = currTime - lastTime;
            lastTime = currTime;

            unprocessedTime += passedTime;
            frameTime += passedTime;

            while (unprocessedTime >= frameCap) {
                game.update(this);
                unprocessedTime -= frameCap;
                render = true;

                if(frameTime >= 1) {
                    frameTime = 0;
                    fps = frames;
                    frames = 0;
                }

                if (render) {
                    canvas = surfaceHolder.lockCanvas();
                    game.render(this, renderer);
                    image.setPixels(renderer.getPixels(), 0, getWidth(), 0, 0, getWidth(), getHeight());
                    canvas.drawBitmap(image, 0, 0, null);
                    surfaceHolder.unlockCanvasAndPost(canvas);

                    frames++;
                } else {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void pause() {
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread = null;
    }

    public void resume() {
        if (game != null) {
            game.init(this);
            isRunning = true;
            thread = new Thread(this);
            thread.start();
        }
    }

    public void setGame(LibZ game) {
        this.game = game;
    }

    public void clear(Canvas canvas) {
        Paint clearPaint = new Paint();
        clearPaint.setColor(Color.TRANSPARENT);
        clearPaint.setStyle(Paint.Style.FILL);
        Rect clearRect = new Rect();
        clearRect.set(0, 0, canvas.getWidth(), canvas.getHeight());
        canvas.drawRect(clearRect, clearPaint);
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public int getFPS() {
        return fps;
    }
}