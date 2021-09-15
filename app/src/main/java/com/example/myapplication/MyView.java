package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MyView extends View {
    Paint myPaint;
    Path myPath;

    public MyView(Context context) {
        super(context);
        Log.i("MyView", "construct");

        myPaint = new Paint();
        myPaint.setStrokeWidth(7.0f);
        myPaint.setStyle(Paint.Style.STROKE);
        myPaint.setColor(getResources().getColor(R.color.colorFingerPainting));

        myPath = new Path();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawPath(myPath, myPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("MotionEvent", "DOWN");
                Log.i("DOWN -X,Y", String.valueOf(event.getX()) + "," + String.valueOf(event.getY()));

                myPath.moveTo(x, y);
                break;

            case MotionEvent.ACTION_MOVE:
                Log.i("MotionEvent", "MOVE");
                Log.i("MOVE -X,Y", String.valueOf(event.getX()) + "," + String.valueOf(event.getY()));

                myPath.lineTo(x, y);
                break;

            case MotionEvent.ACTION_UP:
                Log.i("MotionEvent", "UP");
                Log.i("UP -X,Y", String.valueOf(event.getX()) + "," + String.valueOf(event.getY()));

                break;
        }

        invalidate();
        return true;

    }
    public Bitmap getCanvasBitmap(){
        Bitmap bitmap = Bitmap.createBitmap(this.getWidth(),this.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        this.draw(canvas);

        return bitmap;
    }
}

