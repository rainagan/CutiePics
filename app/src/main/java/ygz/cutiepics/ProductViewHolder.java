package ygz.cutiepics;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by Raina on 2018-02-01.
 */

public class ProductViewHolder extends RecyclerView.ViewHolder {
    public  ImageView emoji;

    public ProductViewHolder(View itemView) {
        super(itemView);
        emoji = (ImageView) itemView.findViewById(R.id.emoji);
    }

    public  ImageView getEmoji() {return emoji;}
}