package ygz.cutiepics;

import android.graphics.Bitmap;

/**
 * Created by yuyuxiao on 2018-03-20.
 *
 * This class is used as helper for savedPhoto activity
 *  it can be reused to transfer image between activities
 *
 */

public class photoModel {
    private static Bitmap mPhoto;

    private photoModel() {}

    public static Bitmap getmPhoto() {
        return mPhoto;
    }

    public static void setmPhoto(Bitmap mInsertPicture) {
        mPhoto = mInsertPicture;
    }
}
