package ygz.cutiepics;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Gallery;
import android.widget.ImageView;
import android.util.*;

/**
 * Created by yuyuxiao on 2018-02-02.
 */

//implements View.OnClickListener

public class FrameViewHolder extends RecyclerView.ViewHolder {
    public ImageView frames;

    public FrameViewHolder(View itemView) {
        super(itemView);
        frames = (ImageView) itemView.findViewById(R.id.frames);
        //frames.setOnClickListener(this);
    }

    public ImageView getFrame() {return frames;}

/*
    public void onClick(View v){
        // do your action here with imageView
        //v.getSelectedView();

        BitmapDrawable res_origin = (BitmapDrawable)frames.getDrawable();
        Bitmap res = res_origin.getBitmap();

        Log.d("Debug", "View is clicked");
    }
    */



}
