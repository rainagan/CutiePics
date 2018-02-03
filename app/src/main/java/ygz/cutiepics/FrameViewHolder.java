package ygz.cutiepics;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by yuyuxiao on 2018-02-02.
 */

public class FrameViewHolder extends RecyclerView.ViewHolder {
    public ImageView frames;

    public FrameViewHolder(View itemView) {
        super(itemView);
        frames = (ImageView) itemView.findViewById(R.id.frames);
    }
}
