package me.winspeednl.libz.core;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

import me.winspeednl.libz.graphics.Render;
import me.winspeednl.libz.input.TouchHandler;

/**
 * Created by sven on 27-1-17.
 */

public class GameCore extends SurfaceView implements Runnable {

    private SurfaceHolder surfaceHolder;
    private Canvas canvas;
    private Thread thread;
    private LibZ game;
    private Render renderer;
    private TouchHandler touchHandler;

    private boolean isRunning = false, fullscreen = false;
    private int fps, width, height;
    private double targetFPS = 60, frameCap = 1D / targetFPS;

    public int ORIENTATION_PORTRAIT = 0, ORIENTATION_LANDSCAPE = 1, ORIENTATION_REVERSE_PORTRAIT = 2, ORIENTATION_REVERSE_LANDSCAPE = 3;

    private static Context context;
    private static Activity activity;

    public GameCore(Activity activity) {
        super((Context)activity);
        this.context = (Context)activity;
        this.activity = activity;
        activity.requestWindowFeature (Window.FEATURE_NO_TITLE);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        surfaceHolder = getHolder();
        touchHandler = new TouchHandler(this);
    }

    public void run() {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        height = metrics.heightPixels;
        width = metrics.widthPixels;
        int tryCount = 0, maxTries = 20;

        while (true) {
            if (getWidth() > 0 && getHeight() > 0) {
                System.out.println(tryCount);
                break;
            } else tryCount++;
            if (tryCount >= maxTries) {
                break;
            }
        }
        renderer = new Render(this);
        game.init(this);

        double currTime;
        double lastTime = System.nanoTime() / 1000000000D;
        double passedTime;
        double unprocessedTime = 0;
        double frameTime = 0;
        int frames = 0;
        long fpsTime = 0;

        while (isRunning) {
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

                if (frameTime >= 1) {
                    frameTime = 0;
                }
                if (System.currentTimeMillis() - fpsTime > 1000) {
                    fpsTime = System.currentTimeMillis();
                    fps = frames;
                    frames = 0;
                }

                if (render) {
                    try {
                        canvas = surfaceHolder.lockCanvas();
                        synchronized (surfaceHolder) {
                            canvas.save();
                            renderer.setCanvas(canvas);
                            game.render(this, renderer);
                            canvas.restore();
                        }
                        frames++;
                    } finally {
                        try {
                            if (canvas != null) surfaceHolder.unlockCanvasAndPost(canvas);
                        } catch (Exception e) {
                            continue;
                        }
                    }

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

    public void stop() {
        activity.finishAffinity();
    }

    public void resume() {
        if (game != null) {
            isRunning = true;
            thread = new Thread(this);
            thread.start();
        } else {
            System.out.println("no LibZ game has been defined!");
            System.out.println("gc.setGame(new YourGameClass());");
            System.out.println("Make sure YourGameClass extends LibZ");
        }
    }

    public void setFullscreen(boolean fullscreen) {
        this.fullscreen = fullscreen;
        if (fullscreen && activity != null) {
            activity.requestWindowFeature (Window.FEATURE_NO_TITLE);
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
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

    public int getScreenWidth() {
        return width;
    }

    public int getScreenHeight() {
        return height;
    }

    public void setTargetFPS(double fps) {
        this.targetFPS = fps;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public Render getRender() {
        return renderer;
    }

    public LibZ getGame() {
        return game;
    }

    public int getFPS() {
        return fps;
    }

    public static Context getStaticContext() {
        return context;
    }

    public TouchHandler getTouchHandler() {
        return touchHandler;
    }

    public void setOrientation(int orientation) {
        if (orientation == ORIENTATION_PORTRAIT) activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        else if (orientation == ORIENTATION_LANDSCAPE) activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        else if (orientation == ORIENTATION_REVERSE_PORTRAIT) activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
        else if (orientation == ORIENTATION_REVERSE_LANDSCAPE) activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
        else activity.finishAffinity();
    }
}
