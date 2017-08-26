package me.winspeednl.libz.input;

import android.graphics.PointF;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

/**
 * Created by sven on 29-1-17.
 */

public class TouchHandler implements View.OnTouchListener {

    //private int x, y;
    //private boolean pressed, moving;
    private SparseArray<PointF> pointers = new SparseArray<PointF>();

    public TouchHandler(SurfaceView surfaceView) {
        surfaceView.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        int pointerIndex = event.getActionIndex();
        int pointerId = event.getPointerId(pointerIndex);
        int action = event.getActionMasked();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN: {
                PointF p = new PointF();
                p.x = pointerId < event.getPointerCount() ? event.getX(pointerId) : -1;
                p.y = pointerId < event.getPointerCount() ? event.getY(pointerId) : -1;
                pointers.put(pointerId, p);
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                for (int size = event.getPointerCount(), i = 0; i < size; i++) {
                    PointF point = pointers.get(event.getPointerId(i));
                    if (point != null) {
                        point.x = event.getX(i);
                        point.y = event.getY(i);
                    }
                }
                break;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL: {
                //pointers.remove(pointerId);
                PointF point = pointers.get(pointerId);
                if (point != null) {
                    point.x = -1;
                    point.y = -1;
                }
                break;
            }
        }
        return true;
    }

    public SparseArray<PointF> getPointers() {
        return pointers;
    }

    public PointF getFirstPointer() {
        PointF pointer = new PointF(-1, -1);
        for (int size = pointers.size(), i = 0; i < size; i++) {
            if (getPointer(i).x > 0 && getPointer(i).y > 0) {
                pointer = getPointer(i);
                break;
            };
        }
        return pointer;
    }

    public PointF getPointer(int id) {
        PointF fallback = new PointF(-1, -1);
        return pointers.get(id, fallback);
    }

    public boolean isTouching() {
        int count = 0;
        for (int size = pointers.size(), i = 0; i < size; i++) {
            if (getPointer(i).x > 0 && getPointer(i).y > 0) count++;
        }
        return count > 0;
    }

    public boolean isSingleTouch() {
        int count = 0;
        for (int size = pointers.size(), i = 0; i < size; i++) {
            if (getPointer(i).x > 0 && getPointer(i).y > 0) count++;
        }
        return count == 1;
    }
}
