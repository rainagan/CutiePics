package ygz.cutiepics;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * Created by zhaofan on 2018-03-24.
 */

    //pic:the Bitmap to draw
    //preX,preY: the X and Y
class Bmp{
    float preX = 0;
    float preY = 0;
    Bitmap pic = null;

    //    	构造器
    public Bmp(Bitmap pic){
        this.pic = pic;
    }

    //    	构造器
    public Bmp(Bitmap pic, float preX, float preY){
        this(pic);
        this.preX = preX + pic.getWidth();
        this.preY = preY + pic.getHeight();
    }

    //    	return X and Y
    public float getXY(int i){
        if(i == 1){
            return this.preX;
        }
        else if(i == 2){
            return this.preY;
        }
        return (Float) null;
    }

    //    	getPicture
    public Bitmap getPic()
    {
        return this.pic;
    }
}
