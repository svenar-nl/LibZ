package me.winspeednl.libz.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.util.Random;

import me.winspeednl.libz.core.GameCore;

/**
 * Created by sven on 29-1-17.
 */

public class Sprite {

    private Bitmap bitmap, originalBitmap;
    private boolean flipX = false, flipY = false;
    private float rotation = 0;
    private int id;

    public Sprite(int id) {
        this.id = id;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        bitmap = originalBitmap = BitmapFactory.decodeResource(GameCore.getStaticContext().getResources(), id, options);
    }

    public Sprite(Bitmap bitmap) {
        this.bitmap = originalBitmap = bitmap;
    }

    public Sprite(Sprite sprite) {
        bitmap = originalBitmap = sprite.getBitmap();
    }

    public void setRotation(float angle) {
        Matrix matrix = new Matrix();
        rotation = angle;
        matrix.postRotate(angle);
        bitmap = Bitmap.createBitmap(originalBitmap, 0, 0, originalBitmap.getWidth(), originalBitmap.getHeight(), matrix, true);
    }

    public void cut(int x, int y, int width, int height) {
        bitmap = Bitmap.createBitmap(originalBitmap, x, y, width, height);
        id = new Random().nextInt(89999) + 10000;
    }

    public void flip(boolean flipX, boolean flipY) {
        Matrix matrix = new Matrix();
        this.flipX = flipX;
        this.flipY = flipY;
        matrix.setScale(flipX ? -1 : 1, flipY ? -1 : 1);
        bitmap = Bitmap.createBitmap(originalBitmap, 0, 0, originalBitmap.getWidth(), originalBitmap.getHeight(), matrix, true);
    }

    public void setSize(int width, int height) {
        float scaleWidth = ((float) width) / getWidth();
        float scaleHeight = ((float) height) / getHeight();

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        bitmap = Bitmap.createBitmap(bitmap, 0, 0, getWidth(), getHeight(), matrix, false);
    }

    public int getWidth() {
        return bitmap.getWidth();
    }

    public int getHeight() {
        return bitmap.getHeight();
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public Bitmap getOriginalBitmap() {
        return originalBitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public boolean isFlippedX() {
        return flipX;
    }

    public boolean isFlippedY() {
        return flipY;
    }

    public float getRotation() {
        return rotation;
    }

    public void overwriteCurrentAsOriginal() {
        originalBitmap = bitmap;
    }

    public Sprite copy() {
        Sprite sprite = new Sprite(id);
        sprite.setBitmap(bitmap);
        sprite.overwriteCurrentAsOriginal();
        return sprite;
    }

    public int getId() {
        return id;
    }
}