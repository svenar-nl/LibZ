package me.winspeednl.libz.graphics;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;

import me.winspeednl.libz.image.Sprite;

/**
 * Created by sven on 29-1-17.
 */

public class Graphics {

    private Paint paint;
    private Canvas canvas;
    private int offsetX, offsetY;

    public Graphics(Canvas canvas) {
        this.canvas = canvas;
        paint = new Paint();
    }

    public void clearRect(int x, int y, int width, int height) {
        int previousColor = paint.getColor();
        paint.setColor(Color.BLACK);
        canvas.drawRect(x, y, x + width, y + height, paint);
        paint.setColor(previousColor);
    }

    public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        Paint.Style previousStyle = paint.getStyle();
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(x, y, x + width, y + height, startAngle, arcAngle, false, paint);
        paint.setStyle(previousStyle);
    }

    public void drawImage(Bitmap image, int x, int y) {
        canvas.drawBitmap(image, x, y, paint);
    }

    public void drawImage(Bitmap image, int x, int y, int width, int height) {
        float scaleWidth = ((float) width) / image.getWidth();
        float scaleHeight = ((float) height) / image.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        image = Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), matrix, false);
        canvas.drawBitmap(image, x, y, paint);
    }

    public void drawLine(int x1, int y1, int x2, int y2) {
        canvas.drawLine(x1, y1, x2, y2, paint);
    }

    public void drawOval(int x, int y, int width, int height) {
        drawArc(x, y, width, height, 0, 360);
    }

    public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints) {
        Paint.Style previousStyle = paint.getStyle();
        paint.setStyle(Paint.Style.STROKE);
        Path path = new Path();
        for (int i = 0; i < nPoints; i++) {
            if (i == 0) path.moveTo(xPoints[0], yPoints[0]);
            else path.lineTo(xPoints[i], yPoints[i]);
        }
        canvas.drawPath(path, paint);
        paint.setStyle(previousStyle);
    }

    public void drawPolyline(int[] xPoints, int[] yPoints, int nPoints) {
        drawPolygon(xPoints, yPoints, nPoints);
    }

    public void drawRect(int x, int y, int width, int height) {
        x = Math.min(x, x + width);
        width = Math.abs(width);
        y = Math.min(y, y + height);
        height = Math.abs(height);
        Paint.Style previousStyle = paint.getStyle();
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(x, y, x + width, y + height, paint);
        paint.setStyle(previousStyle);
    }

    public void drawString(String str, int x, int y) {
        canvas.drawText(str, x, y, paint);
    }

    public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        Paint.Style previousStyle = paint.getStyle();
        paint.setStyle(Paint.Style.FILL);
        canvas.drawArc(x, y, x + width, y + height, startAngle, arcAngle, false, paint);
        paint.setStyle(previousStyle);
    }

    public void fillOval(int x, int y, int width, int height) {
        fillArc(x, y, width, height, 0, 360);
    }

    public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints) {
        Paint.Style previousStyle = paint.getStyle();
        paint.setStyle(Paint.Style.FILL);
        Path path = new Path();
        for (int i = 0; i < nPoints; i++) {
            if (i == 0) path.moveTo(xPoints[0], yPoints[0]);
            else path.lineTo(xPoints[i], yPoints[i]);
        }
        canvas.drawPath(path, paint);
        paint.setStyle(previousStyle);
    }

    public void fillRect(int x, int y, int width, int height) {
        x = Math.min(x, x + width);
        width = Math.abs(width);
        y = Math.min(y, y + height);
        height = Math.abs(height);
        Paint.Style previousStyle = paint.getStyle();
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(x, y, x + width, y + height, paint);
        paint.setStyle(previousStyle);
    }

    public int getColor() {
        return paint.getColor();
    }

    public float getFontSize() {
        return paint.getTextSize();
    }

    public void setColor(int color) {
        paint.setColor(color);
    }

    public void setFontSize(float size) {
        paint.setTextSize(size);
    }

    public void translate(int x, int y) {
        canvas.translate(x, y);
    }

    public void drawSprite(Sprite sprite, int x, int y) {
        canvas.drawBitmap(sprite.getBitmap(), x, y, paint);
    }

    public void drawCircle(int x, int y, int circleRadius) {
        drawOval(x, y, circleRadius, circleRadius);
    }

    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    public int getAlpha() {
        return paint.getAlpha();
    }

    public void setTextAlign(Paint.Align align) {
        paint.setTextAlign(align);
    }

    public Paint.Align getTextAlign() {
        return paint.getTextAlign();
    }

    public Paint getPaint() {
        return paint;
    }

    public void setOffset(int x, int y) {
        canvas.translate(x, y);
        offsetX = x;
        offsetY = y;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }
}