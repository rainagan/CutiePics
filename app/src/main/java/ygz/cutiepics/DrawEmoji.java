package ygz.cutiepics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by zhaofan on 2018-03-24.
 */

class DrawEmoji extends View {
    private Bitmap canvasBitmap = Bitmap.createBitmap(480, 500, Bitmap.Config.ARGB_8888);
    private Bmp tempBitmap = null;
    private Canvas canvas = new Canvas(canvasBitmap);
    private float X = 0f;
    private float Y = 0f;
    private float DownX = 0f;
    private float DownY = 0f;
    private Bmp pic;

    Bmp bmp;

    //    	construct
    public DrawEmoji(Context context) {
        super(context);
        bmp = new Bmp(canvasBitmap, 50f,  60f);
        this.pic = bmp;
    }

    //    	construct
    public DrawEmoji(Context context, Bmp pic) {
        this(context);
        this.pic = pic;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(canvasBitmap, 0, 0, null);
    }

    //    	OntouchEvent
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            this.DownX = event.getX();
            this.DownY = event.getY();
        }
//    		find the picture that User touch and reOrder
//    		this.X = event.getX();
//    		this.Y = event.getY();
//    		order(event);

//			draw the Canvas
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            this.X = event.getX();
            this.Y = event.getY();
            //order(event);
            this.canvas.drawColor(-232432445);
            tempBitmap = pic;
            this.canvas.drawBitmap(tempBitmap.getPic(), tempBitmap.getXY(1) - tempBitmap.getPic().getWidth() / 2,
                    tempBitmap.getXY(2) - tempBitmap.getPic().getHeight() / 2, null);


            tempBitmap = pic;
            if ((Math.abs(pic.getXY(1) - this.X) < pic.getPic().getWidth() / 2)
                    && (Math.abs(pic.getXY(2) - this.Y) < pic.getPic().getHeight() / 2)) {
                this.canvas.drawBitmap(tempBitmap.getPic(), X - tempBitmap.getPic().getWidth() / 2,
                        Y - tempBitmap.getPic().getWidth() / 2, null);
                pic.preX = X;
                pic.preY = Y;
            } else {
                this.canvas.drawBitmap(tempBitmap.getPic(), tempBitmap.getXY(1) - tempBitmap.getPic().getWidth() / 2,
                        tempBitmap.getXY(2) - tempBitmap.getPic().getWidth() / 2, null);
            }
        }
        invalidate();
        return true;
    }
}