package ygz.cutiepics;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

/**
 * Created by yuyuxiao on 2018-03-20.
 *
 * This class is used for savedPhoto activity
 */

public class SavePhoto extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_photo);

        Bitmap bmp = photoModel.getmPhoto();
        saveImageToGallery(bmp);
    }

    public String saveImageToGallery(Bitmap saved) {
        String result = MediaStore.Images.Media.insertImage(getContentResolver(), saved, "" , "");
        return result;
    }

    public void editNext(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        photoModel.getmPhoto().recycle();
    }

    public void sharePhoto(View view) {
        // TODO: share photo to groups
    }
}
