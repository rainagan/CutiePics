package ygz.cutiepics.models;

import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by Raina on 2018-03-25.
 */

public class MyDragListener implements View.OnDragListener {

    private RelativeLayout.LayoutParams params;
    private int imgHeight;
    private int imgWidth;
    private int imgLeft;
    private int imgTop;
    private int imgRight;
    private int imgBottom;

    public MyDragListener(int height, int width, int left, int top) {
        this.imgHeight = height;
        this.imgWidth = width;
        this.imgLeft = left;
        this.imgTop = top;
        this.imgRight = imgLeft + imgWidth;
        this.imgBottom = imgTop + imgHeight;
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        View view = (View) event.getLocalState();


        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:

                params = (RelativeLayout.LayoutParams) view.getLayoutParams();
                break;

            case DragEvent.ACTION_DRAG_ENTERED:
                int x = (int) event.getX();
                int y = (int) event.getY();

                break;

            case DragEvent.ACTION_DRAG_EXITED:

                break;

            case DragEvent.ACTION_DRAG_LOCATION:
                x = (int) event.getX();
                y = (int) event.getY();
                break;

            case DragEvent.ACTION_DRAG_ENDED:

                break;

            case DragEvent.ACTION_DROP:

                x = (int) event.getX();
                y = (int) event.getY();

                if (!checkBound(x - 100, y - 30)) {
                    if (x < imgLeft) {
                        x = imgLeft+100;
                    }
                    if (x > imgRight) {
                        x = imgRight;
                    }
                    if (y < imgTop) {
                        y = imgTop+30;
                    }
                    if (y > imgBottom) {
                        y = imgBottom;
                    }
                }

                params.leftMargin = x - 100;
                params.topMargin = y - 30;

                view.setLayoutParams(params);
                view.setVisibility(View.VISIBLE);

                break;
            default:
                break;
        }
        return true;
    }

    public boolean checkBound(int x, int y) {
        if (x >= imgLeft && x <= imgRight && y >= imgTop && y <= imgBottom) {
            return true;
        } else {
            return false;
        }
    }
}
