package com.example.mateusz.paint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class AreaPainting extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private SurfaceHolder container;
    private Thread threadPaint;
    private boolean threadPaintWork = false;
    private Object lock = new Object();
    private Bitmap bitmap = null;
    private Canvas myCanvas = null;
    private Paint color = new Paint();
    private float positionX;
    private float positionY;
    private float earlierPositionX;
    private float earlierPositionY;

    public AreaPainting(Context context, AttributeSet attrs) {
        super(context, attrs);

        container = getHolder();
        container.addCallback(this);
    }

    public void startPaint() {
        threadPaint = new Thread(this);
        threadPaintWork = true;
        threadPaint.start();
    }

    public void stopPaint() {
        threadPaintWork = false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        performClick();
        color.setStrokeWidth(15);
        synchronized (lock) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: //Dotkniecie
                    stopPaint();
                    startPaint();
                    positionX = event.getX();
                    positionY = event.getY();
                    color.setColor(MainActivity.paintColor);
                    myCanvas.drawCircle(positionX, positionY, 30, color);
                    earlierPositionX = positionX;
                    earlierPositionY = positionY;
                    return true;
                case MotionEvent.ACTION_MOVE: //Przesuniecie
                    positionX = event.getX();
                    positionY = event.getY();
                    myCanvas.drawLine(earlierPositionX, earlierPositionY, positionX, positionY, color);
                    earlierPositionX = positionX;
                    earlierPositionY = positionY;
                    return true;
                case MotionEvent.ACTION_UP: //Puszczenie
                    positionX = event.getX();
                    positionY = event.getY();
                    myCanvas.drawCircle(positionX, positionY, 30, color);
                    return true;
                default:
                    break;
            }
        }
        return false;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        myCanvas = new Canvas(bitmap);
        myCanvas.drawARGB(255, 255, 255, 255);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        threadPaintWork = false;
    }

    @Override
    public void run() {
        while (threadPaintWork) {
            Canvas canvas = null;
            try {
                synchronized (container) {
                    if (!container.getSurface().isValid()) continue;
                    canvas = container.lockCanvas(null);
                    synchronized (lock) {
                        if (threadPaintWork) {
                            if (MainActivity.clear == true) {
                                canvas.drawARGB(255, 255, 255, 255);
                                MainActivity.setClear();
                            }
                            canvas.drawBitmap(bitmap, 0, 0, null);
                        }
                    }
                }
            } finally {
                if (canvas != null) {
                    container.unlockCanvasAndPost(canvas);
                }
            }
            try {
                Thread.sleep(1000 / 25);
            } catch (InterruptedException ex) {

            }
        }
    }
}
