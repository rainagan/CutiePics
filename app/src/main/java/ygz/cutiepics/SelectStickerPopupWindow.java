package ygz.cutiepics;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

/**
 * Created by Raina on 2018-01-31.
 */

public class SelectStickerPopupWindow extends PopupWindow {
    private View mStickerView;

    public SelectStickerPopupWindow(Activity context, View.OnClickListener itemsOnClick) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mStickerView = inflater.inflate(R.layout.sticker_popup, null);

        this.setContentView(mStickerView);
        // set width of layout
        this.setWidth(RelativeLayout.LayoutParams.FILL_PARENT);
        // set height of the layout
        this.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        // let the popup window be clickable
        this.setFocusable(true);
        // set popup animation
        this.setAnimationStyle(R.style.Animation);
        // create a half transparent color
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // set background color of popup window
        this.setBackgroundDrawable(dw);
        // if click outside of popup window, dismiss popup window
        mStickerView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                int height = mStickerView.findViewById(R.id.pop_sticker).getTop();
                int y=(int) event.getY();
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(y<height){
                        dismiss();
                    }
                }
                return true;
            }
        });
    }
}
